/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.urbanairship.api.client.model.APIClientResponse;
import com.urbanairship.api.client.model.APIListTagsResponse;
import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public final class ListTagsAPIResponseHandler implements ResponseHandler<APIClientResponse<APIListTagsResponse>> {

    private static final ObjectMapper mapper = APIResponseObjectMapper.getInstance();
    private static final APIClientResponse.Builder<APIListTagsResponse> builder = APIClientResponse.newListTagsResponseBuilder();

    @Override
    public APIClientResponse<APIListTagsResponse> handleResponse(HttpResponse response) throws IOException {

        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode >= 200 && statusCode < 300) {
            return handleSuccessfulSchedule(response);
        } else {
            throw APIRequestException.exceptionForResponse(response);
        }
    }

    private APIClientResponse<APIListTagsResponse> handleSuccessfulSchedule(HttpResponse response) throws IOException {

        builder.setHttpResponse(response);

        try {
            String jsonPayload = EntityUtils.toString(response.getEntity());
            APIListTagsResponse tagResponse = mapper.readValue(jsonPayload, APIListTagsResponse.class);
            builder.setApiResponse(tagResponse);
        } finally {
            EntityUtils.consumeQuietly(response.getEntity());
        }

        return builder.build();
    }
}
