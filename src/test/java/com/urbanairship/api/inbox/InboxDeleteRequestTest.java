package com.urbanairship.api.inbox;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class InboxDeleteRequestTest {

    InboxDeleteRequest InboxDeleteRequest = com.urbanairship.api.inbox.InboxDeleteRequest.newRequest("id");

    @Test
    public void testContentType() throws Exception {
        assertEquals(InboxDeleteRequest.getContentType(), null);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(InboxDeleteRequest.getHttpMethod(), Request.HttpMethod.DELETE);
    }

    @Test
    public void testBody() throws Exception {
        assertEquals(InboxDeleteRequest.getRequestBody(), null);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(InboxDeleteRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expextedURI = URI.create("https://go.urbanairship.com/api/user/messages/id");
        assertEquals(InboxDeleteRequest.getUri(baseURI), expextedURI);
    }

    @Test
    public void testScheduleParser() throws Exception {
        ResponseParser<String> responseParser = new ResponseParser<String>() {
            @Override
            public String parse(String response) throws IOException {
                return response;
            }
        };

        assertEquals(InboxDeleteRequest.getResponseParser().parse(null), responseParser.parse(null));
    }
    
}
