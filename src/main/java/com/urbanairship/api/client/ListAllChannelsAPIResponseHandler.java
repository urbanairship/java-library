package com.urbanairship.api.client;

import com.urbanairship.api.client.model.APIClientResponse;
import com.urbanairship.api.client.model.APIListAllChannelsResponse;
import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;


import java.io.IOException;

public final class ListAllChannelsAPIResponseHandler implements ResponseHandler<APIClientResponse<APIListAllChannelsResponse>> {

    private static final ObjectMapper mapper = APIResponseObjectMapper.getInstance();
    private static final APIClientResponse.Builder<APIListAllChannelsResponse> builder = APIClientResponse.newListAllChannelsResponseBuilder();

    @Override
    public APIClientResponse<APIListAllChannelsResponse> handleResponse(HttpResponse response) throws IOException {

        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode >= 200 && statusCode < 300) {
            return handleSuccessfulRequest(response);
        } else {
            throw APIRequestException.exceptionForResponse(response);
        }
    }

    private APIClientResponse<APIListAllChannelsResponse> handleSuccessfulRequest(HttpResponse response) throws IOException
    {

        builder.setHttpResponse(response);

        try {
            String jsonPayload = EntityUtils.toString(response.getEntity());
            APIListAllChannelsResponse channelsResponse = mapper.readValue(jsonPayload, APIListAllChannelsResponse.class);
            builder.setApiResponse(channelsResponse);
        } finally {
            EntityUtils.consumeQuietly(response.getEntity());
        }

        return builder.build();
    }

}
