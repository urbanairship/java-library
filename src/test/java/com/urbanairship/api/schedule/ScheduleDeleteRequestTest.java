package com.urbanairship.api.schedule;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.common.model.GenericResponse;
import com.urbanairship.api.common.parse.CommonObjectMapper;

import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ScheduleDeleteRequestTest {

    ScheduleDeleteRequest scheduleDeleteRequest = ScheduleDeleteRequest.newRequest("id");

    @Test
    public void testContentType() throws Exception {
        assertEquals(scheduleDeleteRequest.getContentType(), null);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(scheduleDeleteRequest.getHttpMethod(), Request.HttpMethod.DELETE);
    }

    @Test
    public void testBody() throws Exception {
        assertEquals(scheduleDeleteRequest.getRequestBody(), null);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(scheduleDeleteRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expextedURI = URI.create("https://go.urbanairship.com/api/schedules/id");
        assertEquals(scheduleDeleteRequest.getUri(baseURI), expextedURI);
    }

    @Test
    public void testScheduleParser() throws Exception {

        GenericResponse genericResponse = new GenericResponse(true, null, null, null);

        String responseJson = "{" +
                "\"ok\": true" +
                "}";

        assertEquals(scheduleDeleteRequest.getResponseParser().parse(responseJson), genericResponse);
    }
}
