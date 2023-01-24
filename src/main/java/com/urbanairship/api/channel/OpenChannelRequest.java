package com.urbanairship.api.channel;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.model.OpenChannelResponse;
import com.urbanairship.api.channel.model.open.OpenChannelPayload;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class OpenChannelRequest implements Request<OpenChannelResponse> {

    private final static String API_OPEN_CHANNEL = "/api/channels/open/";

    private final OpenChannelPayload payload;

    private OpenChannelRequest(OpenChannelPayload payload) {
        Preconditions.checkNotNull(payload, "Payload must not be null to create an open channel request");
        this.payload = payload;
    }

    public static OpenChannelRequest newRequest(OpenChannelPayload payload) {
        return new OpenChannelRequest(payload);
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
        return RequestUtils.resolveURI(baseUri, API_OPEN_CHANNEL);
    }

    @Override
    public ResponseParser<OpenChannelResponse> getResponseParser() {
        return response -> ChannelObjectMapper.getInstance().readValue(response, OpenChannelResponse.class);
    }

    @Override
    public boolean bearerTokenAuthRequired() {
        return false;
    }

    @Override
    public boolean canUseBearerTokenAuth() {
        return false;
    }
}
