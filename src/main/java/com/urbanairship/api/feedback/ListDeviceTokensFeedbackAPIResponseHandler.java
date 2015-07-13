/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */
package com.urbanairship.api.feedback;

import com.urbanairship.api.client.APIRequestException;
import com.urbanairship.api.client.model.APIClientResponse;
import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import com.urbanairship.api.feedback.model.APIDeviceTokensFeedbackResponse;
import com.urbanairship.api.reports.model.AppStats;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.List;

public class ListDeviceTokensFeedbackAPIResponseHandler implements ResponseHandler<APIClientResponse<List<APIDeviceTokensFeedbackResponse>>>
{
    private static final ObjectMapper mapper = APIResponseObjectMapper.getInstance();
    private static final APIClientResponse.Builder<List<APIDeviceTokensFeedbackResponse>> builder = APIClientResponse.newListDeviceTokensFeedbackBuilder();

    @Override
    public APIClientResponse<List<APIDeviceTokensFeedbackResponse>> handleResponse(HttpResponse response) throws IOException
    {

        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode >= 200 && statusCode < 300) {
            return handleSuccessfulResponse(response);
        } else {
            throw APIRequestException.exceptionForResponse(response);
        }
    }

    private APIClientResponse<List<APIDeviceTokensFeedbackResponse>> handleSuccessfulResponse(HttpResponse response) throws IOException {

        builder.setHttpResponse(response);

        try {
            String jsonPayload = EntityUtils.toString(response.getEntity());
            List<APIDeviceTokensFeedbackResponse> listOfDeviceTokensFeedback = mapper.readValue(jsonPayload, new TypeReference<List<APIDeviceTokensFeedbackResponse>>() {
            });
            builder.setApiResponse(listOfDeviceTokensFeedback);
        } finally {
            EntityUtils.consumeQuietly(response.getEntity());
        }

        return builder.build();
    }

}
