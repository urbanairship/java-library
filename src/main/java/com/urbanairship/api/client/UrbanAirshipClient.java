package com.urbanairship.api.client;

/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Executor;
import org.apache.http.entity.ContentType;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Properties;

/**
 * The UrbanAirshipClient class handles HTTP requests to the Urban Airship API
 */
public class UrbanAirshipClient {

    /* User auth */
    private final String appKey;
    private final String appSecret;
    /* URI */
    private final URI baseURI;
    /* HTTP */
    private final HttpHost uaHost;
    private final Optional<ProxyInfo> proxyInfo;
    private final Optional<BasicHttpParams> httpParams;
    private final Executor executor;
    private String userAgent;

    UrbanAirshipClient(Builder builder) {
        this.appKey = builder.key;
        this.appSecret = builder.secret;
        this.baseURI = URI.create(builder.baseURI);
        this.uaHost = new HttpHost(URI.create(builder.baseURI).getHost(), 443, "https");
        this.proxyInfo = Optional.fromNullable(builder.proxyInfo);
        this.httpParams = Optional.fromNullable(builder.httpParams);

        this.executor = Executor.newInstance()
                .auth(this.uaHost, this.appKey, this.appSecret)
                .authPreemptive(this.uaHost);

        if (proxyInfo.isPresent()) {
            HttpHost proxyHost = proxyInfo.get().getProxyHost();
            this.executor.authPreemptiveProxy(proxyHost);

            if (proxyInfo.get().getProxyCredentials().isPresent()) {
                this.executor.auth(proxyHost, proxyInfo.get().getProxyCredentials().get());
            }
        }
    }

    /**
     * UrbanAirshipClient builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the proxy info.  This is optional.
     * @return Optional<<T>ProxyInfo</T>>
     */
    public Optional<ProxyInfo> getProxyInfo() {
        return proxyInfo;
    }

    /**
     * Get the app secret.
     * @return appSecret
     */
    public String getAppSecret() {
        return appSecret;
    }

    /**
     * Get the app key.
     * @return appKey
     */
    public String getAppKey() {
        return appKey;
    }

    /**
     * Get the non-default http parameters.  This is optional.
     * @return Optional<<T>BasicHttpParams</T>>
     */
    public Optional<BasicHttpParams> getHttpParams() {
        return httpParams;
    }

    /**
     * Command for executing Urban Airship requests
     * @param request An Urban Airship request object.
     * @return A client response.
     * @throws IOException
     */
    public <T> Response<T> execute(final Request<T> request) throws IOException, URISyntaxException {
        org.apache.http.client.fluent.Request apacheRequest;

        switch (request.getHttpMethod()) {
            case GET:
                apacheRequest = org.apache.http.client.fluent.Request.Get(request.getUri(baseURI));
                break;
            case PUT:
                apacheRequest = org.apache.http.client.fluent.Request.Put(request.getUri(baseURI));
                break;
            case POST:
                apacheRequest = org.apache.http.client.fluent.Request.Post(request.getUri(baseURI));
                break;
            case DELETE:
                apacheRequest = org.apache.http.client.fluent.Request.Delete(request.getUri(baseURI));
                break;
            case OPTIONS:
                apacheRequest = org.apache.http.client.fluent.Request.Options(request.getUri(baseURI));
                break;
            case TRACE:
                apacheRequest = org.apache.http.client.fluent.Request.Trace(request.getUri(baseURI));
                break;
            case HEAD:
                apacheRequest = org.apache.http.client.fluent.Request.Head(request.getUri(baseURI));
                break;
            default:
                apacheRequest = org.apache.http.client.fluent.Request.Get(request.getUri(baseURI));
                break;
        }

        // Headers
        Map<String, String> requestHeaders = request.getRequestHeaders();
        if (requestHeaders != null) {
            for (Map.Entry<String, String> entry : requestHeaders.entrySet()) {
                apacheRequest.addHeader(entry.getKey(), entry.getValue());
            }
        }

        // Body
        String body = request.getRequestBody();
        ContentType contentType = request.getContentType();
        if (body != null && contentType != null) {
            apacheRequest.bodyString(body, contentType);
        }

        // User Agent
        apacheRequest.config(CoreProtocolPNames.USER_AGENT, getUserAgent());

        // Http Params
        if (httpParams.isPresent()) {
            for (String name : httpParams.get().getNames()) {
                apacheRequest.config(name, httpParams.get().getParameter(name));
            }
        }

        // Proxy
        if (proxyInfo.isPresent()) {
            apacheRequest.viaProxy(proxyInfo.get().getProxyHost());
        }

        final ResponseParser<T> parser = request.getResponseParser();

        // Perform the request
        return executor.execute(apacheRequest)
                .handleResponse(new ResponseHandler<Response<T>>() {

                    @Override
                    public Response<T> handleResponse(HttpResponse httpResponse) throws IOException {
                        int statusCode = httpResponse.getStatusLine().getStatusCode();

                        if (statusCode >= 200 && statusCode < 300) {
                            return new Response<T>(parseResponse(parser, httpResponse), getHeaders(httpResponse), httpResponse.getStatusLine().getStatusCode());
                        } else {
                            throw APIRequestException.exceptionForResponse(httpResponse);
                        }
                    }
                });
    }

