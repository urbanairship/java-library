/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.google.common.base.Preconditions;

import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.schedule.model.SchedulePayload;

import org.apache.commons.lang.StringUtils;

import org.apache.http.HttpHost;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.params.CoreProtocolPNames;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.io.IOException;

/**
 * APIClient handles HTTP requests to the Urban Airship API
 */
public class APIClient {

    /* Header keys/values */
    private final static String CONTENT_TYPE_KEY = "Content-type";
    private final static String ACCEPT_KEY = "Accept";
    private final static String UA_APPLICATION_JSON =
            "application/vnd.urbanairship+json;";

    private final static String USER_AGENT = "UrbanAirship/version0.1beta";

    /* URI Paths */
    private final static String API_PUSH_PATH = "/api/push/";
    private final static String API_VALIDATE_PATH = "/api/push/validate/";
    private final static String API_SCHEDULE_PATH = "/api/schedules/";

    /* User auth */
    private final String appKey;
    private final String appSecret;

    /* URI, version */
    private final URI baseURI;
    private final Number version;

    /* HTTP */
    private final HttpHost uaHost;
    private final static String UA_HOSTNAME = "go.urbanairship.com";

    private final static Logger logger =
            LoggerFactory.getLogger("com.urbanairship.api");

    /**
     * APIClient Builder
     * @return Builder
     */
    public static Builder newBuilder(){
        return new Builder();
    }

    private APIClient(String appKey,
                      String appSecret,
                      String baseURI,
                      Number version){
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

    /**
     * The application master secret associated with the application on Urban
     * Airship.
     * @return Application secret for this client.
     */
    public String getAppSecret() {
        return appSecret;
    }

    /**
     * The application key associated with the application on Urban Airship.
     * @return Application key for this client.
     */
    public String getAppKey() {
        return appKey;
    }

    /*
    Add the version number to the default version header.
     */
    private String versionedAcceptHeader(Number version){
        return String.format("%s version=%s", UA_APPLICATION_JSON,
                             version.toString());
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

        if (httpMethod.equals("POST")){
            request = Request.Post(uri);
            request.bodyString(payload.toJSON(), ContentType.APPLICATION_JSON);

        } else if (httpMethod.equals("GET")){
            request = Request.Get(uri);
        }
        else if (httpMethod.equals("PUT")){
            request = Request.Put(uri);
            request.bodyString(payload.toJSON(), ContentType.APPLICATION_JSON);
        }
        else if (httpMethod.equals("DELETE")){
            request = Request.Delete(uri);
        }
        else {
            throw new
                    IllegalArgumentException(
                    String.format("Schedule requests support POST/GET/DELETE/PUT " +
                                          "HTTP %s Method passed", httpMethod));
        }
        return request.config(CoreProtocolPNames.USER_AGENT, USER_AGENT)
                .addHeader(CONTENT_TYPE_KEY, versionedAcceptHeader(version))
                .addHeader(ACCEPT_KEY, versionedAcceptHeader(version));
    }

//    /*
//    Append the id to the path, pass to base schedule method
//     */
//    private Request scheduleRequest(SchedulePayload payload, String path,
//                                    String httpMethod, String id){
//        StringBuilder builder = new StringBuilder(path);
//        builder.append(String.format("/%s/", id));
//        throw new NotImplementedException("Schedule API not implemented yet");
//        return scheduleRequest(payload, builder.toString(), httpMethod);
//    }

    /*
    Execute the push request and log errors.
     */
    private APIClientResponse<APIPushResponse> executePushRequest(Request request)
            throws IOException{
        Executor executor = Executor.newInstance()
                                    .auth(uaHost, appKey, appSecret)
                                    .authPreemptive(uaHost);
        logger.debug(String.format("Executing push request %s", request));
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
    public APIClientResponse<APIPushResponse> validate(PushPayload payload) throws
            IOException {
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
        Request request = pushRequest(payload, API_PUSH_PATH);
        return executePushRequest(request);
    }


    /*
    Execute the push request and log errors.
     */
    private APIClientResponse<APIScheduleResponse> executeScheduleRequest(Request request)
        throws IOException{
        Executor executor = Executor.newInstance()
                                    .auth(uaHost, appKey, appSecret)
                                    .authPreemptive(uaHost);
        logger.debug(String.format("Executing schedule request %s", request));
        return executor.execute(request).handleResponse(new ScheduleAPIResponseHandler());
    }

    /**
     * Send a scheduled push request to the Urban Airship API to be delivered
     * according to the parameters setup in the schedule payload.
     *
     * @param payload A schedule payload
     * @return APIClientResponse <<T>APIScheduleResponse</T>>
     * @throws IOException
     */
    public APIClientResponse<APIScheduleResponse> schedule(SchedulePayload payload)
            throws IOException {
        Request request = scheduleRequest(payload, API_SCHEDULE_PATH, "POST");
        return executeScheduleRequest(request);
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
