/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.google.common.base.Preconditions;

import com.urbanairship.api.client.model.*;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.schedule.model.SchedulePayload;

import com.urbanairship.api.tag.model.AddRemoveDeviceFromTagPayload;
import com.urbanairship.api.tag.model.BatchModificationPayload;
import org.apache.commons.lang.StringUtils;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.params.CoreProtocolPNames;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URI;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * The APIClient class handles HTTP requests to the Urban Airship API
 */

public class APIClient {

    /* Header keys/values */
    private final static String CONTENT_TYPE_KEY = "Content-type";
    private final static String CONTENT_TYPE_VALUE = "application/json";
    private final static String ACCEPT_KEY = "Accept";
    private final static String UA_APPLICATION_JSON = "application/vnd.urbanairship+json;";

    /* URI Paths */
    private final static String API_PUSH_PATH = "/api/push/";
    private final static String API_VALIDATE_PATH = "/api/push/validate/";
    private final static String API_SCHEDULE_PATH = "/api/schedules/";
    private final static String API_TAGS_PATH = "/api/tags/";
    private final static String API_TAGS_BATCH_PATH = "/api/tags/batch/";
    private final static String API_SEGMENTS_PATH = "/api/segments/";

    /* User auth */
    private final String appKey;
    private final String appSecret;

    /* URI, version */
    private final URI baseURI;
    private final Number version;

    /* HTTP */
    private final HttpHost uaHost;

    private final static Logger logger = LoggerFactory.getLogger("com.urbanairship.api");

    public static Builder newBuilder(){
        return new Builder();
    }

    private APIClient(String appKey, String appSecret, String baseURI, Number version) {
        Preconditions.checkArgument(StringUtils.isNotBlank(appKey),
                "App key must be provided.");
        Preconditions.checkArgument(StringUtils.isNotBlank(appSecret),
                "App secret must be provided");
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.baseURI = URI.create(baseURI);
        this.version = version;
        this.uaHost = new HttpHost(URI.create(baseURI).getHost(), 443, "https");
    }

    public String getAppSecret() { return appSecret; }
    public String getAppKey() { return appKey; }

    /* Add the version number to the default version header */

    private String versionedAcceptHeader(Number version){
        return String.format("%s version=%s;", UA_APPLICATION_JSON, version.toString());
    }

    /*
    Retrieves Java Client API Version
    */
    public String getUserAgent() {
        InputStream stream = getClass().getResourceAsStream("/client.properties");

        if (stream == null) { return "UNKNOWN"; }

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
        return object
                .config(CoreProtocolPNames.USER_AGENT, getUserAgent())
                .addHeader(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE)
                .addHeader(ACCEPT_KEY, versionedAcceptHeader(version));
    }

    private Request provisionRequestWithoutAcceptKey(Request object) {
        return object
                .config(CoreProtocolPNames.USER_AGENT, getUserAgent())
                .addHeader(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
    }

    private Executor provisionExecutor() {
        return Executor.newInstance()
                .auth(uaHost, appKey, appSecret)
                .authPreemptive(uaHost);
    }

    /* Push API */

    public APIClientResponse<APIPushResponse> push(PushPayload payload) throws IOException {
        Preconditions.checkNotNull(payload, "Payload required when executing a push operation");
        Request request = provisionRequest(Request.Post(baseURI.resolve(API_PUSH_PATH)));
        request.bodyString(payload.toJSON(), ContentType.APPLICATION_JSON);

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing push request %s", request));
        }

        return provisionExecutor().execute(request).handleResponse(new PushAPIResponseHandler());
    }

    public APIClientResponse<APIPushResponse> validate(PushPayload payload) throws IOException {
        Preconditions.checkNotNull(payload, "Payload required when executing a validate push operation");
        Request request = provisionRequest(Request.Post(baseURI.resolve(API_VALIDATE_PATH)));
        request.bodyString(payload.toJSON(), ContentType.APPLICATION_JSON);

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing validate push request %s", request));
        }

