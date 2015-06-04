/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.urbanairship.api.client.model.APIClientResponse;
import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public final class APIClientResponseHandler<T> implements ResponseHandler<APIClientResponse<T>> {

    private final Class<T> type;

    public APIClientResponseHandler(Class<T> type) {
        this.type = type;
    }

    public APIClientResponse<T> handleResponse(HttpResponse response) throws IOException {
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode >= 200 && statusCode < 300) {
            return handleSuccessfulRequest(response);
        } else {
            throw APIRequestException.exceptionForResponse(response);
        }
    }

    private APIClientResponse<T> handleSuccessfulRequest(HttpResponse response) throws IOException {
        T apiResponse;

        try {
            String jsonPayload = EntityUtils.toString(response.getEntity());
            apiResponse = APIResponseObjectMapper.getInstance().readValue(jsonPayload, type);
        } finally {
            EntityUtils.consumeQuietly(response.getEntity());
        }

        return new APIClientResponse.Builder<T>()
                .setHttpResponse(response)
                .setApiResponse(apiResponse)
                .build();
    }
}
