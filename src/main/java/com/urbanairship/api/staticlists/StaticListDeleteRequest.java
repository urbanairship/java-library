/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.staticlists;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.common.model.GenericResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * The StaticListDeleteRequest class builds static list deletion requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class StaticListDeleteRequest implements Request<GenericResponse> {
    private final static String API_LISTS_PATH = "/api/lists/";
    private final String name;

    private StaticListDeleteRequest(String name) {
        this.name = name;
    }

    /**
     * Create a static list delete request.
     *
     * @param name The list to be deleted.
     * @return StaticListDeleteRequest
     */
    public static StaticListDeleteRequest newRequest(String name) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(name), "List name cannot be blank.");
        return new StaticListDeleteRequest(name);
    }

    @Override
    public ContentType getContentType() {
        return null;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.ACCEPT, UA_VERSION_JSON);
        return headers;
    }

    @Override
    public Request.HttpMethod getHttpMethod() {
        return Request.HttpMethod.DELETE;
    }

    @Override
    public String getRequestBody() {
        return null;
    }

    @Override
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, API_LISTS_PATH + name);
    }

    @Override
    public ResponseParser<GenericResponse> getResponseParser() {
        return RequestUtils.GENERIC_RESPONSE_PARSER;
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
