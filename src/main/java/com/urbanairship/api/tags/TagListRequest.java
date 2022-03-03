/*
 * Copyright (c) 2013-2022.  Urban Airship and Contributors
 */

package com.urbanairship.api.tags;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import org.apache.http.entity.ContentType;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * The CreateTagListRequest class builds tag list mutation requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class TagListRequest implements Request<String> {

    private final static String CREATE_TAG_LIST_PATH = "/api/tag-lists";
    private static final String NAME_KEY = "name";
    private static final String DESCRIPTION_KEY = "description";
    private static final String EXTRAS_KEY = "extra";
    private static final String ADD_KEY = "add";
    private static final String REMOVE_KEY = "remove";
    private static final String SET_KEY = "set";

    private final Map<String, ImmutableSet<String>> addTags = new HashMap<String, ImmutableSet<String>>();
    private final Map<String, ImmutableSet<String>> removeTags = new HashMap<String, ImmutableSet<String>>();
    private final Map<String, ImmutableSet<String>> setTags = new HashMap<String, ImmutableSet<String>>();
    private final Map<String, String> extras = new HashMap<String,String>();

    private ObjectNode payload = JsonNodeFactory.instance.objectNode();


    /**
     * Create new TagListRequest mutation request.
     *
     * @return TagListRequest
     */
    public static TagListRequest newRequest() {
        return new TagListRequest();
    }

    /**
     * Set the name of the tag list.
     *
     * @param name String
     * @return TagListRequest
     */
    public TagListRequest setName(String name) {
        this.payload.put(NAME_KEY, name);
        return this;
    }


    /**
     * Set the description of the tag list.
     *
     * @param description String
     * @return TagListRequest
     */
    public TagListRequest setDescription(String description) {
        this.payload.put(DESCRIPTION_KEY, description);
        return this;
    }

    /**
     * Add a key-value pair to the extra mapping.
     *
     * @param key String
     * @param val String
     * @return TagListRequest
     */
    public TagListRequest addExtra(String key, String val) {
        extras.put(key, val);
        return this;
    }

    /**
     * Add tag group and tags to add to channels.
     *
     * @param tagGroup String
     * @param tags Set of Strings
     * @return TagListRequest
     * */
    public TagListRequest addTags(String tagGroup, ImmutableSet<String> tags) {
        addTags.put(tagGroup, tags);
        return this;
    }

    /**
     * Add tag group and tags to remove from channels.
     *
     * @param tagGroup String
     * @param tags Set of Strings
     * @return TagListRequest
     */
    public TagListRequest removeTags(String tagGroup, ImmutableSet<String> tags) {
        removeTags.put(tagGroup, tags);
        return this;
    }

    /**
     * Add tag group and tags to set to channels.
     *
     * @param tagGroup String
     * @param tags Set of tags
     * @return TagListRequest
     */
    public TagListRequest setTags(String tagGroup, ImmutableSet<String> tags) {
        setTags.put(tagGroup, tags);
        return this;
    }


    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getRequestBody() {

        if (!addTags.isEmpty()) {
            payload.putPOJO(ADD_KEY, addTags);        
        }

        if (!removeTags.isEmpty()) {
            payload.putPOJO(REMOVE_KEY,removeTags);        
        }

        if (!setTags.isEmpty()) {
            payload.putPOJO(SET_KEY, setTags);
        }

        if (!extras.isEmpty()){
            payload.putPOJO(EXTRAS_KEY, extras);
        }

        Preconditions.checkArgument(payload.has(NAME_KEY), "Name required when executing a tag list operation");

        try {
            return ChannelObjectMapper.getInstance().writeValueAsString(payload);
        } catch (Exception ex) {
            return "{ \"exception\" : \"" + ex.getClass().getName() + "\", \"message\" : \"" + ex.getMessage() + "\" }";
        }
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
    public URI getUri(URI baseUri) throws URISyntaxException {
        return RequestUtils.resolveURI(baseUri, CREATE_TAG_LIST_PATH);
    }

    @Override
    public ResponseParser<String> getResponseParser() {
        return response -> response;
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