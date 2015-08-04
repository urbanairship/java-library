package com.urbanairship.api.schedule;

/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

import com.google.common.collect.Sets;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.parse.PushObjectMapper;
import com.urbanairship.api.schedule.model.Schedule;
import com.urbanairship.api.schedule.model.SchedulePayload;
import com.urbanairship.api.schedule.model.ScheduleResponse;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ScheduleRequest implements Request<ScheduleResponse> {

    final static String API_SCHEDULE_PATH = "/api/schedules/";

    private final Schedule schedule;
    private final PushPayload pushPayload;
    private final String path;
    private final HttpMethod method;
    private final HashSet<String > pushIds = Sets.newHashSet();
    private String url;
    private String name;

    private ScheduleRequest(Schedule schedule, PushPayload pushPayload, HttpMethod method, String path) {
        this.schedule = schedule;
        this.pushPayload = pushPayload;
        this.path = path;
        this.method = method;
    }

    public static ScheduleRequest newRequest(Schedule schedule, PushPayload payload) {
        return new ScheduleRequest(schedule, payload, HttpMethod.POST, API_SCHEDULE_PATH);
    }

    public static ScheduleRequest newUpdateRequest(Schedule schedule, PushPayload payload, String scheduleId) {
        return new ScheduleRequest(schedule, payload, HttpMethod.PUT, API_SCHEDULE_PATH + scheduleId);
    }

    public ScheduleRequest setUrl(String url) {
        this.url = url;
        return this;
    }

    public ScheduleRequest setName(String name) {
        this.name = name;
        return this;
    }

    public ScheduleRequest addPushId(String pushId) {
        this.pushIds.add(pushId);
        return this;
    }

    public ScheduleRequest addAllPushIds(Collection<String> pushIds) {
        this.pushIds.addAll(pushIds);
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
                .setUrl(url)
                .setPushPayload(pushPayload)
                .setSchedule(schedule)
                .addAllPushIds(pushIds)
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
                return PushObjectMapper.getInstance().readValue(response, ScheduleResponse.class);
            }
        };
    }
}
