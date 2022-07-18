package com.urbanairship.api.segment;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.common.model.ErrorDetails;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.segments.SegmentLookupRequest;
import com.urbanairship.api.segments.model.SegmentView;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SegmentLookupRequestTest {
    private static final String TEST_QUERY_PATH = "/api/segments/";
    private static final String TEST_SEGMENT_ID = "abc123";

    SegmentLookupRequest request;

    @Before
    public void setup() {
        request = SegmentLookupRequest.newRequest(TEST_SEGMENT_ID);
    }

    @Test
    public void testContentType() throws Exception {
        assertNull(request.getContentType());
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(request.getHttpMethod(), Request.HttpMethod.GET);
    }

    @Test
    public void testBody() throws Exception {
        assertNull(request.getRequestBody());
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

        Selector compound = Selectors.and(Selectors.tag("ipad"), Selectors.not(Selectors.tag("foo")));

        SegmentView segmentView = SegmentView.newBuilder()
                .setCriteria(compound)
                .setDisplayName("A segment")
                .setError("error")
                .setErrorDetails(new ErrorDetails("an error", "a path"))
                .build();


        String responseInfo = "{\n" +
                "   \"criteria\": {\n" +
                "      \"and\": [\n" +
                "         {\n" +
                "            \"tag\": \"ipad\"\n" +
                "         },\n" +
                "         {\n" +
                "            \"not\": {\n" +
                "               \"tag\": \"foo\"\n" +
                "            }\n" +
                "         }\n" +
                "      ]\n" +
                "   },\n" +
                "   \"display_name\": \"A segment\",\n" +
                "   \"error\": \"error\",\n" +
                "   \"details\": {\"error\": \"an error\", \"path\":\"a path\"}\n" +
                "}";

        assertEquals(request.getResponseParser().parse(responseInfo), segmentView);
    }
}
