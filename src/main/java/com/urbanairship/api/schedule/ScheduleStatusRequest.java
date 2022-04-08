package com.urbanairship.api.schedule;

import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * Copyright (c) 2013-2021.  Urban Airship and Contributors
 */

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.common.model.GenericResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * The ScheduleStatusRequest class builds update scheduled push status requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class ScheduleStatusRequest implements Request<GenericResponse> {

    private final String scheduleId;
    private final String status;

    public enum Status {
        PAUSE("pause"),
        RESUME("resume");
    
        private final String status;
    
        private Status(final String status) {
            this.status = status;
        }
    
        public String getStatus() {
            return status;
        }
    }


    private ScheduleStatusRequest(String scheduleId, String status) {
        this.scheduleId = scheduleId;
        this.status = status;  
    }

    /**
     * Create the update status request.
     *
     * @param scheduleId String
     * @return ScheduleStatusRequest
     */
    public static ScheduleStatusRequest pauseScheduleRequest(String scheduleId) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(scheduleId), "Schedule ID may not be null");
        return new ScheduleStatusRequest(scheduleId, Status.PAUSE.getStatus());
    }

    public static ScheduleStatusRequest resumeScheduleRequest(String scheduleId) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(scheduleId), "Schedule ID may not be null");
        return new ScheduleStatusRequest(scheduleId, Status.RESUME.getStatus());
    }

    @Override
    public ContentType getContentType() {
        return null;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.ACCEPT, UA_VERSION_JSON);
        return headers;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getRequestBody() {
        return null;
    }

    @Override
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, ScheduleRequest.API_SCHEDULE_PATH + scheduleId + "/" + status);
    }

    @Override
    public ResponseParser<GenericResponse> getResponseParser() {
        return RequestUtils.GENERIC_RESPONSE_PARSER;
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