        return provisionExecutor().execute(request).handleResponse(new PushAPIResponseHandler());
    }

    /* Schedules API */

    public APIClientResponse<APIScheduleResponse> schedule(SchedulePayload payload) throws IOException {
        Preconditions.checkNotNull(payload, "Payload required when scheduling a push request");
        Request request = provisionRequest(Request.Post(baseURI.resolve(API_SCHEDULE_PATH)));
        request.bodyString(payload.toJSON(), ContentType.APPLICATION_JSON);

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing schedule request %s", request));
        }

        return provisionExecutor().execute(request).handleResponse(new ScheduleAPIResponseHandler());
    }

    public APIClientResponse<APIListAllSchedulesResponse> listAllSchedules() throws IOException {
        Request request = provisionRequest(Request.Get(baseURI.resolve(API_SCHEDULE_PATH)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list all schedules request %s", request));
        }

        return provisionExecutor().execute(request).handleResponse(new ListAllSchedulesAPIResponseHandler());
    }

    public APIClientResponse<APIListAllSchedulesResponse> listAllSchedules(String start, int limit, String order) throws IOException {
        String path = "/api/schedules" + "?" + "start=" + start + "&limit=" + limit +"&order=" + order;
        Request request = provisionRequest(Request.Get(baseURI.resolve(path)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list all schedules request %s", request));
        }

        return provisionExecutor().execute(request).handleResponse(new ListAllSchedulesAPIResponseHandler());
    }

    public APIClientResponse<APIListAllSchedulesResponse> listAllSchedules(String next_page) throws IOException, URISyntaxException {
        URI np = new URI(next_page);
        Request request = provisionRequest(Request.Get(baseURI.resolve(np.getPath() + "?" + np.getQuery())));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list all schedules request %s", request));
        }

        return provisionExecutor().execute(request).handleResponse(new ListAllSchedulesAPIResponseHandler());
    }

    public APIClientResponse<SchedulePayload> listSchedule(String id) throws IOException {
        Request request = provisionRequest(Request.Get(baseURI.resolve(API_SCHEDULE_PATH + id)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list specific schedule request %s", request));
        }

        return provisionExecutor().execute(request).handleResponse(new ListScheduleAPIResponseHandler());
    }

    public APIClientResponse<APIScheduleResponse> updateSchedule(SchedulePayload payload, String id) throws IOException {
        Preconditions.checkNotNull(payload, "Payload is required when updating schedule");
        Request req = provisionRequest(Request.Put(baseURI.resolve(API_SCHEDULE_PATH + id)));
        req.bodyString(payload.toJSON(), ContentType.APPLICATION_JSON);

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing update schedule request %s", req));
        }

        return provisionExecutor().execute(req).handleResponse(new ScheduleAPIResponseHandler());
    }

    public HttpResponse deleteSchedule(String id) throws IOException {
        Request req = provisionRequest(Request.Delete(baseURI.resolve(API_SCHEDULE_PATH + id)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing delete schedule request %s", req));
        }

        return provisionExecutor().execute(req).returnResponse();
    }

    /* Tags API */

    public APIClientResponse<APIListTagsResponse> listTags() throws IOException {
        Request req = provisionRequest(Request.Get(baseURI.resolve(API_TAGS_PATH)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list tags request %s", req));
        }

        return provisionExecutor().execute(req).handleResponse(new ListTagsAPIResponseHandler());
    }

    public HttpResponse createTag(String tag) throws IOException {
        Request req = provisionRequest(Request.Put(baseURI.resolve(API_TAGS_PATH + tag)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing create tag request %s", req));
        }

        return provisionExecutor().execute(req).returnResponse();
    }

    public HttpResponse deleteTag(String tag) throws IOException {
        Request req = provisionRequest(Request.Delete(baseURI.resolve(API_TAGS_PATH + tag)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing delete tag request %s", req));
        }

        return provisionExecutor().execute(req).returnResponse();
    }

    public HttpResponse addRemoveDevicesFromTag(String tag, AddRemoveDeviceFromTagPayload payload) throws IOException {
        Preconditions.checkNotNull(payload, "Payload is required when adding and/or removing devices from a tag");
        Request req = provisionRequest(Request.Post(baseURI.resolve(API_TAGS_PATH + tag)));
        req.bodyString(payload.toJSON(), ContentType.APPLICATION_JSON);

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing add/remove devices from tag request %s", req));
        }

        return provisionExecutor().execute(req).returnResponse();
    }

    public HttpResponse batchModificationOfTags(BatchModificationPayload payload) throws IOException {
        Preconditions.checkNotNull(payload, "Payload is required when performing batch modification of tags");
        Request req = provisionRequest(Request.Post(baseURI.resolve(API_TAGS_BATCH_PATH)));
        req.bodyString(payload.toJSON(), ContentType.APPLICATION_JSON);

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing batch modification of tags request %s", req));
        }

        return provisionExecutor().execute(req).returnResponse();
    }

    /* Segments API */

    public APIClientResponse<APIListAllSegmentsResponse> listAllSegments() throws IOException {
        Request req = provisionRequestWithoutAcceptKey(Request.Get(baseURI.resolve(API_SEGMENTS_PATH)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list all segments request %s", req));
        }

        return provisionExecutor().execute(req).handleResponse(new ListAllSegmentsAPIResponseHandler());
    }

    public APIClientResponse<APIListAllSegmentsResponse> listAllSegments(String nextPage) throws IOException, URISyntaxException {
        URI np = new URI(nextPage);
        Request req = provisionRequestWithoutAcceptKey(Request.Get(baseURI.resolve(np.getPath() + "?" + np.getQuery())));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list all segments request %s", req));
        }

        return provisionExecutor().execute(req).handleResponse(new ListAllSegmentsAPIResponseHandler());
    }

    public APIClientResponse<APIListAllSegmentsResponse> listAllSegments(String start, int limit, String order) throws IOException, URISyntaxException {
        String path = "/api/segments" + "?" + "start=" + start + "&limit=" + limit +"&order=" + order;
        Request req = provisionRequest(Request.Get(baseURI.resolve(path)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list all segments request %s", req));
        }

        return provisionExecutor().execute(req).handleResponse(new ListAllSegmentsAPIResponseHandler());
    }

    /* Object methods */

    @Override
    public String toString() {
        return "APIClient\nAppKey:"+ appKey +"\nAppSecret:" + appSecret + "\n";
    }

    /* Builder for APIClient */

    public static class Builder {

        private String key;
        private String secret;
        private String baseURI;
        private Number version;

        private Builder(){
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

        public Builder setBaseURI(String URI){
            this.baseURI = URI;
            return this;
        }

        public Builder setVersion(Number version){
            this.version = version;
            return this;
        }

        public APIClient build() {
            Preconditions.checkNotNull(key, "app key needed to build APIClient");
            Preconditions.checkNotNull(secret, "app secret needed to build APIClient");
            Preconditions.checkNotNull(baseURI, "base URI needed to build APIClient");
            Preconditions.checkNotNull(version, "version needed to build APIClient");
            return new APIClient(key, secret, baseURI, version);
        }

    }
}
