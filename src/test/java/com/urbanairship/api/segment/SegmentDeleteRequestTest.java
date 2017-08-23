package com.urbanairship.api.segment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.segments.SegmentDeleteRequest;
import com.urbanairship.api.segments.parse.SegmentObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SegmentDeleteRequestTest {
    private static final String TEST_QUERY_PATH = "/api/segments/";
    private static final String TEST_SEGMENT_ID = "abcdefg";
    private static final ObjectMapper mapper = SegmentObjectMapper.getInstance();

    SegmentDeleteRequest request;

    @Before
    public void setup() {
        request = SegmentDeleteRequest.newRequest(TEST_SEGMENT_ID);
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
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(request.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");
        URI expectedURI = URI.create("https://go.urbanairship.com" + TEST_QUERY_PATH + TEST_SEGMENT_ID);

        assertEquals(request.getUri(baseURI), expectedURI);
    }

    @Test
    public void testParser() throws Exception {
        ResponseParser responseParser = new ResponseParser<String>() {
            @Override
            public String parse(String response) throws IOException {
                return response;
            }
        };

        assertEquals(request.getResponseParser().parse("204 No Content"), responseParser.parse("204 No Content"));
    }
}
