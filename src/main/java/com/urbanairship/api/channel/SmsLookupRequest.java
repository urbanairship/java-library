package com.urbanairship.api.channel;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.model.ChannelResponse;
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

public class SmsLookupRequest implements Request<ChannelResponse> {
    private final static String API_SMS_CHANNEL = "/api/channels/sms/";

    private final String path;

    private SmsLookupRequest(String path) {
        this.path = path;
    }

    public static SmsLookupRequest newRequest(String msisdn, String sender) {
        Preconditions.checkNotNull(msisdn, "msisdn cannot be null.");
        Preconditions.checkNotNull(sender, "Sender cannot be null.");

        return new SmsLookupRequest(API_SMS_CHANNEL + "/" + msisdn + "/" + sender);
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.GET;
    }

    @Override
    public String getRequestBody() {
        return null;
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
    public ResponseParser<ChannelResponse> getResponseParser() {
        return new ResponseParser<ChannelResponse>() {
            @Override
            public ChannelResponse parse(String response) throws IOException {
                return ChannelObjectMapper.getInstance().readValue(response, ChannelResponse.class);
            }
        };
    }

    @Override
    public boolean bearerTokenAuthRequired() {
        return false;
    }
}
