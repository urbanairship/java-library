/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.feedback;

import com.google.common.net.HttpHeaders;

import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.feedback.model.ApidsFeedbackResponse;
import com.urbanairship.api.feedback.parse.FeedbackObjectMapper;

import org.apache.http.client.utils.URIBuilder;
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

public class ListApidsFeedbackRequestTest {

    DateTime now = DateTime.now();
    ListApidsFeedbackRequest listApidsFeedbackRequest = new ListApidsFeedbackRequest(now);

    @Test
    public void testContentType() throws Exception {
        assertEquals(listApidsFeedbackRequest.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(listApidsFeedbackRequest.getHttpMethod(), Request.HttpMethod.GET);
    }

    @Test
    public void testBody() throws Exception {
        assertEquals(listApidsFeedbackRequest.getRequestBody(), null);
    }

    @Test
    public void testHeader() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(listApidsFeedbackRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");
        URIBuilder builder = new URIBuilder(new URI("https://go.urbanairship.com/api/apids/feedback/"));
        builder.addParameter("since", DateFormats.DATE_ONLY_FORMATTER.print(now));
        URI expectedURI = builder.build();
        assertEquals(expectedURI, listApidsFeedbackRequest.getUri(baseURI));
    }

    @Test
    public void testFeedbackParser() throws Exception {
        ResponseParser<List<ApidsFeedbackResponse>> responseParser =
            new ResponseParser<List<ApidsFeedbackResponse>>() {
            @Override
            public List<ApidsFeedbackResponse> parse(String response) throws IOException {
                return FeedbackObjectMapper.getInstance().readValue(response, new TypeReference<List<ApidsFeedbackResponse>>(){});
            }
        };
        String response = "["+
                "{" +
                "\"apid\": \"00000000-0000-0000-0000-000000000000\"," +
                "\"gcm_registration_id\": \"abcdefghijklmn\", "+
                "\"marked_inactive_on\": \"2009-06-22 10:05:00\"," +
                "\"alias\": \"bob\"" +
                "}," +
                "{" +
                "\"apid\": \"00000000-0000-0000-0000-000000000001\"," +
                "\"gcm_registration_id\": \"opqrstuvmxyz\", "+
                "\"marked_inactive_on\": \"2009-06-22 10:07:00\"," +
                "\"alias\": \"Alice\"" +
                "}" +
                "]";
        assertEquals(listApidsFeedbackRequest.getResponseParser().parse(response), responseParser.parse(response));
    }
}
