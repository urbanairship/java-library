/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.feedback;

import com.google.common.net.HttpHeaders;

import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.feedback.model.APIApidsFeedbackResponse;
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

public class ListApidsFeedbackRequest implements Request<List<APIApidsFeedbackResponse>> {
    private final static String API_APIDS_FEEDBACK_PATH = "/api/apids/feedback/";
    private final FeedbackPayload payload;

    public ListApidsFeedbackRequest(FeedbackPayload payload) {
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
        return RequestUtils.resolveURI(baseUri, API_APIDS_FEEDBACK_PATH);
    }

    @Override
    public ResponseParser< List<APIApidsFeedbackResponse> > getResponseParser() {
        return new ResponseParser<List<APIApidsFeedbackResponse>>() {
            @Override
            public List<APIApidsFeedbackResponse> parse(String response) throws IOException {
                return FeedbackObjectMapper.getInstance().readValue(response, new TypeReference<List<APIApidsFeedbackResponse>>(){});
            }
        };
    }
}
