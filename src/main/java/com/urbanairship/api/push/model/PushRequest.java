package com.urbanairship.api.push.model;

/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.client.model.APIPushResponse;
import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * The PushRequest class builds push and push validation requests to be executed in the UrbanAirshipClient
 */

public class PushRequest implements Request<APIPushResponse> {

    private final static String API_PUSH_PATH = "/api/push/";
    private final static String API_VALIDATE_PATH = "/api/push/validate/";

    private final String path;
    private final PushPayload payload;

    private PushRequest(PushPayload payload, String path) {
        Preconditions.checkNotNull(payload, "Payload required when creating a push request");

        this.path = path;
        this.payload = payload;
    }

    /**
     * Create a push request.
     *
     * @param payload PushPayload
     * @return PushRequest
     */
    public static PushRequest createPushRequest(PushPayload payload) {
        return new PushRequest(payload, API_PUSH_PATH);
    }

    /**
     * Create a push validation request.
     *
     * @param payload PushPayload
     * @return PushRequest
     */
    public static PushRequest createValidateRequest(PushPayload payload) {
        return new PushRequest(payload, API_VALIDATE_PATH);
    }

    @Override
    public ContentType getContentType() {
        return ContentType.APPLICATION_JSON;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, UA_VERSION);
        return headers;
    }

    @Override
    public HTTPMethod getHTTPMethod() {
        return HTTPMethod.POST;
    }

    @Override
    public String getRequestBody() {
        return payload.toJSON();
    }

    @Override
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, path);
    }

    @Override
    public ResponseParser<APIPushResponse> getResponseParser() {
        return new ResponseParser<APIPushResponse>() {
            @Override
            public APIPushResponse parse(String response) throws IOException {
                return APIResponseObjectMapper.getInstance().readValue(response, APIPushResponse.class);
            }
        };
    }
}
