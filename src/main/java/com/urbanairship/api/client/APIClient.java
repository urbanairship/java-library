/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.google.common.base.Preconditions;

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

import java.net.URI;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * APIClient handles HTTP requests to the Urban Airship API
 */
public class APIClient {

    /* Header keys/values */
    private final static String CONTENT_TYPE_KEY = "Content-type";
    private final static String ACCEPT_KEY = "Accept";
    private final static String UA_APPLICATION_JSON = "application/vnd.urbanairship+json;";
    private final static String USER_AGENT = "UrbanAirship/version0.1beta";

    /* URI Paths */
    private final static String API_PUSH_PATH = "/api/push/";
    private final static String API_VALIDATE_PATH = "/api/push/validate/";
    private final static String API_SCHEDULE_PATH = "/api/schedules/";
    private final static String API_TAGS_PATH = "/api/tags/";
    private final static String API_TAGS_BATCH_PATH = "/api/tags/batch/";

    /* User auth */
    private final String appKey;
    private final String appSecret;

    /* URI, version */
    private final URI baseURI;
    private final Number version;

    /* HTTP */
    private final HttpHost uaHost;
    private final static String UA_HOSTNAME = "go.urbanairship.com";
    private final static String GET = "GET";
    private final static String POST = "POST";
    private final static String PUT = "PUT";
    private final static String DELETE = "DELETE";

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
        this.uaHost = new HttpHost(UA_HOSTNAME, 443, "https");
    }

    public String getAppSecret() { return appSecret; }
    public String getAppKey() { return appKey; }

    /*
    Add the version number to the default version header.
     */
    private String versionedAcceptHeader(Number version){
        return String.format("%s version=%s", UA_APPLICATION_JSON, version.toString());
    }

    /*
    Base request common for all API push operations
     */
    private Request pushRequest(PushPayload payload, String path){
        URI uri = baseURI.resolve(path);
        return Request.Post(uri)
                      .config(CoreProtocolPNames.USER_AGENT, USER_AGENT)
                      .addHeader(CONTENT_TYPE_KEY, versionedAcceptHeader(version))
                      .addHeader(ACCEPT_KEY, versionedAcceptHeader(version))
                      .bodyString(payload.toJSON(), ContentType.APPLICATION_JSON);
    }

    /*
    Base request for all API schedule operations
    Suppressing warnings until more of schedule API is implemented
     */
    private Request scheduleRequest(SchedulePayload payload, @SuppressWarnings("SameParameterValue") String path,
                                    @SuppressWarnings("SameParameterValue") String httpMethod){
        URI uri = baseURI.resolve(path);
        Request request;

        if (httpMethod.equals(POST)) {
            request = Request.Post(uri);
            request.bodyString(payload.toJSON(), ContentType.APPLICATION_JSON);
        } else if (httpMethod.equals(GET)) {
            request = Request.Get(uri);
        } else if (httpMethod.equals(PUT)) {
            request = Request.Put(uri);
            request.bodyString(payload.toJSON(), ContentType.APPLICATION_JSON);
        } else if (httpMethod.equals(DELETE)) {
            request = Request.Delete(uri);
        } else {
            throw new
                    IllegalArgumentException(
                    String.format("Schedule requests support POST/GET/DELETE/PUT " +
                                          "HTTP %s Method passed", httpMethod));
        }

        return request.config(CoreProtocolPNames.USER_AGENT, USER_AGENT)
                .addHeader(CONTENT_TYPE_KEY, versionedAcceptHeader(version))
                .addHeader(ACCEPT_KEY, versionedAcceptHeader(version));
    }

    /*
    Base request for all API tag operations
    Suppressing warnings until more of schedule API is implemented
     */
    private Request tagRequest(@SuppressWarnings("SameParameterValue") String path,
                               @SuppressWarnings("SameParameterValue") String httpMethod) {
        URI uri = baseURI.resolve(path);
        Request request;

        if (httpMethod.equals(GET)) {
            request = Request.Get(uri);
        } else if (httpMethod.equals(PUT)) {
            request = Request.Put(uri);
        } else if (httpMethod.equals(DELETE)) {
            request = Request.Delete(uri);
        } else {
            throw new
                    IllegalArgumentException(
                    String.format("tag requests support GET/PUT/DELETE " +
                            "HTTP %s Method passed", httpMethod));
        }

        return request.config(CoreProtocolPNames.USER_AGENT, USER_AGENT)
                .addHeader(CONTENT_TYPE_KEY, versionedAcceptHeader(version))
                .addHeader(ACCEPT_KEY, versionedAcceptHeader(version));
    }

    private Request tagAddRemoveDeviceRequest(AddRemoveDeviceFromTagPayload payload, @SuppressWarnings("SameParameterValue") String path) {
        Preconditions.checkArgument(payload != null, "Payload is required when adding and/or removing devices from a tag");
        URI uri = baseURI.resolve(path);
        Request request = Request.Post(uri);
        request.bodyString(payload.toJSON(), ContentType.APPLICATION_JSON);

        return request.config(CoreProtocolPNames.USER_AGENT, USER_AGENT)
                .addHeader(ACCEPT_KEY, versionedAcceptHeader(version));
    }

    private Request tagBatchRequest(BatchModificationPayload payload, @SuppressWarnings("SameParameterValue") String path) {
        Preconditions.checkArgument(payload != null, "Payload is required when performing batch modification of tags");
        URI uri = baseURI.resolve(path);
        Request request = Request.Post(uri);
        request.bodyString(payload.toJSON(), ContentType.APPLICATION_JSON);

        return request.config(CoreProtocolPNames.USER_AGENT, USER_AGENT)
                .addHeader(ACCEPT_KEY, versionedAcceptHeader(version));
    }

    /*
    Execute a standard request for which the expected response is HttpResponse
     */
    private HttpResponse executeStandardRequest(Request request) throws IOException{
        Executor executor = Executor.newInstance()
                .auth(uaHost, appKey, appSecret)
                .authPreemptive(uaHost);
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing standard request %s", request));
        }

        return executor.execute(request).returnResponse();
    }

    /*
    Execute the push request and log errors.
     */
    private APIClientResponse<APIPushResponse> executePushRequest(Request request) throws IOException {
        Executor executor = Executor.newInstance()
                                    .auth(uaHost, appKey, appSecret)
                                    .authPreemptive(uaHost);
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing push request %s", request));
        }

        return executor.execute(request).handleResponse(new PushAPIResponseHandler());
    }

    /**
     * Validates a request with the Urban Airship API. This does not result
     * in a push being sent through the system. Useful for development.
     *
     * @param payload PushPayload for the message to be validated.
     * @return APIClientResponse <<T>APIPushResponse</T>> API response for this request.
     * @throws IOException
     */
    public APIClientResponse<APIPushResponse> validate(PushPayload payload) throws IOException {
        Request request = pushRequest(payload, API_VALIDATE_PATH);
        return executePushRequest(request);
    }

    /**
     * Send request to Urban Airship API to push a message immediately with
     * the parameters setup in the PushPayload.
     *
     * @param payload PushPayload for the message to be sent through the API.
     * @return APIClientResponse <<T>APIPushResponse</T> response for this request.
     * @throws IOException
     */
    public APIClientResponse<APIPushResponse> push(PushPayload payload) throws IOException {
        Preconditions.checkArgument(payload != null, "Payload required when executing a push operation");
        Request request = pushRequest(payload, API_PUSH_PATH);
        return executePushRequest(request);
    }

    /*
    Execute the push request and log errors.
     */
    private APIClientResponse<APIScheduleResponse> executeScheduleRequest(Request request) throws IOException {
        Executor executor = Executor.newInstance()
                                    .auth(uaHost, appKey, appSecret)
                                    .authPreemptive(uaHost);
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing schedule request %s", request));
        }

        return executor.execute(request).handleResponse(new ScheduleAPIResponseHandler());
    }

    /*
    Execute the list schedule request and log errors.
     */
    private APIClientResponse<APIListScheduleResponse> executeListScheduleRequest(Request request) throws IOException {
        Executor executor = Executor.newInstance()
                                    .auth(uaHost, appKey, appSecret)
                                    .authPreemptive(uaHost);
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list schedule request %s", request));
        }

        return executor.execute(request).handleResponse(new ListScheduleAPIResponseHandler());
    }

    /*
    Execute the list tags request and log errors.
     */
    private APIClientResponse<APIListTagsResponse> executeListTagsRequest(Request request) throws IOException {
        Executor executor = Executor.newInstance()
                .auth(uaHost, appKey, appSecret)
                .authPreemptive(uaHost);
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list tags request %s", request));
        }

        return executor.execute(request).handleResponse(new ListTagsAPIResponseHandler());
    }

    /**
     * Send a scheduled push request to the Urban Airship API to be delivered
     * according to the parameters setup in the schedule payload.
     *
     * @param payload A schedule payload
     * @return APIClientResponse <<T>APIScheduleResponse</T>>
     * @throws IOException
     */
    public APIClientResponse<APIScheduleResponse> schedule(SchedulePayload payload) throws IOException {
        Preconditions.checkArgument(payload != null, "Payload required when scheduling a push request");
        Request request = scheduleRequest(payload, API_SCHEDULE_PATH, POST);
        return executeScheduleRequest(request);
    }

    /**
     * Send a list schedule request to the Urban Airship API.
     *
     * @return APIClientResponse <<T>APIListScheduleResponse</T>>
     * @throws IOException
     */
    public APIClientResponse<APIListScheduleResponse> listSchedules() throws IOException {
        Request request = scheduleRequest(null, API_SCHEDULE_PATH, GET);
        return executeListScheduleRequest(request);
    }

    public APIClientResponse<APIListScheduleResponse> listSchedules(String start, int limit, String order) throws IOException {
        String path = "/api/schedules" + "?" + "start=" + start + "&limit=" + limit +"&order=" + order;
        Request request = scheduleRequest(null, path, GET);
        return executeListScheduleRequest(request);
    }

    public APIClientResponse<APIListScheduleResponse> listSchedules(String next_page) throws IOException, URISyntaxException {
        URI np = new URI(next_page);
        Request request = scheduleRequest(null, np.getPath() + "?" + np.getQuery(), GET);
        return executeListScheduleRequest(request);
    }

    /**
     * Sends a tag request to the Urban Airship API.
     *
     * @return APIClientResponse <<T>APIListTagResponse</T>>
     * @throws IOException
     */
    public APIClientResponse<APIListTagsResponse> listTags() throws IOException {
        Request request = tagRequest(API_TAGS_PATH, GET);
        return executeListTagsRequest(request);
    }

    public HttpResponse createTag(String tag) throws IOException {
        Request request = tagRequest(API_TAGS_PATH + tag, PUT);
        return executeStandardRequest(request);
    }

    public HttpResponse deleteTag(String tag) throws IOException {
        Request request = tagRequest(API_TAGS_PATH + tag, DELETE);
        return executeStandardRequest(request);
    }

    public HttpResponse addRemoveDevicesFromTag(String tag, AddRemoveDeviceFromTagPayload payload) throws IOException {
        Preconditions.checkArgument(payload != null, "Payload is required when adding and/or removing devices from a tag");
        Request request = tagAddRemoveDeviceRequest(payload, API_TAGS_PATH + tag);
        return executeStandardRequest(request);
    }

    public HttpResponse batchModificationofTags(BatchModificationPayload payload) throws IOException {
        Preconditions.checkArgument(payload != null, "Payload is required when performing batch modification of tags");
        Request request = tagBatchRequest(payload, API_TAGS_BATCH_PATH);
        return executeStandardRequest(request);
    }

    /*
    Object methods
     */
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("APIClient");
        stringBuilder.append("\nAppKey:");
        stringBuilder.append(appKey);
        stringBuilder.append("\nAppSecret:");
        stringBuilder.append(appSecret);
        return stringBuilder.toString();
    }

    /**
     * Builder for the APIClient.
     */
    public static class Builder {

        private String key;
        private String secret;
        private String baseURI;
        private Number version;

        private Builder(){
            baseURI = "https://go.urbanairship.com";
            version = 3;
        }

        /**
         * The application key that corresponds with an application on
         * Urban Airship
         * @param key String Application key.
         * @return This builder.
         */
        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        /**
         * The application master secret that matches the application key and
         * corresponds with the application on Urban Airship
         * @param appSecret String Application master secret
         * @return This builder.
         */
        public Builder setSecret(String appSecret) {
            this.secret = appSecret;
            return this;
        }

        /**
         * Base URI for the APIClient.
         * @param URI String Base URI
         * @return This builder.
         */
        public Builder setBaseURI(String URI){
            this.baseURI = URI;
            return this;
        }

        /**
         * API version to work with.
         * @param version Number Version
         * @return This builder.
         */
        public Builder setVersion(Number version){
            this.version = version;
            return this;
        }

        /**
         * Build the APIClient using the given key, secret, baseURI and version.
         * @return APIClient
         */
        public APIClient build() {
            return new APIClient(key, secret, baseURI, version);
        }

    }
}
