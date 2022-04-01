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
import com.urbanairship.api.staticlists.parse.StaticListsObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * The StaticListRequest class builds a static list creation request to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class StaticListRequest implements Request<GenericResponse> {
    private final static String API_LISTS_PATH = "/api/lists/";
    private final String path;

    private static final String NAME_KEY = "name";
    private static final String DESCRIPTION_KEY = "description";
    private static final String EXTRAS_KEY = "extra";
    private final Map<String, String> extras = new HashMap<String,String>();
    private final Map<String, Object> payload = new HashMap<String, Object>();

    private StaticListRequest(String path, String name) {
        this.path = path;
        this.payload.put(NAME_KEY, name);
    }

    /**
     * Build a static list creation request.
     *
     * @param name The name of the list as a string.
     * @return StaticListRequest
     */
    public static StaticListRequest newRequest(String name) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(name), "List name cannot be empty.");
        return new StaticListRequest(API_LISTS_PATH, name);
    }

    /**
     * Create a static list update request.
     *
     * @param name The name of the list as a string.
     * @return StaticListRequest
     */
    public static StaticListRequest newUpdateRequest(String name) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(name), "List name cannot be empty.");
        return new StaticListRequest(API_LISTS_PATH + name, name);
    }

    /**
     * Set the description of the static list.
     *
     * @param description String
     * @return StaticListRequest
     */
    public StaticListRequest setDescription(String description) {
        this.payload.put(DESCRIPTION_KEY, description);
        return this;
    }

    /**
     * Add a key-value pair to the extra mapping.
     *
     * @param key String
     * @param val String
     * @return StaticListRequest
     */
    public StaticListRequest addExtra(String key, String val) {
        this.extras.put(key, val);
        return this;
    }

    /**
     * Add all key-value pairs to the map of extras.
     *
     * @param entries Map of Strings
     * @return StaticListCreationRequest
     */
    public StaticListRequest addAllExtras(Map<String, String> entries) {
        this.extras.putAll(entries);
        return this;
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
    public Request.HttpMethod getHttpMethod() {
        if (path.equals(API_LISTS_PATH)) {
            return Request.HttpMethod.POST;
        }
        return HttpMethod.PUT;
    }

    @Override
    public String getRequestBody() {
        if (!extras.isEmpty()) {
            this.payload.put(EXTRAS_KEY, extras);
        }

        try {
            return StaticListsObjectMapper.getInstance().writeValueAsString(this.payload);
        } catch (Exception ex) {
            return "{ \"exception\" : \"" + ex.getClass().getName() + "\", \"message\" : \"" + ex.getMessage() + "\" }";
        }
    }

    @Override
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, path);
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