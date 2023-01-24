package com.urbanairship.api.push;

/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The PushRequest class builds push and push validation requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class PushRequest implements Request<PushResponse> {

    private final static String API_PUSH_PATH = "/api/push/";
    private final static String API_VALIDATE_PATH = "/api/push/validate/";

    private final List<PushPayload> payloads = new ArrayList<>();
    private boolean validateOnly;

    private PushRequest(PushPayload payload) {
        Preconditions.checkNotNull(payload, "Payload required when creating a push request");
        this.payloads.add(payload);
    }

    private PushRequest(List<PushPayload> payloadList) {
        Preconditions.checkNotNull(payloadList, "Payload required when creating a push request");
        if (payloadList.isEmpty()) {
            throw new IllegalStateException("Payload list cannot be empty");
        }
        this.payloads.addAll(payloadList);
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
     * Create a push request.
     *
     * @param payloadList List of PushPayload objects
     * @return PushRequest
     */
    public static PushRequest newRequest(List<PushPayload> payloadList) {
        return new PushRequest(payloadList);
    }

    /**
     * Add additional payloads to a batch push request
     *
     * @param newPayload PushPayload
     * @return PushRequest
     */
    public PushRequest addPayload(PushPayload newPayload) {
        Preconditions.checkNotNull(newPayload, "Payload required when adding to a push request");
        payloads.add(newPayload);
        return this;
    }

    /**
     * Add additional payloads to a batch push request
     *
     * @param newPayloads List of PushPayload objects
     * @return PushRequest
     */
    public PushRequest addPayloads(List<PushPayload> newPayloads) {
        Preconditions.checkNotNull(newPayloads, "Payload required when adding to a push request");
        if (newPayloads.isEmpty()) {
            throw new IllegalStateException("Payload list cannot be empty");
        }
        payloads.addAll(newPayloads);
        return this;
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
        headers.put(HttpHeaders.ACCEPT, UA_VERSION_JSON);
        return headers;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getRequestBody() {
        if (this.payloads.size() == 1) {
            return this.payloads.get(0).toJSON();
        }

        ObjectMapper mapper = new ObjectMapper();

        ArrayNode arrayNode = mapper.createArrayNode();

        for (PushPayload pushPayload : this.payloads) {
            try {
                JsonNode pushJson = mapper.readTree(pushPayload.toJSON());
                arrayNode.add(pushJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return arrayNode.toString();
    }

    @Override
    public URI getUri(URI baseUri) {
        String path = validateOnly ? API_VALIDATE_PATH : API_PUSH_PATH;
        return RequestUtils.resolveURI(baseUri, path);
    }

    @Override
    public ResponseParser<PushResponse> getResponseParser() {
        return response -> PushObjectMapper.getInstance().readValue(response, PushResponse.class);
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
