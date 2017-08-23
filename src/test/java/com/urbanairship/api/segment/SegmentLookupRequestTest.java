package com.urbanairship.api.segment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.segments.SegmentLookupRequest;
import com.urbanairship.api.segments.model.SegmentView;
import com.urbanairship.api.segments.parse.SegmentObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SegmentLookupRequestTest {
    private static final ObjectMapper mapper = SegmentObjectMapper.getInstance();
    private static final String TEST_QUERY_PATH = "/api/segments/";
    private static final String TEST_SEGMENT_ID = "abc123";

    SegmentLookupRequest request;

    @Before
    public void setup() {
        request = SegmentLookupRequest.newRequest(TEST_SEGMENT_ID);
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(request.getContentType(), null);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(request.getHttpMethod(), Request.HttpMethod.GET);
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

        URI expectedURI = URI.create("https://go.urbanairship.com" + TEST_QUERY_PATH + TEST_SEGMENT_ID);
        assertEquals(request.getUri(baseURI), expectedURI);
    }

    @Test
    public void testParser() throws Exception {
        ResponseParser responseParser = new ResponseParser<SegmentView>() {
            @Override
            public SegmentView parse(String response) throws IOException {
                return mapper.readValue(response, SegmentView.class);
            }
        };

        String responseInfo = "{  \n" +
                "  \"display_name\":\"2014-11-07T14:26:56.749-08:00\",\n" +
                "  \"criteria\":{  \n" +
                "    \"and\":[  \n" +
                "      {  \n" +
                "        \"location\":{  \n" +
                "          \"us_state\":\"OR\",\n" +
                "          \"date\":{  \n" +
                "            \"days\":{  \n" +
                "              \"start\":\"2014-11-02\",\n" +
                "              \"end\":\"2014-11-07\"\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      {  \n" +
                "        \"location\":{  \n" +
                "          \"us_state\":\"CA\",\n" +
                "          \"date\":{  \n" +
                "            \"recent\":{  \n" +
                "              \"months\":3\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      {  \n" +
                "        \"or\":[  \n" +
                "          {  \n" +
                "            \"tag\":\"tag1\"\n" +
                "          },\n" +
                "          {  \n" +
                "            \"tag\":\"tag2\"\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      {  \n" +
                "        \"not\":{  \n" +
                "          \"tag\":\"not-tag\"\n" +
                "        }\n" +
                "      },\n" +
                "      {  \n" +
                "        \"not\":{  \n" +
                "          \"and\":[  \n" +
                "            {  \n" +
                "              \"location\":{  \n" +
                "                \"us_state\":\"WA\",\n" +
                "                \"date\":{  \n" +
                "                  \"months\":{  \n" +
                "                    \"start\":\"2011-05\",\n" +
                "                    \"end\":\"2012-02\"\n" +
                "                  }\n" +
                "                }\n" +
                "              }\n" +
                "            },\n" +
                "            {  \n" +
                "              \"tag\":\"woot\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        assertEquals(request.getResponseParser().parse(responseInfo), responseParser.parse(responseInfo));
    }
}
