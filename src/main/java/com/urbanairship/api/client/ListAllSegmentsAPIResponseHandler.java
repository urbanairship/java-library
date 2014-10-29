/*
 * Copyright 2014 Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.urbanairship.api.client.model.APIClientResponse;
import com.urbanairship.api.client.model.APIListAllSegmentsResponse;
import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public final class ListAllSegmentsAPIResponseHandler implements ResponseHandler<APIClientResponse<APIListAllSegmentsResponse>> {

    private static final ObjectMapper mapper = APIResponseObjectMapper.getInstance();
    private static final APIClientResponse.Builder<APIListAllSegmentsResponse> builder = APIClientResponse.newListAllSegmentsResponseBuilder();

    @Override
    public APIClientResponse<APIListAllSegmentsResponse> handleResponse(HttpResponse response) throws IOException {

        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode >= 200 && statusCode < 300) {
            return handleSuccessfulListSegments(response);
        } else {
            throw APIRequestException.exceptionForResponse(response);
        }
    }

    private APIClientResponse<APIListAllSegmentsResponse> handleSuccessfulListSegments(HttpResponse response) throws IOException {

        builder.setHttpResponse(response);

        try {
            String jsonPayload = EntityUtils.toString(response.getEntity());
            APIListAllSegmentsResponse segmentsResponse = mapper.readValue(jsonPayload, APIListAllSegmentsResponse.class);
            builder.setApiResponse(segmentsResponse);
        } finally {
            EntityUtils.consumeQuietly(response.getEntity());
        }

        return builder.build();
    }
}