    /**
     * Parses the response body.
     * @param response The response.
     * @return The parsed response body or {@code null} if the response body is empty or
     * the parser is unavailable.
     * @throws IOException
     */
    private <T> T parseResponse(ResponseParser<T> parser, HttpResponse response) throws IOException {
        String jsonPayload = null;
        try {
            if (response.getEntity() != null) {
                jsonPayload = EntityUtils.toString(response.getEntity());
            }
        } finally {
            EntityUtils.consumeQuietly(response.getEntity());
        }

        if (StringUtils.isNotBlank(jsonPayload) && parser != null) {
            return parser.parse(jsonPayload);
        }

        return null;
    }



    /**
     * Retrieves the response headers.
     * @param httpResponse The HttpResponse.
     * @return An immutable map of response headers.
     */
    private ImmutableMap<String, String> getHeaders(HttpResponse httpResponse) {
        ImmutableMap.Builder<String, String> headers = ImmutableMap.builder();
        for (Header header : httpResponse.getAllHeaders()) {
            headers.put(header.getName(), header.getValue());
        }
        return  headers.build();
    }

    private String getUserAgent() {
        if (userAgent == null) {
            userAgent = "UNKNOWN";

            InputStream stream = getClass().getResourceAsStream("/client.properties");

            if (stream != null) {
                Properties props = new Properties();

                try {
                    props.load(stream);
                    stream.close();
                    userAgent = "UAJavaLib/" + props.get("client.version");
                } catch (IOException e) {
                    // log it
                }
            }
        }

        return userAgent;
    }


    /* Object methods */

    @Override
    public int hashCode() {
        return Objects.hashCode(appKey, appSecret, baseURI, uaHost, proxyInfo, httpParams);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final UrbanAirshipClient other = (UrbanAirshipClient) obj;
        return Objects.equal(this.appKey, other.appKey)
            && Objects.equal(this.appSecret, other.appSecret)
            && Objects.equal(this.baseURI, other.baseURI)
            && Objects.equal(this.uaHost, other.uaHost)
            && Objects.equal(this.proxyInfo, other.proxyInfo)
            && Objects.equal(this.httpParams, other.httpParams);
    }

    @Override
    public String toString() {
        return "APIClient{ +" +
            "appKey=" + appKey +
            ", appSecret=" + appSecret +
            ", baseURI=" + baseURI +
            ", uaHost=" + uaHost +
            ", proxyInfo=" + proxyInfo +
            ", httpParams=" + httpParams +
            '}';
    }

    /* Builder for newAPIClient */

    public static class Builder {

        private String key;
        private String secret;
        private String baseURI;
        private ProxyInfo proxyInfo = null;
        private BasicHttpParams httpParams = null;

        private Builder() {
            baseURI = "https://go.urbanairship.com";
        }

        /**
         * Set the app key.
         * @param key String app key
         * @return Builder
         */
        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        /**
         * Set the app secret.
         * @param appSecret String app secret
         * @return Builder
         */
        public Builder setSecret(String appSecret) {
            this.secret = appSecret;
            return this;
        }

        /**
         * Set the base URI -- defaults to "https://go.urbanairship.com"
         * @param URI String base URI
         * @return Builder
         */
        public Builder setBaseURI(String URI) {
            this.baseURI = URI;
            return this;
        }

        /**
         * Set the proxy info.
         * @param value ProxyInfo
         * @return Builder
         */
        public Builder setProxyInfo(ProxyInfo value) {
            this.proxyInfo = value;
            return this;
        }

        /**
         * Set the http parameters.
         * @param httpParams BasicHttpParams
         * @return Builder
         */
        public Builder setHttpParams(BasicHttpParams httpParams) {
            this.httpParams = httpParams;
            return this;
        }

        /**
         * Build an UrbanAirshipClient object.  Will fail if any of the following
         * preconditions are not met.
         * <pre>
         * 1. App key must be set.
         * 2. App secret must be set.
         * 3. The base URI has been overridden but not set.
         * </pre>
         *
         * @return UrbanAirshipClient
         */
        public UrbanAirshipClient build() {
            Preconditions.checkNotNull(key, "app key needed to build APIClient");
            Preconditions.checkNotNull(secret, "app secret needed to build APIClient");
            Preconditions.checkNotNull(baseURI, "base URI needed to build APIClient");

            return new UrbanAirshipClient(this);
        }
    }
}
