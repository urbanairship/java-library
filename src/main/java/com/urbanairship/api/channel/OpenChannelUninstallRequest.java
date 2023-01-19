package com.urbanairship.api.channel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.model.ChannelUninstallResponse;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.common.parse.APIParsingException;
import org.apache.http.entity.ContentType;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class OpenChannelUninstallRequest implements Request<ChannelUninstallResponse> {

    private final static String API_OPEN_CHANNEL_UNINSTALL = "/api/channels/open/uninstall";
    private final static ObjectMapper MAPPER = ChannelObjectMapper.getInstance();

    private final Map<String, String> payload = new HashMap<String, String>();

    private OpenChannelUninstallRequest(String address, String openPlatformName) {
        Preconditions.checkNotNull(address, "address must not be null to uninstall an open channel");
        Preconditions.checkNotNull(openPlatformName, "openPlatformName must not be null to uninstall an open channel");
        payload.put("address", address);
        payload.put("open_platform_name", openPlatformName);
    }

    public static OpenChannelUninstallRequest newRequest(String address, String openPlatformName) {
        return new OpenChannelUninstallRequest(address, openPlatformName);
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
            return "{ \"exception\" : \"" + e.getClass().getName() + "\", \"message\" : \"" + e.getMessage() + "\" }";
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
        return RequestUtils.resolveURI(baseUri, API_OPEN_CHANNEL_UNINSTALL);
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
