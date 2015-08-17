package com.urbanairship.api.schedule;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DeleteScheduleRequestTest {

    DeleteScheduleRequest deleteScheduleRequest = DeleteScheduleRequest.newRequest("id");

    @Test
    public void testContentType() throws Exception {
        assertEquals(deleteScheduleRequest.getContentType(), null);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(deleteScheduleRequest.getHTTPMethod(), Request.HTTPMethod.DELETE);
    }

    @Test
    public void testBody() throws Exception {
        assertEquals(deleteScheduleRequest.getRequestBody(), null);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION);

        assertEquals(deleteScheduleRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expextedURI = URI.create("https://go.urbanairship.com/api/schedules/id");
        assertEquals(deleteScheduleRequest.getUri(baseURI), expextedURI);
    }

    @Test
    public void testScheduleParser() throws Exception {
        ResponseParser responseParser = new ResponseParser<String>() {
            @Override
            public String parse(String response) throws IOException {
                return response;
            }
        };

        assertEquals(deleteScheduleRequest.getResponseParser().parse(null), responseParser.parse(null));
    }
}
