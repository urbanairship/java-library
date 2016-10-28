/**
 * Copyright (c) 2016 Zillow, Inc
 */
package com.urbanairship.api.feedback;

import com.google.common.net.HttpHeaders;

import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.feedback.model.DeviceTokensFeedbackResponse;
import com.urbanairship.api.feedback.model.FeedbackPayload;
import com.urbanairship.api.feedback.parse.FeedbackObjectMapper;

import org.apache.http.entity.ContentType;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hongbinl
 */
public class ListDeviceTokensFeedbackRequest implements Request<List<DeviceTokensFeedbackResponse>> {

    private final static String API_DEVICETOKENS_FEEDBACK_PATH = "/api/device_tokens/feedback/";

    private final FeedbackPayload payload;

    public ListDeviceTokensFeedbackRequest(FeedbackPayload payload) {
        this.payload = payload;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.GET;
    }

    @Override
    public String getRequestBody() {
        return payload.toJSON();
    }

    @Override
    public ContentType getContentType() {
        return ContentType.APPLICATION_JSON;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, UA_VERSION_JSON);
        return headers;
    }

    @Override
    public URI getUri(URI baseUri) throws URISyntaxException {
        return RequestUtils.resolveURI(baseUri, API_DEVICETOKENS_FEEDBACK_PATH);
    }

    @Override
    public ResponseParser<List<DeviceTokensFeedbackResponse>> getResponseParser() {
        return new ResponseParser<List<DeviceTokensFeedbackResponse>>() {
            @Override
            public List<DeviceTokensFeedbackResponse> parse(String response) throws IOException {
                return FeedbackObjectMapper
                        .getInstance().readValue(response, new TypeReference<List<DeviceTokensFeedbackResponse>>(){});
            }
        };
    }
}
