package com.urbanairship.api.createandsend;

/*
 * Copyright (c) 2013-2022.  Urban Airship and Contributors
 */

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.createandsend.model.notification.CreateAndSendSchedulePayload;
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
 * The PushRequest class builds schedule create and send requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class CreateAndSendScheduleRequest implements Request<PushResponse> {

    private final static String API_SCHEDULE_PATH = "/api/schedules/create-and-send";

    private final List<CreateAndSendSchedulePayload> payloads = new ArrayList<>();

    private CreateAndSendScheduleRequest(CreateAndSendSchedulePayload payload) {
        Preconditions.checkNotNull(payload, "Payload required when creating an create and send schedule request");
        this.payloads.add(payload);
    }

    private CreateAndSendScheduleRequest(List<CreateAndSendSchedulePayload> payloadList) {
        Preconditions.checkNotNull(payloadList, "Payload required when creating a push request");
        if (payloadList.isEmpty()) {
            throw new IllegalStateException("Payload list cannot be empty");
        }
        this.payloads.addAll(payloadList);
    }

    /**
     * Create a CreateAndSendSchedule request.
     *
     * @param payload CreateAndSendSchedulePayload
     * @return CreateAndSendScheduleRequest
     */
    public static CreateAndSendScheduleRequest newRequest(CreateAndSendSchedulePayload payload) {
        return new CreateAndSendScheduleRequest(payload);
    }

    /**
     * Create a CreateAndSendSchedule request.
     *
     * @param payloadList List of CreateAndSendSchedule objects
     * @return CreateAndSendScheduleRequest
     */
    public static CreateAndSendScheduleRequest newRequest(List<CreateAndSendSchedulePayload> payloadList) {
        return new CreateAndSendScheduleRequest(payloadList);
    }

    /**
     * Add additional payloads to a batch CreateAndSendSchedule request
     *
     * @param newPayload CreateAndSendScheduleRequest
     * @return CreateAndSendScheduleRequest
     */
    public CreateAndSendScheduleRequest addPayload(CreateAndSendSchedulePayload newPayload) {
        Preconditions.checkNotNull(newPayload, "Payload required when adding to a CreateAndSendSchedule request");
        payloads.add(newPayload);
        return this;
    }

    /**
     * Add additional payloads to a batch CreateAndSendSchedule request
     *
     * @param newPayloads List of CreateAndSendSchedule objects
     * @return CreateAndSendScheduleRequest
     */
    public CreateAndSendScheduleRequest addPayloads(List<CreateAndSendSchedulePayload> newPayloads) {
        Preconditions.checkNotNull(newPayloads, "Payload required when adding to a CreateAndSendSchedule request");
        if (newPayloads.isEmpty()) {
            throw new IllegalStateException("Payload list cannot be empty");
        }
        payloads.addAll(newPayloads);
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

        for (CreateAndSendSchedulePayload pushPayload : this.payloads) {
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
        return RequestUtils.resolveURI(baseUri, API_SCHEDULE_PATH);
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
