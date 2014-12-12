/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.urbanairship.api.client.model.APIClientResponse;
import com.urbanairship.api.client.model.APIPushResponse;
import com.urbanairship.api.client.parse.APIResponseObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public final class PushAPIResponseHandler implements ResponseHandler<APIClientResponse<APIPushResponse>> {

    private static final Logger logger = LoggerFactory.getLogger("com.urbanairship.api");
    private static final ObjectMapper mapper = APIResponseObjectMapper.getInstance();
    private static final APIClientResponse.Builder<APIPushResponse> builder = APIClientResponse.newPushResponseBuilder();

    @Override
    public APIClientResponse<APIPushResponse> handleResponse(HttpResponse response) throws IOException {

        int statusCode = response.getStatusLine().getStatusCode();

        switch (statusCode){
            case HttpStatus.SC_ACCEPTED:
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("Handling response code:%s", statusCode));
                }
                return handleSuccessfulPush(response);

            case HttpStatus.SC_BAD_REQUEST:
            case HttpStatus.SC_UNAUTHORIZED:
            case HttpStatus.SC_NOT_ACCEPTABLE:
                throw APIRequestException.exceptionForResponse(response);
            default:
                break;
        }

        if (statusCode >= 200 && statusCode < 300){
            return handleSuccessfulPush(response);
        } else {
            throw APIRequestException.exceptionForResponse(response);
        }
    }

    private APIClientResponse<APIPushResponse> handleSuccessfulPush(HttpResponse response) throws IOException {

        builder.setHttpResponse(response);

        try {
            String jsonPayload = EntityUtils.toString(response.getEntity());
            APIPushResponse pushResponse = mapper.readValue(jsonPayload, APIPushResponse.class);
            builder.setApiResponse(pushResponse);
        } finally {
            EntityUtils.consumeQuietly(response.getEntity());
        }

        return builder.build();
    }


}

