/*
 * Copyright (c) 2013-2022.  Airship and Contributors
 */

package com.urbanairship.api.attributelists;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.attributelists.parse.AttributeListsObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * The AttributeListsCreateRequest class builds a attribute list creation request to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class AttributeListsCreateRequest implements Request<String> {
    private final static String ATTRIBUTE_LISTS_PATH = "/api/attribute-lists/";
    private final String path;

    private static final String NAME_KEY = "name";
    private static final String DESCRIPTION_KEY = "description";
    private static final String EXTRAS_KEY = "extra";
    private final Map<String, String> extras = new HashMap<String,String>();
    private final Map<String, Object> payload = new HashMap<String, Object>();


    private AttributeListsCreateRequest(String path, String name) {
        this.path = path;
        this.payload.put(NAME_KEY, name);
    }

    /**
     * Build a attribute list creation request.
     *
     * @param name The name of the list as a string.
     * @return AttributeListsCreateRequest
     */
    public static AttributeListsCreateRequest newRequest(String name) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(name), "List name cannot be empty.");
        Preconditions.checkArgument(name.startsWith("ua_attributes_"), "You must prefix attribute list names with 'ua_attributes_'");
        return new AttributeListsCreateRequest(ATTRIBUTE_LISTS_PATH, name);
    }

    /**
     * Set the description of the AttributeListsCreateRequest list.
     *
     * @param description String
     * @return AttributeListsCreateRequest
     */
    public AttributeListsCreateRequest setDescription(String description) {
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
    public AttributeListsCreateRequest addExtra(String key, String val) {
        this.extras.put(key, val);
        return this;
    }

    /**
     * Add all key-value pairs to the map of extras.
     *
     * @param entries Map of Strings
     * @return StaticListCreationRequest
     */
    public AttributeListsCreateRequest addAllExtras(Map<String, String> entries) {
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
        return Request.HttpMethod.POST;
    }


    @Override
    public String getRequestBody() {
        if (!extras.isEmpty()) {
            this.payload.put(EXTRAS_KEY, extras);
        }

        try {
            return AttributeListsObjectMapper.getInstance().writeValueAsString(this.payload);
        } catch (Exception ex) {
            return "{ \"exception\" : \"" + ex.getClass().getName() + "\", \"message\" : \"" + ex.getMessage() + "\" }";
        }
    }

    @Override
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, path);
    }

    @Override
    public ResponseParser<String> getResponseParser() {
        return new ResponseParser<String>() {
            @Override
            public String parse(String response) throws IOException {
                return response;
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