/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.urbanairship.api.client.model.APIClientResponse;
import com.urbanairship.api.client.model.APIListTagsResponse;
import com.urbanairship.api.client.model.APILocationResponse;
import com.urbanairship.api.location.model.BoundedBox;
import com.urbanairship.api.location.model.Point;
import com.urbanairship.api.tag.model.AddRemoveDeviceFromTagPayload;
import com.urbanairship.api.tag.model.BatchModificationPayload;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * The APIClient class handles HTTP requests to the Urban Airship API
 */

public class APIClient {

    /* Static Strings */
    private final static String HOURLY = "HOURLY";
    private final static String MONTHLY = "MONTHLY";
    private final static String DAILY = "DAILY";

    /* Header keys/values */
    private final static String CONTENT_TYPE_KEY = "Content-type";
    private final static String ACCEPT_KEY = "Accept";
    private final static String CONTENT_TYPE_JSON = "application/json";
    private final static String UA_APPLICATION_JSON = "application/vnd.urbanairship+json;";

    /* URI Paths */
    private final static String API_TAGS_PATH = "/api/tags/";
    private final static String API_TAGS_BATCH_PATH = "/api/tags/batch/";
    private final static String API_LOCATION_PATH = "/api/location/";

    private final static Logger logger = LoggerFactory.getLogger("com.urbanairship.api");
    /* User auth */
    private final String appKey;
    private final String appSecret;
    /* URI, version */
    private final URI baseURI;
    private final Number version;
    /* HTTP */
    private final HttpHost uaHost;
    private final Optional<ProxyInfo> proxyInfo;
    private final Optional<BasicHttpParams> httpParams;


