package com.urbanairship.api.channel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.model.subscriptionlist.SubscriptionListPayload;
import com.urbanairship.api.channel.model.subscriptionlist.SubscriptionListResponse;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class SubscriptionListRequest implements Request<SubscriptionListResponse> {
    private final ObjectMapper MAPPER = ChannelObjectMapper.getInstance();
    private final static String API_CHANNELS_SUBSCRIPTION_LIST = "/api/channels/subscription_lists/";
    private final String path;
    private final SubscriptionListPayload payload;

    private SubscriptionListRequest(String path, SubscriptionListPayload payload) {
        this.path = path;
        this.payload = payload;
    }

    /**
     * Create a new channel subscription list request.
     *
     * @param payload SubscriptionListPayload payload
     * @return SubscriptionListRequest
     */
    public static SubscriptionListRequest newRequest(SubscriptionListPayload payload) {
        return new SubscriptionListRequest(API_CHANNELS_SUBSCRIPTION_LIST, payload);
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
    public ResponseParser<SubscriptionListResponse> getResponseParser() {
        return new ResponseParser<SubscriptionListResponse>() {
            @Override
            public SubscriptionListResponse parse(String response) throws IOException {
                return MAPPER.readValue(response, SubscriptionListResponse.class);
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
