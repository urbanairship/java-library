/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.nameduser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.common.model.GenericResponse;
import com.urbanairship.api.nameduser.parse.NamedUserObjectMapper;
import org.apache.http.entity.ContentType;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The NamedUserTagRequest class builds named user tag mutation requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class NamedUserTagRequest implements Request<GenericResponse> {

    private final static String API_NAMED_USERS_TAGS = "/api/named_users/tags/";
    private static final String NAMED_USER_AUDIENCE_KEY = "named_user_id";
    private static final String AUDIENCE_KEY = "audience";
    private static final String ADD_KEY = "add";
    private static final String REMOVE_KEY = "remove";
    private static final String SET_KEY = "set";

    private final Map<String, Set<String>> audience = new HashMap<String, Set<String>>();
    private final Map<String, Set<String>> addTags = new HashMap<String, Set<String>>();
    private final Map<String, Set<String>> removeTags = new HashMap<String, Set<String>>();
    private final Map<String, Set<String>> setTags = new HashMap<String, Set<String>>();

    /**
     * Create new named user tag mutations request.
     *
     * @return NamedUserTagRequest
     */
    public static NamedUserTagRequest newRequest() {
        return new NamedUserTagRequest();
    }

    private NamedUserTagRequest() {}

    /**
     * Add a named user to the request audience.
     *
     * @param namedUser String
     * @return NamedUserTagRequest
     */
    public NamedUserTagRequest addNamedUser(String namedUser) {
        return addNamedUsers(namedUser);
    }

    /**
     * Add multiple named users to the request audience.
     *
     * @param namedUsers String... of named users
     * @return NamedUserTagRequest
     */
    public NamedUserTagRequest addNamedUsers(String... namedUsers) {
        return addNamedUsers(new HashSet<String>(Arrays.asList(namedUsers)));
    }
    /**
     * Add multiple named users to the request audience.
     *
     * @param namedUsers Set of named users
     * @return NamedUserTagRequest
     */
    public NamedUserTagRequest addNamedUsers(Set<String> namedUsers) {
        appendMapValues(NAMED_USER_AUDIENCE_KEY, namedUsers, this.audience);
        return this;
    }

    /**
     * Add tag group and tags to be added to named user.
     *
     * @param tagGroup String
     * @param tags Set of tags
     * @return NamedUserTagRequest
     */
    public NamedUserTagRequest addTags(String tagGroup, Set<String> tags) {
        appendMapValues(tagGroup, tags, this.addTags);
        return this;
    }

    /**
     * Add tag group and tags to be removed from named user.
     *
     * @param tagGroup String
     * @param tags Set of tags
     * @return NamedUserTagRequest
     */
    public NamedUserTagRequest removeTags(String tagGroup, Set<String> tags) {
        appendMapValues(tagGroup, tags, this.removeTags);
        return this;
    }

    /**
     * Add tag group and tags to be set to named user.
     *
     * @param tagGroup String
     * @param tags Set of tags
     * @return NamedUserTagRequest
     */
    public NamedUserTagRequest setTags(String tagGroup, Set<String> tags) {
        appendMapValues(tagGroup, tags, this.setTags);
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
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getRequestBody() {
        final Map<String, Map<String, Set<String>>> payload = new HashMap<String, Map<String, Set<String>>>();

        payload.put(AUDIENCE_KEY, audience);
        if (!addTags.isEmpty()) {
            payload.put(ADD_KEY, addTags);
        }

        if (!removeTags.isEmpty()) {
            payload.put(REMOVE_KEY, removeTags);
        }

        if (!setTags.isEmpty()) {
            payload.put(SET_KEY, setTags);
        }

        Preconditions.checkArgument(payload.containsKey(AUDIENCE_KEY), "Audience required when executing a named user tag operation");
        Preconditions.checkArgument(payload.containsKey(ADD_KEY) || payload.containsKey(REMOVE_KEY) || payload.containsKey(SET_KEY), "Add, remove, or set operation required when executing a named user tag operation");
        if (payload.containsKey(SET_KEY)) {
            Preconditions.checkArgument(!payload.containsKey(REMOVE_KEY) && !payload.containsKey(ADD_KEY));
        }

        try {
            return NamedUserObjectMapper.getInstance().writeValueAsString(payload);
        } catch (Exception e) {
            return "{ \"exception\" : \"" + e.getClass().getName() + "\", \"message\" : \"" + e.getMessage() + "\" }";
        }
    }

    @Override
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, API_NAMED_USERS_TAGS);
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

    private static void appendMapValues(String key, Set<String> values, Map<String, Set<String>> map) {
        if (!map.containsKey(key)) {
            map.put(key, values);
        } else {
            Set<String> newSet = map.get(key);
            newSet.addAll(values);
            map.put(key, newSet);
        }
    }
}