    private APIClient(String appKey, String appSecret, String baseURI, Number version, Optional<ProxyInfo> proxyInfoOptional, Optional<BasicHttpParams> httpParams) {
        Preconditions.checkArgument(StringUtils.isNotBlank(appKey),
                "App key must be provided.");
        Preconditions.checkArgument(StringUtils.isNotBlank(appSecret),
                "App secret must be provided");
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.baseURI = URI.create(baseURI);
        this.version = version;
        this.uaHost = new HttpHost(URI.create(baseURI).getHost(), 443, "https");
        this.proxyInfo = proxyInfoOptional;
        this.httpParams = httpParams;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Optional<ProxyInfo> getProxyInfo() {
        return proxyInfo;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public String getAppKey() {
        return appKey;
    }

    public Optional<BasicHttpParams> getHttpParams() {
        return httpParams;
    }

    /* Add the version number to the default version header */

    private String versionedAcceptHeader(Number version) {
        return String.format("%s version=%s;", UA_APPLICATION_JSON, version.toString());
    }

    /* Retrieves Java Client API Version */

    public String getUserAgent() {
        InputStream stream = getClass().getResourceAsStream("/client.properties");

        if (stream == null) {
            return "UNKNOWN";
        }

        Properties props = new Properties();

        try {
            props.load(stream);
            stream.close();
            return "UAJavaLib/" + props.get("client.version");
        } catch (IOException e) {
            return "UNKNOWN";
        }
    }
    
    /* Provisioning Methods */

    private Request provisionRequest(Request object) {
        object.config(CoreProtocolPNames.USER_AGENT, getUserAgent())
                .addHeader(CONTENT_TYPE_KEY, CONTENT_TYPE_JSON)
                .addHeader(ACCEPT_KEY, versionedAcceptHeader(version));

        if (httpParams.isPresent()) {
            for (String name : httpParams.get().getNames()) {
                object.config(name, httpParams.get().getParameter(name));
            }
        }

        if (proxyInfo.isPresent()) {
            object.viaProxy(proxyInfo.get().getProxyHost());
        }

        return object;
    }

    private Executor provisionExecutor() {
        Executor executor = Executor.newInstance()
                .auth(uaHost, appKey, appSecret)
                .authPreemptive(uaHost);

        if (proxyInfo.isPresent()) {

            HttpHost host = proxyInfo.get().getProxyHost();
            executor.authPreemptiveProxy(host);

            if (proxyInfo.get().getProxyCredentials().isPresent()) {
                executor.auth(host, proxyInfo.get().getProxyCredentials().get());
            }
        }

        return executor;
    }

    /* A method to resolve base URIs without excluding the original path */

    public static URI baseURIResolution(URI baseURI, String path) {
        if (!baseURI.getPath().endsWith("/")) {
            try {
                baseURI = new URI(baseURI.toString() + "/");
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        if (path.startsWith("/"))  {
            path = path.substring(1);
        }

        return baseURI.resolve(path);
    }

    /* Tags API */

    public APIClientResponse<APIListTagsResponse> listTags() throws IOException {
        Request req = provisionRequest(Request.Get(baseURIResolution(baseURI, API_TAGS_PATH)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list tags request %s", req));
        }

        return provisionExecutor()
            .execute(req)
            .handleResponse(new APIClientResponseHandler<APIListTagsResponse>(APIListTagsResponse.class));
    }

    public HttpResponse createTag(String tag) throws IOException {
        Request req = provisionRequest(Request.Put(baseURIResolution(baseURI, API_TAGS_PATH + tag)));

        req.removeHeaders(CONTENT_TYPE_KEY);

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing create tag request %s", req));
        }

        return provisionExecutor().execute(req).returnResponse();
    }

    public HttpResponse deleteTag(String tag) throws IOException {
        Request req = provisionRequest(Request.Delete(baseURIResolution(baseURI, API_TAGS_PATH + tag)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing delete tag request %s", req));
        }

        return provisionExecutor().execute(req).returnResponse();
    }

    public HttpResponse addRemoveDevicesFromTag(String tag, AddRemoveDeviceFromTagPayload payload) throws IOException {
        Preconditions.checkNotNull(payload, "Payload is required when adding and/or removing devices from a tag");
        Request req = provisionRequest(Request.Post(baseURIResolution(baseURI, API_TAGS_PATH + tag)));
        req.bodyString(payload.toJSON(), ContentType.APPLICATION_JSON);

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing add/remove devices from tag request %s", req));
        }

        return provisionExecutor().execute(req).returnResponse();
    }

    public HttpResponse batchModificationOfTags(BatchModificationPayload payload) throws IOException {
        Preconditions.checkNotNull(payload, "Payload is required when performing batch modification of tags");
        Request req = provisionRequest(Request.Post(baseURIResolution(baseURI, API_TAGS_BATCH_PATH)));
        req.bodyString(payload.toJSON(), ContentType.APPLICATION_JSON);

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing batch modification of tags request %s", req));
        }

        return provisionExecutor().execute(req).returnResponse();
    }

    /* Location API */

    public APIClientResponse<APILocationResponse> queryLocationInformation(String query) throws IOException {
        Preconditions.checkArgument(StringUtils.isNotBlank(query), "Query text cannot be blank");

        URIBuilder builder = new URIBuilder(baseURIResolution(baseURI, API_LOCATION_PATH));
        builder.addParameter("q", query);

        Request req = provisionRequest(Request.Get(builder.toString()));

        req.removeHeaders(ACCEPT_KEY);      // Workaround for v3 routing bug

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing query location information without type request %s", req));
        }

        return provisionExecutor()
            .execute(req)
            .handleResponse(new APIClientResponseHandler<APILocationResponse>(APILocationResponse.class));
    }

    public APIClientResponse<APILocationResponse> queryLocationInformation(String query, String type) throws IOException {
        Preconditions.checkArgument(StringUtils.isNotBlank(query), "Query text cannot be blank");

        URIBuilder builder = new URIBuilder(baseURIResolution(baseURI, API_LOCATION_PATH));
        builder.addParameter("q", query);
        builder.addParameter("type", type);

        Request req = provisionRequest(Request.Get(builder.toString()));

        req.removeHeaders(ACCEPT_KEY);      // Workaround for v3 routing bug

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing query location information without type request %s", req));
        }

        return provisionExecutor()
            .execute(req)
            .handleResponse(new APIClientResponseHandler<APILocationResponse>(APILocationResponse.class));
    }

    public APIClientResponse<APILocationResponse> queryLocationInformation(Point point) throws IOException {
        Preconditions.checkNotNull(point, "Point must not be null");
        Preconditions.checkArgument(point.isValid(), "Point must be a valid coordinate");

        URIBuilder builder = new URIBuilder(baseURIResolution(baseURI, API_LOCATION_PATH + point.getLatitude() + "," + point.getLongitude()));

        Request req = provisionRequest(Request.Get(builder.toString()));

        req.removeHeaders(ACCEPT_KEY);      // Workaround for v3 routing bug

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing query location information without type request %s", req));
        }

        return provisionExecutor()
            .execute(req)
            .handleResponse(new APIClientResponseHandler<APILocationResponse>(APILocationResponse.class));
    }

