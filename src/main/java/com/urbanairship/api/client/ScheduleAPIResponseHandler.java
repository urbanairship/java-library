/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;


import com.urbanairship.api.client.model.APIClientResponse;
import com.urbanairship.api.client.model.APIScheduleResponse;
import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;


public final class ScheduleAPIResponseHandler implements ResponseHandler<APIClientResponse<APIScheduleResponse>> {

    private static final ObjectMapper mapper = APIResponseObjectMapper.getInstance();
    private static final APIClientResponse.Builder<APIScheduleResponse> builder = APIClientResponse.newScheduleResponseBuilder();

    @Override
    public APIClientResponse<APIScheduleResponse> handleResponse(HttpResponse response) throws IOException {

        int statusCode = response.getStatusLine().getStatusCode();

        switch (statusCode){
            case HttpStatus.SC_CREATED:
                return handleSuccessfulSchedule(response);

            case HttpStatus.SC_BAD_REQUEST:
            case HttpStatus.SC_UNAUTHORIZED:
            case HttpStatus.SC_FORBIDDEN:
                throw APIRequestException.exceptionForResponse(response);
        }

        if (statusCode >= 200 && statusCode < 300) {
            return handleSuccessfulSchedule(response);
        } else {
            throw APIRequestException.exceptionForResponse(response);
        }
    }

    private APIClientResponse<APIScheduleResponse> handleSuccessfulSchedule(HttpResponse response) throws IOException {

        builder.setHttpResponse(response);

        try {
            String jsonPayload = EntityUtils.toString(response.getEntity());
            APIScheduleResponse scheduleResponse = mapper.readValue(jsonPayload, APIScheduleResponse.class);
            builder.setApiResponse(scheduleResponse);
        } finally {
            EntityUtils.consumeQuietly(response.getEntity());
        }

        return builder.build();
    }
}
