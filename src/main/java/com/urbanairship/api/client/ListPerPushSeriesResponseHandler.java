/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.urbanairship.api.client.model.APIClientResponse;
import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import com.urbanairship.api.reports.model.PerPushSeriesResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;


public class ListPerPushSeriesResponseHandler implements ResponseHandler<APIClientResponse<PerPushSeriesResponse>> {

    private static final ObjectMapper mapper = APIResponseObjectMapper.getInstance();
    private static final APIClientResponse.Builder<PerPushSeriesResponse> builder = APIClientResponse.newListPerPushSeriesBuilder();


    @Override
    public APIClientResponse<PerPushSeriesResponse> handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
        int statusCode = httpResponse.getStatusLine().getStatusCode();

        if (statusCode >= 200 && statusCode < 300) {
            return handleSuccessfulResponse(httpResponse);
        } else {
            throw APIRequestException.exceptionForResponse(httpResponse);
        }
    }

    private APIClientResponse<PerPushSeriesResponse> handleSuccessfulResponse(HttpResponse response) throws IOException {

        builder.setHttpResponse(response);

        try {
            String jsonPayload = EntityUtils.toString(response.getEntity());
            PerPushSeriesResponse obj = mapper.readValue(jsonPayload, PerPushSeriesResponse.class);
            builder.setApiResponse(obj);
        } finally {
            EntityUtils.consumeQuietly(response.getEntity());
        }

        return builder.build();
    }

}
