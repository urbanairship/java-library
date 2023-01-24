package com.urbanairship.api.nameduser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.model.attributes.ChannelAttributesPayload;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.common.model.GenericResponse;
import com.urbanairship.api.nameduser.model.NamedUserScopedPayload;
import org.apache.http.entity.ContentType;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class NamedUserScopedRequest implements Request<GenericResponse> {

    private final ObjectMapper MAPPER = ChannelObjectMapper.getInstance();
    private final static String API_CHANNELS_ATTRIBUTES = "/api/named_users/scoped/";
    private final String path;
    private final NamedUserScopedPayload payload;

    private NamedUserScopedRequest(String path, String namedUserId, NamedUserScopedPayload payload) {
        this.path = path + namedUserId;
        this.payload = payload;
    }

    /**
     * Create a new Named User Scoped request.
     *
     * @param payload NamedUserScopedPayload payload
     * @param namedUserId String namedUserId
     * @return NamedUserScopedRequest
     */
    public static NamedUserScopedRequest newRequest(String namedUserId, NamedUserScopedPayload payload) {
        return new NamedUserScopedRequest(API_CHANNELS_ATTRIBUTES, namedUserId, payload);
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getRequestBody() {
        return payload.toJSON();
    }

    @Override
    public ContentType getContentType() {
        return ContentType.APPLICATION_JSON;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, UA_VERSION_JSON);
        return headers;
    }

    @Override
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, path);
    }

    @Override
    public ResponseParser<GenericResponse> getResponseParser() {
        return response -> MAPPER.readValue(response, GenericResponse.class);
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
