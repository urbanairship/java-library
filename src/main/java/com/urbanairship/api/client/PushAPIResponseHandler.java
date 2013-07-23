package com.urbanairship.api.client;

import com.urbanairship.api.client.parse.APIResponseObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
Handles the server response when working with Push API endpoints.
 */
public class PushAPIResponseHandler implements
        ResponseHandler<APIClientResponse<APIPushResponse>> {

    private final Logger logger;

    PushAPIResponseHandler(){
        logger = LoggerFactory.getLogger("com.urbanairship.api");
    }

    /**
     * Handle HttpResponse. Returns an APIClientResponse on success, or
     * raises an APIRequestException, or an IOException.
     * @param response HttpResponse returned from the Request
     * @return APIClientResponse appropriate for the request.
     * @throws IOException
     */
    @Override
    public APIClientResponse<APIPushResponse> handleResponse(HttpResponse response)
            throws IOException {

        // HTTP Response Code
        int statusCode = response.getStatusLine().getStatusCode();

        // Documented cases
        switch (statusCode){
            case HttpStatus.SC_ACCEPTED:
                logger.debug(String.format("Handling response code:%s",
                                           statusCode));
                return handleSuccessfulPush(response);

            case HttpStatus.SC_BAD_REQUEST:
            case HttpStatus.SC_UNAUTHORIZED:
            case HttpStatus.SC_NOT_ACCEPTABLE:
                throw APIRequestException.exceptionForResponse(response);
            default:
                break;
        }

        // Uncommon, or unknown
        if (statusCode >= 200 && statusCode < 300){
            return handleSuccessfulPush(response);
        }
        // Handle unhandled server error codes
        else {
            throw APIRequestException.exceptionForResponse(response);
        }
    }

    /*
     * Create an API response object for a successful push
     * @param response HttpResponse from Urban Airship
     * @return APIClientResponse
     * @throws IOException
     */
    private APIClientResponse<APIPushResponse> handleSuccessfulPush(HttpResponse response)
            throws IOException{
        String jsonPayload = EntityUtils.toString(response.getEntity());
        EntityUtils.consumeQuietly(response.getEntity());
        ObjectMapper mapper = APIResponseObjectMapper.getInstance();
        APIPushResponse pushResponse =
                mapper.readValue(jsonPayload, APIPushResponse.class);
        APIClientResponse.Builder<APIPushResponse> builder =
                APIClientResponse.newPushResponseBuilder();
        builder.setApiResponse(pushResponse);
        builder.setHttpResponse(response);
        return builder.build();
    }


}

