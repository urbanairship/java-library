package com.urbanairship.api.schedule;

/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * The DeleteScheduleRequest class builds delete scheduled push requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class DeleteScheduleRequest implements Request<String> {

    private final String scheduleId;

    private DeleteScheduleRequest(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    /**
     * Create the delete request.
     *
     * @param scheduleId String
     * @return DeleteScheduleRequest
     */
    public static DeleteScheduleRequest newRequest(String scheduleId) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(scheduleId), "Schedule ID may not be null");
        return new DeleteScheduleRequest(scheduleId);
    }

    @Override
    public ContentType getContentType() {
        return null;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.ACCEPT, UA_VERSION);
        return headers;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.DELETE;
    }

    @Override
    public String getRequestBody() {
        return null;
    }

    @Override
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, ScheduleRequest.API_SCHEDULE_PATH + scheduleId);
    }

    @Override
    public ResponseParser<String> getResponseParser() {
        return new ResponseParser<String>() {
            @Override
            public String parse(String response) throws IOException {
                return response;
            }
        };
    }
}
