/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.urbanairship.api.client.model.APIClientResponse;
import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import com.urbanairship.api.reports.model.PerPushDetailResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class ListPerPushDetailAPIResponseHandler implements ResponseHandler<APIClientResponse<PerPushDetailResponse>> {

    private static final ObjectMapper mapper = APIResponseObjectMapper.getInstance();
    private static final APIClientResponse.Builder<PerPushDetailResponse> builder = APIClientResponse.newListPerPushDetailBuilder();

    @Override
    public APIClientResponse<PerPushDetailResponse> handleResponse(HttpResponse httpResponse) throws IOException {
        int statusCode = httpResponse.getStatusLine().getStatusCode();

        if (statusCode >= 200 && statusCode < 300) {
            return handleSuccessfulResponse(httpResponse);
        } else {
            throw APIRequestException.exceptionForResponse(httpResponse);
        }
    }

    private APIClientResponse<PerPushDetailResponse> handleSuccessfulResponse(HttpResponse response) throws IOException {

        builder.setHttpResponse(response);

        try {
            String jsonPayload = EntityUtils.toString(response.getEntity());
            PerPushDetailResponse obj = mapper.readValue(jsonPayload, PerPushDetailResponse.class);
            builder.setApiResponse(obj);
        } finally {
            EntityUtils.consumeQuietly(response.getEntity());
        }

        return builder.build();
    }
}
