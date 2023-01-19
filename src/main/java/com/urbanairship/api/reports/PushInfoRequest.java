/*
 * Copyright (c) 2013-2016. Urban Airship and Contributors
 */

package com.urbanairship.api.reports;

import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.reports.model.PushInfoResponse;
import com.urbanairship.api.reports.parse.ReportsObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * The SinglePushInfoRequest class builds a request to the individual push
 * response statistics request to be executed in the
 * {@link com.urbanairship.api.client.UrbanAirshipClient}
 */
public class PushInfoRequest implements Request<PushInfoResponse> {
    private final static String API_PUSH_RESPONSE_STATS = "/api/reports/responses/";

    private final String path;

    private PushInfoRequest(String path) {
        this.path = path;
    }

    /**
     * Request individual push response statistics for a UUID.
     *
     * @param uuid String
     * @return PushInfoRequest
     */
    public static PushInfoRequest newRequest(String uuid) {
        return new PushInfoRequest(API_PUSH_RESPONSE_STATS + uuid);
    }

    @Override
    public ContentType getContentType() {
        return ContentType.APPLICATION_JSON;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, UA_VERSION_JSON);
        return headers;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.GET;
    }

    @Override
    public String getRequestBody() {
        return null;
    }

    @Override
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, path);
    }

    @Override
    public ResponseParser<PushInfoResponse> getResponseParser() {
        return response -> ReportsObjectMapper.getInstance().readValue(response, PushInfoResponse.class);
    }

    @Override
    public boolean bearerTokenAuthRequired() {
        return false;
    }

    @Override
    public boolean canUseBearerTokenAuth() {
        return true;
    }
}