    public APIClientResponse<APILocationResponse> queryLocationInformation(Point point, String type) throws IOException {
        Preconditions.checkNotNull(point, "Point must not be null");
        Preconditions.checkArgument(point.isValid(), "Point must be a valid coordinate");

        URIBuilder builder = new URIBuilder(baseURIResolution(baseURI, API_LOCATION_PATH + point.getLatitude() + "," + point.getLongitude()));
        builder.addParameter("type", type);

        Request req = provisionRequest(Request.Get(builder.toString()));

        req.removeHeaders(ACCEPT_KEY);      // Workaround for v3 routing bug

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing query location information without type request %s", req));
        }

        return provisionExecutor()
            .execute(req)
            .handleResponse(new APIClientResponseHandler<APILocationResponse>(APILocationResponse.class));
    }

    public APIClientResponse<APILocationResponse> queryLocationInformation(BoundedBox box) throws IOException {
        Preconditions.checkNotNull(box, "Box must not be null");
        Preconditions.checkArgument(box.isValid(), "Box must be a valid coordinate");

        URIBuilder builder = new URIBuilder(baseURIResolution(baseURI, API_LOCATION_PATH +
                        box.getCornerOne().getLatitude() + "," +
                        box.getCornerOne().getLongitude() + "," +
                        box.getCornerTwo().getLatitude() + "," +
                        box.getCornerTwo().getLongitude()
        ));

        Request req = provisionRequest(Request.Get(builder.toString()));

        req.removeHeaders(ACCEPT_KEY);      // Workaround for v3 routing bug

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing query location information without type request %s", req));
        }

        return provisionExecutor()
            .execute(req)
            .handleResponse(new APIClientResponseHandler<APILocationResponse>(APILocationResponse.class));
    }

    public APIClientResponse<APILocationResponse> queryLocationInformation(BoundedBox box, String type) throws IOException {
        Preconditions.checkNotNull(box, "Box must not be null");
        Preconditions.checkArgument(box.isValid(), "Box must be a valid coordinate");

        URIBuilder builder = new URIBuilder(baseURIResolution(baseURI, API_LOCATION_PATH +
                        box.getCornerOne().getLatitude() + "," +
                        box.getCornerOne().getLongitude() + "," +
                        box.getCornerTwo().getLatitude() + "," +
                        box.getCornerTwo().getLongitude()
        ));

        builder.addParameter("type", type);

        Request req = provisionRequest(Request.Get(builder.toString()));

        req.removeHeaders(ACCEPT_KEY);      // Workaround for v3 routing bug

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing query location information without type request %s", req));
        }

        return provisionExecutor()
            .execute(req)
            .handleResponse(new APIClientResponseHandler<APILocationResponse>(APILocationResponse.class));
    }


    /* Object methods */

    @Override
    public int hashCode() {
        return Objects.hashCode(appKey, appSecret, baseURI, version, uaHost, proxyInfo, httpParams);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final APIClient other = (APIClient) obj;
        return Objects.equal(this.appKey, other.appKey)
            && Objects.equal(this.appSecret, other.appSecret)
            && Objects.equal(this.baseURI, other.baseURI)
            && Objects.equal(this.version, other.version)
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
                ", version=" + version +
                ", uaHost=" + uaHost +
                ", proxyInfo=" + proxyInfo +
                ", httpParams=" + httpParams +
                '}';
    }

    /* Builder for APIClient */

    public static class Builder {

        private String key;
        private String secret;
        private String baseURI;
        private Number version;
        private ProxyInfo proxyInfoOptional;
        private BasicHttpParams httpParams;

        private Builder() {
            baseURI = "https://go.urbanairship.com";
            version = 3;
        }

        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        public Builder setSecret(String appSecret) {
            this.secret = appSecret;
            return this;
        }

        public Builder setBaseURI(String URI) {
            this.baseURI = URI;
            return this;
        }

        public Builder setVersion(Number version) {
            this.version = version;
            return this;
        }

        public Builder setProxyInfo(ProxyInfo value) {
            this.proxyInfoOptional = value;
            return this;
        }

        public Builder setHttpParams(BasicHttpParams httpParams) {
            this.httpParams = httpParams;
            return this;
        }

        public APIClient build() {
            Preconditions.checkNotNull(key, "app key needed to build APIClient");
            Preconditions.checkNotNull(secret, "app secret needed to build APIClient");
            Preconditions.checkNotNull(baseURI, "base URI needed to build APIClient");
            Preconditions.checkNotNull(version, "version needed to build APIClient");

            return new APIClient(key, secret, baseURI, version, Optional.fromNullable(proxyInfoOptional), Optional.fromNullable(httpParams));
        }

    }
}
