package com.urbanairship.api.channel.model.sms;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the Request to be used for updating a sms channel.
 */
public class UpdateSmsChannelRequest implements Request<String> {

    private final static String API_UPDATE_SMS_CHANNEL = "/api/channels/sms/";
    private final String path;
    private final UpdateSmsChannel payload;

    private UpdateSmsChannelRequest(String path, UpdateSmsChannel payload) {
        this.path = path;
        this.payload = payload;
    }

    public static UpdateSmsChannelRequest newRequest(String channelId, UpdateSmsChannel payload) {
        Preconditions.checkNotNull(payload, "Payload must not be null to create an RegisterEmail channel request");
        Preconditions.checkNotNull(channelId, "ChannelId must not be null to create an RegisterEmail channel request");
        return new UpdateSmsChannelRequest(API_UPDATE_SMS_CHANNEL + channelId, payload);
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.PUT;
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