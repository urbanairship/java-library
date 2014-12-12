/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.urbanairship.api.client.model.APIClientResponse;
import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import com.urbanairship.api.schedule.model.SchedulePayload;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public final class ListScheduleAPIResponseHandler implements ResponseHandler<APIClientResponse<SchedulePayload>> {

    private static final ObjectMapper mapper = APIResponseObjectMapper.getInstance();
    private static final APIClientResponse.Builder<SchedulePayload> builder = APIClientResponse.newSchedulePayloadBuilder();

    @Override
    public APIClientResponse<SchedulePayload> handleResponse(HttpResponse response) throws IOException {

        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode >= 200 && statusCode < 300) {
            return handleSuccessfulSchedule(response);
        } else {
            throw APIRequestException.exceptionForResponse(response);
        }
    }

    private APIClientResponse<SchedulePayload> handleSuccessfulSchedule(HttpResponse response) throws IOException {

        builder.setHttpResponse(response);

        try {
            String jsonPayload = EntityUtils.toString(response.getEntity());
            SchedulePayload scheduleResponse = mapper.readValue(jsonPayload, SchedulePayload.class);
            builder.setApiResponse(scheduleResponse);
        } finally {
            EntityUtils.consumeQuietly(response.getEntity());
        }

        return builder.build();
    }
}
