/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.staticlists;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.staticlists.model.StaticListView;
import com.urbanairship.api.staticlists.parse.StaticListsObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * The StaticListLookupRequest class builds static list lookup requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class StaticListLookupRequest implements Request<StaticListView> {
    private final static String API_LISTS_PATH = "/api/lists/";
    private final String path;

    private StaticListLookupRequest(String path) {
        this.path = path;
    }

    /**
     * Create a new request for single list lookup.
     *
     * @param name The name of the list as a string.
     * @return StaticListLookupRequest
     */
    public static StaticListLookupRequest newRequest(String name) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(name), "List name cannot be null.");
        return new StaticListLookupRequest(API_LISTS_PATH + name);
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
        return Request.HttpMethod.GET;
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
    public ResponseParser<StaticListView> getResponseParser() {
        return new ResponseParser<StaticListView>() {
            @Override
            public StaticListView parse(String response) throws IOException {
                return StaticListsObjectMapper.getInstance().readValue(response, StaticListView.class);
            }
        };
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