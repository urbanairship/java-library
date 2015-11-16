package com.urbanairship.api.nameduser;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.nameduser.parse.NamedUserObjectMapper;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NamedUserTagRequest implements Request<String> {

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

    public static NamedUserTagRequest createNamedUserTagRequest() {
        return new NamedUserTagRequest();
    }

    private NamedUserTagRequest() {}

    public NamedUserTagRequest addNamedUser(String channel) {
        return addNamedUsers(channel);
    }

    public NamedUserTagRequest addNamedUsers(String... channels) {
        return addNamedUsers(new HashSet<String>(Arrays.asList(channels)));
    }

    public NamedUserTagRequest addNamedUsers(Set<String> namedUsers) {
        appendMapValues(NAMED_USER_AUDIENCE_KEY, namedUsers, this.audience);
        return this;
    }

    public NamedUserTagRequest addTags(String tagGroup, Set<String> tags) {
        appendMapValues(tagGroup, tags, this.addTags);
        return this;
    }

    public NamedUserTagRequest removeTags(String tagGroup, Set<String> tags) {
        appendMapValues(tagGroup, tags, this.removeTags);
        return this;
    }

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
        headers.put(HttpHeaders.ACCEPT, UA_VERSION);
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
        Preconditions.checkArgument(payload.containsKey(ADD_KEY) || payload.containsKey(REMOVE_KEY) || payload.containsKey(SET_KEY), "Audience required when executing a named user tag operation");
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
    public ResponseParser<String> getResponseParser() {
        return new ResponseParser<String>() {
            @Override
            public String parse(String response) throws IOException {
                return response;
            }
        };
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