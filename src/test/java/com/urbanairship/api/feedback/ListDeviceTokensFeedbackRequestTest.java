/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.feedback;

import com.google.common.net.HttpHeaders;

import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.feedback.model.DeviceTokensFeedbackResponse;
import com.urbanairship.api.feedback.model.FeedbackPayload;
import com.urbanairship.api.feedback.parse.FeedbackObjectMapper;

import org.apache.http.entity.ContentType;
import org.codehaus.jackson.type.TypeReference;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ListDeviceTokensFeedbackRequestTest {
    FeedbackPayload payload = FeedbackPayload.newBuilder().setSince(DateTime.now()).build();
    ListDeviceTokensFeedbackRequest listDeviceTokensFeedbackRequest = new ListDeviceTokensFeedbackRequest(payload);

    @Test
    public void testContentType() throws Exception {
        assertEquals(listDeviceTokensFeedbackRequest.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(listDeviceTokensFeedbackRequest.getHttpMethod(), Request.HttpMethod.GET);
    }

    @Test
    public void testBody() throws Exception {
        assertEquals(listDeviceTokensFeedbackRequest.getRequestBody(), payload.toJSON());
    }

    @Test
    public void testHeader() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(listDeviceTokensFeedbackRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");
        URI expectedURI = URI.create("https://go.urbanairship.com/api/device_tokens/feedback/");

        assertEquals(listDeviceTokensFeedbackRequest.getUri(baseURI), expectedURI);
    }

    @Test
    public void testFeedbackParser() throws Exception {
        ResponseParser<List<DeviceTokensFeedbackResponse>> responseParser =
                new ResponseParser<List<DeviceTokensFeedbackResponse>>() {
                    @Override
                    public List<DeviceTokensFeedbackResponse> parse(String response) throws IOException {
                        return FeedbackObjectMapper
                                .getInstance().readValue(response, new TypeReference<List<DeviceTokensFeedbackResponse>>(){});
                    }
                };
        String response = "[{" +
                "\"device_token\": \"1234123412341234123412341234123412341234123412341234123412341234\"," +
                "\"marked_inactive_on\": \"2009-06-22T10:05:00\"," +
                "\"alias\": \"bob\"" +
                "}," +
                "{" +
                "\"device_token\": \"ABCDABCDABCDABCDABCDABCDABCDABCDABCDABCDABCDABCDABCDABCDABCDABCD\"," +
                "\"marked_inactive_on\": \"2009-06-22T10:07:00\"," +
                "\"alias\": \"Alice\"" +
                "}]";
        assertEquals(listDeviceTokensFeedbackRequest.getResponseParser().parse(response), responseParser.parse(response));
    }
}
