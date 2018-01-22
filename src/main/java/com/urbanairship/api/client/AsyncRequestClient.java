package com.urbanairship.api.client;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.io.BaseEncoding;
import org.apache.http.entity.ContentType;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.BoundRequestBuilder;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.asynchttpclient.filter.FilterContext;
import org.asynchttpclient.proxy.ProxyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsyncRequestClient extends RequestClient {

    private static final Logger log = LoggerFactory.getLogger(UrbanAirshipClient.class);

    private final String appKey;
    private final Optional<String> appSecret;
    private final Optional<String> bearerToken;
    private final Optional<ProxyServer> proxyServer;

    private final URI baseUri;
    private final AsyncHttpClient client;
    private final DefaultAsyncHttpClientConfig clientConfig;

    private AsyncRequestClient(Builder builder) {
        this.appKey = builder.key;
        this.appSecret = Optional.fromNullable(builder.secret);
        this.bearerToken = Optional.fromNullable(builder.bearerToken);
        this.baseUri = URI.create(builder.baseUri);

        DefaultAsyncHttpClientConfig.Builder clientConfigBuilder = builder.clientConfigBuilder;

        clientConfigBuilder.setUserAgent(getUserAgent());
        clientConfigBuilder.addResponseFilter(new RequestRetryFilter(builder.maxRetries, Optional.fromNullable(builder.retryPredicate)));

        if (Optional.fromNullable(builder.proxyServer).isPresent()) {
            proxyServer = Optional.fromNullable(builder.proxyServer.build());
            clientConfigBuilder.setProxyServer(proxyServer.get());
            clientConfigBuilder.setRealm(proxyServer.get().getRealm());
        } else {
            proxyServer = Optional.absent();
        }

        this.clientConfig = clientConfigBuilder.build();
        this.client = new DefaultAsyncHttpClient(clientConfig);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the app key.
     * @return The app key.
     */
    @Override
    public String getAppKey() {
        return appKey;
    }

    /**
     * Get the app secret.
     * @return The app secret.
     */
    @Override
    public Optional<String> getAppSecret() {
        return appSecret;
    }

    /**
     * Get the bearer token.
     * @return The bearer token.
     */
    @Override
    public Optional<String> getBearerToken() {
        return bearerToken;
    }

    public Optional<ProxyServer> getProxyServer() {
        return proxyServer;
    }

    public DefaultAsyncHttpClientConfig getClientConfig() {
        return clientConfig;
    }

    /**
     * Retrieve the client user agent.
     *
     * @return The user agent.
     */
    @VisibleForTesting
    public String getUserAgent() {
        String userAgent = "UNKNOWN";
        InputStream stream = getClass().getResourceAsStream("/client.properties");

        if (stream != null) {
            Properties props = new Properties();
            try {
                props.load(stream);
                stream.close();
                userAgent = "UAJavaLib/" + props.get("client.version");
            } catch (IOException e) {
                log.error("Failed to retrieve client user agent due to IOException - setting to \"UNKNOWN\"", e);
            }
        }
        return userAgent;
    }

    @Override
    public <T> Response execute(Request<T> request, ResponseCallback callback) {
        try {
            return executeAsync(request, callback).get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted while retrieving response from future", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Failed to retrieve response from future", e);
        }
    }

    /**
     * Close the underlying HTTP client's thread pool.
     */
    @Override
    public void close() throws IOException {
        log.info("Closing client");
        client.close();
    }

    @Override
    public <T> Response execute(Request<T> request) {
        return execute(request, null);
    }

    @Override
    /**
     * Command for executing Urban Airship requests asynchronously with a ResponseCallback.
     *
     * @param request An Urban Airship request object.
     * @param callback A ResponseCallback instance.
     * @return A client response future.
     */
    public <T> Future<Response> executeAsync(final Request<T> request, final ResponseCallback callback) {
        BoundRequestBuilder requestBuilder;
        String uri;

        try {
            uri = request.getUri(baseUri).toString();
        } catch (URISyntaxException e) {
            log.error("Failed to generate a request URI from base URI " + baseUri.toString(), e);
            throw new RuntimeException(e);
        }

        switch (request.getHttpMethod()) {
            case GET:
                requestBuilder = client.prepareGet(uri);
                break;
            case PUT:
                requestBuilder = client.preparePut(uri);
                break;
            case POST:
                requestBuilder = client.preparePost(uri);
                break;
            case DELETE:
                requestBuilder = client.prepareDelete(uri);
                break;
            default:
                requestBuilder = client.prepareGet(uri);
                break;
        }

        // Headers
        Map<String, String> requestHeaders = request.getRequestHeaders();
        if (requestHeaders != null) {
            for (Map.Entry<String, String> entry : requestHeaders.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        String auth;

        if (request.bearerTokenAuthRequired()) {
            Preconditions.checkNotNull(bearerToken.get(), "Bearer token required for request: " + request);
            auth = "Bearer " + bearerToken.get();
        } else {
            Preconditions.checkNotNull(appSecret.get(), "App secret required for request: " + request);
            auth = "Basic " + BaseEncoding.base64().encode((appKey + ":" + appSecret.get()).getBytes());
        }

        requestBuilder.addHeader("Authorization", auth)
                .addHeader("X-UA-Appkey", appKey);

        // Body
        String body = request.getRequestBody();
        ContentType contentType = request.getContentType();
        if (body != null && contentType != null) {
            requestBuilder.setBody(body.getBytes(contentType.getCharset()));
        }

        log.debug(String.format("Executing Urban Airship request to %s with body %s.", uri, request.getRequestBody()));
        ResponseAsyncHandler<T> handler = new ResponseAsyncHandler<>(Optional.fromNullable(callback), request.getResponseParser());
        return requestBuilder.execute(handler);
    }

    @Override
    public <T> Future<Response> executeAsync(Request<T> request) {
        return executeAsync(request, null);
    }

    public static class Builder {

        private String key;
        private String secret;
        private String baseUri;
        private String bearerToken;
        private Integer maxRetries = 10;
        private DefaultAsyncHttpClientConfig.Builder clientConfigBuilder = new DefaultAsyncHttpClientConfig.Builder();
        private Predicate<FilterContext> retryPredicate = null;
        private ProxyServer.Builder proxyServer;

        private Builder() {
            baseUri = "https://go.urbanairship.com";
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
         * Set the bearer token.
         * @param bearerToken String bearer token
         * @return Builder
         */
        public Builder setBearerToken(String bearerToken) {
            this.bearerToken = bearerToken;
            return this;
        }

        /**
         * Set the base URI -- defaults to "https://go.urbanairship.com"
         * @param URI String base URI
         * @return Builder
         */
        public Builder setBaseUri(String URI) {
            this.baseUri = URI;
            return this;
        }

        /**
         * Set the maximum for non-POST request retries on 5xxs -- defaults to 10.
         *
         * @param maxRetries The maximum.
         * @return Builder
         */
        public Builder setMaxRetries(Integer maxRetries) {
            this.maxRetries = maxRetries;
            return this;
        }

        /**
         * Set the client config builder -- defaults to a new builder. Available for custom settings.
         *
         * @param builder The client config builder.
         * @return Builder
         */
        public Builder setClientConfigBuilder(DefaultAsyncHttpClientConfig.Builder builder) {
            this.clientConfigBuilder = builder;
            return this;
        }

        /**
         * Set the proxy server builder.
         *
         * @param builder
         * @return Builder
         */
        public Builder setProxyServer(ProxyServer.Builder builder) {
            this.proxyServer = builder;
            return this;
        }

        /**
         * Set an optional predicate for allowing request retries on 5xxs.
         *
         * @param retryPredicate The retry predicate.
         * @return Builder
         */
        public Builder setRetryPredicate(Predicate<FilterContext> retryPredicate) {
            this.retryPredicate = retryPredicate;
            return this;
        }

        /**
         * Build an UrbanAirshipClient object.  Will fail if any of the following
         * preconditions are not met.
         * <pre>
         * 1. App key must be set.
         * 2. App secret must be set.
         * 3. The base URI has been overridden but not set.
         * 4. Max for non-POST 5xx retries must be set, already defaults to 10.
         * 5. HTTP client config builder must be set, already defaults to a new builder.
         * </pre>
         *
         * @return UrbanAirshipClient
         */
        public AsyncRequestClient build() {
            Preconditions.checkNotNull(key, "app key needed to build APIClient");
            Preconditions.checkNotNull(baseUri, "base URI needed to build APIClient");
            Preconditions.checkNotNull(maxRetries, "max non-POST retries needed to build APIClient");
            Preconditions.checkNotNull(clientConfigBuilder, "Async HTTP client config builder needed to build APIClient");

            if (secret == null && bearerToken == null) {
                throw new NullPointerException("secret or the bearer token must be set");
            }

            return new AsyncRequestClient(this);
        }
    }
}
