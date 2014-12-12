/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;


import com.urbanairship.api.client.model.APIClientResponse;
import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import com.urbanairship.api.reports.model.ReportsAPIOpensResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class AppsOpenReportAPIResponseHandler implements ResponseHandler<APIClientResponse<ReportsAPIOpensResponse>> {

    private static final ObjectMapper mapper = APIResponseObjectMapper.getInstance();
    private static final APIClientResponse.Builder<ReportsAPIOpensResponse> builder = APIClientResponse.newAppsOpenReportResponseBuilder();


    @Override
    public APIClientResponse<ReportsAPIOpensResponse> handleResponse(HttpResponse httpResponse) throws IOException {
        int statusCode = httpResponse.getStatusLine().getStatusCode();

        if (statusCode >= 200 && statusCode < 300) {
            return handleSuccessfulResponse(httpResponse);
        } else {
            throw APIRequestException.exceptionForResponse(httpResponse);
        }
    }

    private APIClientResponse<ReportsAPIOpensResponse> handleSuccessfulResponse(HttpResponse response) throws IOException {

        builder.setHttpResponse(response);

        try {
            String jsonPayload = EntityUtils.toString(response.getEntity());
            ReportsAPIOpensResponse apiresponse = mapper.readValue(jsonPayload, ReportsAPIOpensResponse.class);
            builder.setApiResponse(apiresponse);
        } finally {
            EntityUtils.consumeQuietly(response.getEntity());
        }

        return builder.build();
    }
}
