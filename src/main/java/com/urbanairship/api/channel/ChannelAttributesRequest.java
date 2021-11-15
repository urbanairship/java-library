package com.urbanairship.api.channel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.model.ChannelAttributesResponse;
import com.urbanairship.api.channel.model.attributes.ChannelAttributesPayload;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class ChannelAttributesRequest implements Request<ChannelAttributesResponse> {

    private final ObjectMapper MAPPER = ChannelObjectMapper.getInstance();
    private final static String API_CHANNELS_ATTRIBUTES = "/api/channels/attributes";
    private final String path;
    private final ChannelAttributesPayload payload;

    private ChannelAttributesRequest(String path, ChannelAttributesPayload payload) {
        this.path = path;
        this.payload = payload;
    }

    /**
     * Create a new channel attribute request.
     *
     * @param payload ChannelAttributesPayload payload
     * @return ChannelAttributesRequest
     */
    public static ChannelAttributesRequest newRequest(ChannelAttributesPayload payload) {
        return new ChannelAttributesRequest(API_CHANNELS_ATTRIBUTES, payload);
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
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, path);
    }

    @Override
    public ResponseParser<ChannelAttributesResponse> getResponseParser() {
        return new ResponseParser<ChannelAttributesResponse>() {
            @Override
            public ChannelAttributesResponse parse(String response) throws IOException {
                return MAPPER.readValue(response, ChannelAttributesResponse.class);
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
