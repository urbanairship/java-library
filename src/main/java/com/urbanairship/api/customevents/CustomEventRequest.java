package com.urbanairship.api.customevents;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.customevents.model.CustomEventPayload;
import com.urbanairship.api.customevents.model.CustomEventResponse;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class CustomEventRequest implements Request<CustomEventResponse> {

    private final static String API_CUSTOM_EVENTS_PATH = "/api/custom-events/";

    private final CustomEventPayload payload;

    private CustomEventRequest(CustomEventPayload payload) {
        Preconditions.checkNotNull(payload, "Payload required when creating a custom-events request");
        this.payload = payload;
    }

    public static CustomEventRequest newRequest(CustomEventPayload customEventPayload) {
        return new CustomEventRequest(customEventPayload);
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
        return RequestUtils.resolveURI(baseUri, API_CUSTOM_EVENTS_PATH);
    }

    @Override
    public ResponseParser<CustomEventResponse> getResponseParser() {
        return new ResponseParser<CustomEventResponse>() {
            @Override
            public CustomEventResponse parse(String response) throws IOException {
                return PushObjectMapper.getInstance().readValue(response, CustomEventResponse.class);
            }
        };
    }

    @Override
    public boolean bearerTokenAuthRequired() {
        return true;
    }
}
