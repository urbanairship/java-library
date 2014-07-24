package com.urbanairship.api.client;

import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

// No Unit Tests for this Class
public class ListScheduleAPIResponseHandler implements
        ResponseHandler<APIClientResponse<APIListScheduleResponse>> {

    /**
     * Handle HttpResponse. Returns an APIClientResponse on success, or
     * raises an APIRequestException, or an IOException.
     * @param response HttpResponse returned from the Request
     * @return APIClientResponse appropriate for the request.
     * @throws java.io.IOException
     */
    @Override
    public APIClientResponse<APIListScheduleResponse> handleResponse(HttpResponse response)
            throws IOException {

        // HTTP response code
        int statusCode = response.getStatusLine().getStatusCode();

        // Uncommon, or unknown
        if (statusCode >= 200 && statusCode < 300){
            return handleSuccessfulSchedule(response);
        }
        // Handle unhandled server error codes
        else {
            throw APIRequestException.exceptionForResponse(response);
        }
    }

    /*
     * Create an APIResponse for the successful list schedule request.
     * Any exceptions thrown by HttpResponse object that are related to
     * closing the response body are ignored.
     * @param response
     * @return APIClientResponse<APIListScheduleResponse>
     * @throws IOException
     */
    private APIClientResponse<APIListScheduleResponse> handleSuccessfulSchedule(HttpResponse response)
            throws IOException {
        String jsonPayload = EntityUtils.toString(response.getEntity());
        // toss out exceptions related to closing the entity
        EntityUtils.consumeQuietly(response.getEntity());
        ObjectMapper mapper = APIResponseObjectMapper.getInstance();
        APIListScheduleResponse scheduleResponse =
                mapper.readValue(jsonPayload, APIListScheduleResponse.class);
        APIClientResponse.Builder<APIListScheduleResponse> builder =
                APIClientResponse.newListScheduleResponseBuilder();
        builder.setHttpResponse(response);
        builder.setApiResponse(scheduleResponse);
        return builder.build();
    }
}
