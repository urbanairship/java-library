package com.urbanairship.api.segment;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.common.model.GenericResponse;
import com.urbanairship.api.segments.SegmentDeleteRequest;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SegmentDeleteRequestTest {
    private static final String TEST_QUERY_PATH = "/api/segments/";
    private static final String TEST_SEGMENT_ID = "abcdefg";

    SegmentDeleteRequest request;

    @Before
    public void setup() {
        request = SegmentDeleteRequest.newRequest(TEST_SEGMENT_ID);
    }

    @Test
    public void testContentType() throws Exception {
        assertNull(request.getContentType());
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(request.getHttpMethod(), Request.HttpMethod.DELETE);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<>();
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
        
        GenericResponse genericResponse = new GenericResponse(true, null, null, null);

        String responseJson = "{" +
                "\"ok\": true" +
                "}";

        assertEquals(request.getResponseParser().parse(responseJson), genericResponse);
    }
}
