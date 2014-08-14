package com.urbanairship.api.client;

import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public final class ListScheduleAPIResponseHandler implements
        ResponseHandler<APIClientResponse<APIListScheduleResponse>> {

    private static final ObjectMapper mapper = APIResponseObjectMapper.getInstance();
    private static final APIClientResponse.Builder<APIListScheduleResponse> builder = APIClientResponse.newListScheduleResponseBuilder();

    @Override
    public APIClientResponse<APIListScheduleResponse> handleResponse(HttpResponse response)
            throws IOException {

        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode >= 200 && statusCode < 300) {
            return handleSuccessfulSchedule(response);
        } else {
            throw APIRequestException.exceptionForResponse(response);
        }
    }

    private APIClientResponse<APIListScheduleResponse> handleSuccessfulSchedule(HttpResponse response)
            throws IOException {

        String jsonPayload = EntityUtils.toString(response.getEntity());
        EntityUtils.consumeQuietly(response.getEntity());

        APIListScheduleResponse scheduleResponse = mapper.readValue(jsonPayload, APIListScheduleResponse.class);

        builder.setHttpResponse(response);
        builder.setApiResponse(scheduleResponse);

        return builder.build();
    }
}
