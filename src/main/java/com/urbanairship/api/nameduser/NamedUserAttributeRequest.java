package com.urbanairship.api.nameduser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.nameduser.model.NamedUserAttributePayload;
import com.urbanairship.api.nameduser.model.NamedUserAttributeResponse;
import com.urbanairship.api.nameduser.parse.NamedUserObjectMapper;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class NamedUserAttributeRequest implements Request<NamedUserAttributeResponse> {
    private final static String API_NAMED_USER = "/api/named_users/";
    private final static String ATTRIBUTES_END_POINT = "/attributes";
    private final static ObjectMapper MAPPER = NamedUserObjectMapper.getInstance();

    private final String namedUser;
    private final String path;
    private final NamedUserAttributePayload payload;

    private NamedUserAttributeRequest(String namedUser, NamedUserAttributePayload payload) {
        path = API_NAMED_USER + namedUser + ATTRIBUTES_END_POINT;
        this.namedUser = namedUser;
        this.payload = payload;
    }

    /**
     * Sets attributes defined in the NamedUserAttributePayload to the named user id.
     *
     * @param namedUser String
     * @param payload NamedUserAttributePayload payload
     * @return NamedUserAttributeRequest
     */
    public static NamedUserAttributeRequest newRequest(String namedUser, NamedUserAttributePayload payload) {
        Preconditions.checkNotNull(namedUser, "Named user must be set.");
        return new NamedUserAttributeRequest(namedUser, payload);
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
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, UA_VERSION_JSON);
        return headers;
    }

    @Override
    public URI getUri(URI baseUri) throws URISyntaxException {
        return RequestUtils.resolveURI(baseUri, path);
    }

    @Override
    public ResponseParser<NamedUserAttributeResponse> getResponseParser() {
        return new ResponseParser<NamedUserAttributeResponse>() {
            @Override
            public NamedUserAttributeResponse parse(String response) throws IOException {
                return MAPPER.readValue(response, NamedUserAttributeResponse.class);
            }
        };
    }

    @Override
    public boolean bearerTokenAuthRequired() {
        return false;
    }
}
