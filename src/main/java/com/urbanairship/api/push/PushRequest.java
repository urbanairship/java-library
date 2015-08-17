package com.urbanairship.api.push;

/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.PushResponse;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * The PushRequest class builds push and push validation requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class PushRequest implements Request<PushResponse> {

    private final static String API_PUSH_PATH = "/api/push/";
    private final static String API_VALIDATE_PATH = "/api/push/validate/";

    private final PushPayload payload;
    private boolean validateOnly;

    private PushRequest(PushPayload payload) {
        Preconditions.checkNotNull(payload, "Payload required when creating a push request");
        this.payload = payload;
    }

    /**
     * Create a push request.
     *
     * @param payload PushPayload
     * @return PushRequest
     */
    public static PushRequest newRequest(PushPayload payload) {
        return new PushRequest(payload);
    }

    /**
     * Sets if the request should only validate the payload.
     *
     * @param validateOnly {@code true} to only validate the payload, {@code false} to send the push request.
     * @return The push request.
     */
    public PushRequest setValidateOnly(boolean validateOnly) {
        this.validateOnly = validateOnly;
        return this;
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
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getRequestBody() {
        return payload.toJSON();
    }

    @Override
    public URI getUri(URI baseUri) {
        String path  = validateOnly ? API_VALIDATE_PATH : API_PUSH_PATH;
        return RequestUtils.resolveURI(baseUri, path);
    }

    @Override
    public ResponseParser<PushResponse> getResponseParser() {
        return new ResponseParser<PushResponse>() {
            @Override
            public PushResponse parse(String response) throws IOException {
                return PushObjectMapper.getInstance().readValue(response, PushResponse.class);
            }
        };
    }
}
