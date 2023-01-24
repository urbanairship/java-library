package com.urbanairship.api.channel.model.email;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.common.model.GenericResponse;
import com.urbanairship.api.common.parse.APIParsingException;
import org.apache.http.entity.ContentType;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class SuppressEmailChannelRequest implements Request<GenericResponse> {

    private final static String API_EMAIL_CHANNEL_SUPPRESS = "/api/channels/email/suppress";
    private final Map<String, String> payload = new HashMap<>();

    private SuppressEmailChannelRequest(String address, String reason) {
        Preconditions.checkNotNull(address, "address must not be null to create a SuppressEmailChannel request");
        Preconditions.checkNotNull(reason, "reason must not be null to create a SuppressEmailChannel request");
        payload.put("address", address);
        payload.put("reason", reason);
    }

    public static SuppressEmailChannelRequest newRequest(String address, String reason) {
        return new SuppressEmailChannelRequest(address, reason);
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getRequestBody() {
        try {
            return ChannelObjectMapper.getInstance().writeValueAsString(payload);
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
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
    public URI getUri(URI baseUri) throws URISyntaxException {
        return RequestUtils.resolveURI(baseUri, API_EMAIL_CHANNEL_SUPPRESS);
    }

    @Override
    public ResponseParser<GenericResponse> getResponseParser() {
        return response -> ChannelObjectMapper.getInstance().readValue(response, GenericResponse.class);
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