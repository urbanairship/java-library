package com.urbanairship.api.channel;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.model.ChannelResponse;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class ChannelRequest implements Request<ChannelResponse> {

    private final static String API_CHANNELS_LIST = "/api/channels/";

    private final String path;

    private ChannelRequest(String path) {
        this.path = path;
    }

    public static ChannelRequest createListRequest(String channel) {
        return new ChannelRequest(API_CHANNELS_LIST + channel);
    }

    public static ChannelRequest createListAllRequest() {
        return new ChannelRequest(API_CHANNELS_LIST);
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
    public HTTPMethod getHTTPMethod() {
        return HTTPMethod.GET;
    }

    @Override
    public String getRequestBody() {
        return null;
    }

    @Override
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, path);
    }

    @Override
    public ResponseParser<ChannelResponse> getResponseParser() {
        return new ResponseParser<ChannelResponse>() {
            @Override
            public ChannelResponse parse(String response) throws IOException {
                return ChannelObjectMapper.getInstance().readValue(response, ChannelResponse.class);
            }
        };
    }
}
