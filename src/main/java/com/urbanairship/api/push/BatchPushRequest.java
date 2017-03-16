package com.urbanairship.api.push;

/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.ning.http.util.StringUtils;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.PushResponse;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The BatchPushRequest class builds push and push validation requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class BatchPushRequest implements Request<PushResponse> {

    private final static String API_PUSH_PATH = "/api/push/";
    private final static String API_VALIDATE_PATH = "/api/push/validate/";

    private final List<PushPayload> payload = new ArrayList<>();
    private boolean validateOnly;

    private BatchPushRequest(List<PushPayload> payload) {
        Preconditions.checkNotNull(payload, "Payload required when creating a push request");
        this.payload.addAll(payload);
    }

    /**
     * Create a batch push request from a single PushPayload.
     *
     * @param singlePayload PushPayload
     * @return BatchPushRequest
     */
    public static BatchPushRequest newRequest(PushPayload singlePayload) {
        final List<PushPayload> payload = new ArrayList<>();
        payload.add(singlePayload);
        return new BatchPushRequest(payload);
    }
    /**
     * Create a batch push request from a list of PushPayloads.
     *
     * @param payloadList List<PushPayload>
     * @return BatchPushRequest
     */
    public static BatchPushRequest newRequest(List<PushPayload> payloadList) {
        return new BatchPushRequest(payloadList);
    }

    /**
     * Add additional payloads to a batch push request
     * @param newPayload
     * @return BatchPushRequest
     */
    public BatchPushRequest addRequest(PushPayload newPayload) {
        Preconditions.checkNotNull(payload, "Payload required when adding to a push request");
        payload.add(newPayload);
        return this;
    }

    /**
     * Sets if the request should only validate the payload.
     *
     * @param validateOnly {@code true} to only validate the payload, {@code false} to send the push request.
     * @return The batch push request.
     */
    public BatchPushRequest setValidateOnly(boolean validateOnly) {
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
        headers.put(HttpHeaders.ACCEPT, UA_VERSION_JSON);
        return headers;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getRequestBody() {
        StringBuilder sb = new StringBuilder("[");

        for (PushPayload pushPayload : this.payload) {
            sb.append(pushPayload.toJSON());
            sb.append(",");
        }
        // remove last comma
        sb.setLength(sb.length() - 1);

        return sb.append("]").toString();
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
