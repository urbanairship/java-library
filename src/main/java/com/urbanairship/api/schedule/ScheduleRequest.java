package com.urbanairship.api.schedule;

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
import com.urbanairship.api.schedule.model.SchedulePayload;
import com.urbanairship.api.schedule.model.ScheduleResponse;
import com.urbanairship.api.schedule.parse.ScheduleObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The ScheduleRequest class builds scheduled push requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class ScheduleRequest implements Request<ScheduleResponse> {

    final static String API_SCHEDULE_PATH = "/api/schedules/";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final List<SchedulePayload> schedulePayloads = new ArrayList<>();
    private final String path;
    private final HttpMethod method;

    private ScheduleRequest(SchedulePayload schedulePayload, HttpMethod method, String path) {
        schedulePayloads.add(schedulePayload);
        this.path = path;
        this.method = method;
    }

    private ScheduleRequest(List<SchedulePayload> schedulePayloads, HttpMethod method, String path) {
        Preconditions.checkNotNull(schedulePayloads, "Schedule pushes required when creating a schedule request.");
        if (schedulePayloads.isEmpty()) {
            throw new IllegalStateException("Schedule payloads cannot be empty.");
        }
        this.path = path;
        this.method = method;
        this.schedulePayloads.addAll(schedulePayloads);
    }

    /**
     * Create a scheduled push request.
     *
     * @param schedulePayload SchedulePayload
     * @return ScheduleRequest
     */
    public static ScheduleRequest newRequest(SchedulePayload schedulePayload) {
        Preconditions.checkNotNull(schedulePayload, "Schedule Push may not be null");
        return new ScheduleRequest(schedulePayload, HttpMethod.POST, API_SCHEDULE_PATH);
    }

    public static ScheduleRequest newRequest(List<SchedulePayload> schedulePayloads) {
        return new ScheduleRequest(schedulePayloads, HttpMethod.POST, API_SCHEDULE_PATH);
    }

    /**
     * Create a request to update a scheduled push.
     *
     * @param schedulePayload SchedulePayload
     * @param scheduleId String
     * @return ScheduleRequest
     */
    public static ScheduleRequest newUpdateRequest(SchedulePayload schedulePayload, String scheduleId) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(scheduleId), "Schedule ID may not be mull");
        Preconditions.checkNotNull(schedulePayload, "Schedule Push may not be null");
        return new ScheduleRequest(schedulePayload, HttpMethod.PUT, API_SCHEDULE_PATH + scheduleId);
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
        return method;
    }

    @Override
    public String getRequestBody() {
        if (this.schedulePayloads.size() == 1) {
            return schedulePayloads.get(0).toJSON();
        }

        ArrayNode arrayNode = MAPPER.createArrayNode();

        for (SchedulePayload schedulePayload : this.schedulePayloads) {
            try {
                JsonNode pushJson = MAPPER.readTree(schedulePayload.toJSON());
                arrayNode.add(pushJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return arrayNode.toString();
    }

    @Override
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, path);
    }

    @Override
    public ResponseParser<ScheduleResponse> getResponseParser() {
        return response -> ScheduleObjectMapper.getInstance().readValue(response, ScheduleResponse.class);
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
