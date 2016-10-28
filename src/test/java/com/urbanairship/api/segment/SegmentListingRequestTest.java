package com.urbanairship.api.segment;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.segments.SegmentListingRequest;
import com.urbanairship.api.segments.model.SegmentListingResponse;
import com.urbanairship.api.segments.parse.SegmentObjectMapper;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SegmentListingRequestTest {
    private static final String TEST_QUERY_PATH = "/api/segments/";
    private static final ObjectMapper mapper = SegmentObjectMapper.getInstance();

    SegmentListingRequest request;

    @Before
    public void setup() {
        request = SegmentListingRequest.newRequest();
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

        URI expectedURI = URI.create("https://go.urbanairship.com" + TEST_QUERY_PATH);
        assertEquals(request.getUri(baseURI), expectedURI);
    }

    @Test
    public void testListParser() throws Exception {
        ResponseParser responseParser = new ResponseParser<SegmentListingResponse>() {
            @Override
            public SegmentListingResponse parse(String response) throws IOException {
                return mapper.readValue(response, SegmentListingResponse.class);
            }
        };

        String responseList = "{\n" +
                "   \"next_page\": \"https://go.urbanairship.com/api/segments?limit=1&sort=id&order=asc&start=3832cf72-cb44-4132-a11f-eafb41b82f64\",\n" +
                "   \"segments\": [\n" +
                "      {\n" +
                "        \"creation_date\": 1346248822221,\n" +
                "        \"display_name\": \"segment1\",\n" +
                "        \"id\": \"00c0d899-a595-4c66-9071-bc59374bbe6a\",\n" +
                "        \"modification_date\": 1346248822221\n" +
                "      },\n" +
                "      {\n" +
                "        \"creation_date\": 1346248822222,\n" +
                "        \"display_name\": \"segment2\",\n" +
                "        \"id\": \"00c0d899-a595-4c66-9071-bc59374bbe6b\",\n" +
                "        \"modification_date\": 1346248822222\n" +
                "      },\n" +
                "      {\n" +
                "        \"creation_date\": 1346248822223,\n" +
                "        \"display_name\": \"segment3\",\n" +
                "        \"id\": \"00c0d899-a595-4c66-9071-bc59374bbe6c\",\n" +
                "        \"modification_date\": 1346248822223\n" +
                "      },\n" +
                "      {\n" +
                "        \"creation_date\": 1346248822224,\n" +
                "        \"display_name\": \"segment4\",\n" +
                "        \"id\": \"00c0d899-a595-4c66-9071-bc59374bbe6d\",\n" +
                "        \"modification_date\": 1346248822224\n" +
                "      },\n" +
                "      {\n" +
                "        \"creation_date\": 1346248822225,\n" +
                "        \"display_name\": \"segment5\",\n" +
                "        \"id\": \"00c0d899-a595-4c66-9071-bc59374bbe6e\",\n" +
                "        \"modification_date\": 1346248822225\n" +
                "      }\n" +
                "   ]\n" +
                "}";

        assertEquals(request.getResponseParser().parse(responseList), responseParser.parse(responseList));
    }

}
