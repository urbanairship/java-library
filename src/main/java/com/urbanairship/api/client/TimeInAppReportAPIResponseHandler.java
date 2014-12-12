/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.urbanairship.api.client.model.APIClientResponse;
import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import com.urbanairship.api.reports.model.ReportsAPITimeInAppResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class TimeInAppReportAPIResponseHandler implements ResponseHandler<APIClientResponse<ReportsAPITimeInAppResponse>> {

    private static final ObjectMapper mapper = APIResponseObjectMapper.getInstance();
    private static final APIClientResponse.Builder<ReportsAPITimeInAppResponse> builder = APIClientResponse.newTimeInAppReportResponseBuilder();


    @Override
    public APIClientResponse<ReportsAPITimeInAppResponse> handleResponse(HttpResponse httpResponse) throws IOException {
        int statusCode = httpResponse.getStatusLine().getStatusCode();

        if (statusCode >= 200 && statusCode < 300) {
            return handleSuccessfulResponse(httpResponse);
        } else {
            throw APIRequestException.exceptionForResponse(httpResponse);
        }
    }

    private APIClientResponse<ReportsAPITimeInAppResponse> handleSuccessfulResponse(HttpResponse response) throws IOException {

        builder.setHttpResponse(response);

        try {
            String jsonPayload = EntityUtils.toString(response.getEntity());
            ReportsAPITimeInAppResponse apiresponse = mapper.readValue(jsonPayload, ReportsAPITimeInAppResponse.class);
            builder.setApiResponse(apiresponse);
        } finally {
            EntityUtils.consumeQuietly(response.getEntity());
        }

        return builder.build();
    }
}
