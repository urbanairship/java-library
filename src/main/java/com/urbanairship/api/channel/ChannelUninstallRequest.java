/*
 * Copyright (c) 2013-2021.  Urban Airship and Contributors
 */

package com.urbanairship.api.channel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.model.ChannelUninstallPayload;
import com.urbanairship.api.channel.model.ChannelUninstallResponse;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import org.apache.http.entity.ContentType;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * The ChannelUninstallRequest class builds channels uninstall requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class ChannelUninstallRequest implements Request<ChannelUninstallResponse> {

    private final static String API_CHANNELS_UNINSTALL_PATH = "/api/channels/uninstall/";
    private final static ObjectMapper MAPPER = ChannelObjectMapper.getInstance();
    private final ChannelUninstallPayload payload;

    private ChannelUninstallRequest(ChannelUninstallPayload payload) {
        this.payload = payload;
    }

    /**
     * Create new channels uninstall request.
     *
     * @return ChannelUninstallRequest
     */
    public static ChannelUninstallRequest newRequest(ChannelUninstallPayload payload) {
        return new ChannelUninstallRequest(payload);
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
        try {
            return ChannelObjectMapper.getInstance().writeValueAsString(payload.getChannels());
        } catch (Exception ex) {
            return "{ \"exception\" : \"" + ex.getMessage() + "\" }";
        }
    }

    @Override
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, API_CHANNELS_UNINSTALL_PATH);
    }

    @Override
    public ResponseParser<ChannelUninstallResponse> getResponseParser() {
        return response -> MAPPER.readValue(response, ChannelUninstallResponse.class);
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
