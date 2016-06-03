package com.urbanairship.api.staticlists;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class StaticListDeleteRequestTest {
    private final static String STATIC_LIST_DELETE_PATH = "/api/lists/abc123";

    StaticListDeleteRequest request;

    @Before
    public void setup() {
        request = StaticListDeleteRequest.newRequest("abc123");
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(request.getContentType(), null);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(request.getHttpMethod(), Request.HttpMethod.DELETE);
    }

    @Test
    public void testBody() throws Exception {
        assertEquals(request.getRequestBody(), null);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(request.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedUri = URI.create("https://go.urbanairship.com" + STATIC_LIST_DELETE_PATH);
        assertEquals(request.getUri(baseURI), expectedUri);
    }

    @Test
    public void testScheduleParser() throws Exception {
        ResponseParser responseParser = new ResponseParser<String>() {
            @Override
            public String parse(String response) throws IOException {
                return response;
            }
        };

        assertEquals(request.getResponseParser().parse(null), responseParser.parse(null));
    }
}