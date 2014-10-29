/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.urbanairship.api.client.model.APIClientResponse;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class StringAPIResponseHandler implements ResponseHandler<APIClientResponse<String>> {

    private static final Logger logger = LoggerFactory.getLogger("com.urbanairship.api");
    private static final APIClientResponse.Builder<String> builder = APIClientResponse.newStringResponseBuilder();

    @Override
    public APIClientResponse<String> handleResponse(HttpResponse response) throws IOException {

        int statusCode = response.getStatusLine().getStatusCode();

        switch (statusCode){
            case HttpStatus.SC_ACCEPTED:
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("Handling response code:%s", statusCode));
                }
                return handleSuccessfulRequest(response);

            case HttpStatus.SC_BAD_REQUEST:
            case HttpStatus.SC_UNAUTHORIZED:
            case HttpStatus.SC_NOT_ACCEPTABLE:
                throw APIRequestException.exceptionForResponse(response);
            default:
                break;
        }

        if (statusCode >= 200 && statusCode < 300){
            return handleSuccessfulRequest(response);
        } else {
            throw APIRequestException.exceptionForResponse(response);
        }
    }

    private APIClientResponse<String> handleSuccessfulRequest(HttpResponse response) throws IOException {

        builder.setHttpResponse(response);

        try {
            String payload = EntityUtils.toString(response.getEntity());
            builder.setApiResponse(payload);
        } finally {
            EntityUtils.consumeQuietly(response.getEntity());
        }

        return builder.build();
    }
}
