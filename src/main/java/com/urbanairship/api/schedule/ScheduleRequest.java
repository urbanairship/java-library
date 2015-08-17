package com.urbanairship.api.schedule;

/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.schedule.model.Schedule;
import com.urbanairship.api.schedule.model.SchedulePayload;
import com.urbanairship.api.schedule.model.ScheduleResponse;
import com.urbanairship.api.schedule.parse.ScheduleObjectMapper;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * The ScheduleRequest class builds scheduled push requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class ScheduleRequest implements Request<ScheduleResponse> {

    final static String API_SCHEDULE_PATH = "/api/schedules/";

    private final Schedule schedule;
    private final PushPayload pushPayload;
    private final String path;
    private final HttpMethod method;
    private String name;

    private ScheduleRequest(Schedule schedule, PushPayload pushPayload, HttpMethod method, String path) {
        this.schedule = schedule;
        this.pushPayload = pushPayload;
        this.path = path;
        this.method = method;
    }

    /**
     * Create a scheduled push request.
     *
     * @param schedule Schedule
     * @param payload PushPayload
     * @return ScheduleRequest
     */
    public static ScheduleRequest newRequest(Schedule schedule, PushPayload payload) {
        Preconditions.checkNotNull(schedule, "Schedule may not be null");
        Preconditions.checkNotNull(payload, "Push payload may not be null");
        return new ScheduleRequest(schedule, payload, HttpMethod.POST, API_SCHEDULE_PATH);
    }

    /**
     * Create a request to update a scheduled push.
     *
     * @param schedule Schedule
     * @param payload PushPayload
     * @param scheduleId String
     * @return ScheduleRequest
     */
    public static ScheduleRequest newUpdateRequest(Schedule schedule, PushPayload payload, String scheduleId) {
        Preconditions.checkNotNull(scheduleId, "Schedule ID may not be mull");
        Preconditions.checkNotNull(schedule, "Schedule may not be null");
        Preconditions.checkNotNull(payload, "Push payload may not be null");
        return new ScheduleRequest(schedule, payload, HttpMethod.PUT, API_SCHEDULE_PATH + scheduleId);
    }

    public ScheduleRequest setName(String name) {
        this.name = name;
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
        return method;
    }

    @Override
    public String getRequestBody() {
        return SchedulePayload.newBuilder()
                .setName(name)
                .setPushPayload(pushPayload)
                .setSchedule(schedule)
                .build()
                .toJSON();
    }

    @Override
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, path);
    }

    @Override
    public ResponseParser<ScheduleResponse> getResponseParser() {
        return new ResponseParser<ScheduleResponse>() {
            @Override
            public ScheduleResponse parse(String response) throws IOException {
                return ScheduleObjectMapper.getInstance().readValue(response, ScheduleResponse.class);
            }
        };
    }
}
