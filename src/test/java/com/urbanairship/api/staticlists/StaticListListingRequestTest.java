package com.urbanairship.api.staticlists;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.staticlists.model.StaticListListingResponse;
import com.urbanairship.api.staticlists.parse.StaticListsObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class StaticListListingRequestTest {
    private final static ObjectMapper mapper = StaticListsObjectMapper.getInstance();
    private final static String STATIC_LIST_LISTING_PATH = "/api/lists/";

    StaticListListingRequest request;

    @Before
    public void setup() {
        request = StaticListListingRequest.newRequest();
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

        URI expectedUri = URI.create("https://go.urbanairship.com" + STATIC_LIST_LISTING_PATH);
        assertEquals(request.getUri(baseURI), expectedUri);
    }

    @Test
    public void testParser() throws Exception {
        ResponseParser<StaticListListingResponse> responseParser = response -> mapper.readValue(response, StaticListListingResponse.class);

        String responseString = "{" +
                "\"ok\": true,\n" +
                "\"lists\": [\n" +
                    "{\n" +
                        "\"name\": \"platinum_members\",\n" +
                        "\"description\": \"loyalty program platinum members\",\n" +
                        "\"extra\": {\"key\": \"value\"},\n" +
                        "\"created\": \"2013-08-08T20:41:06\",\n" +
                        "\"last_updated\": \"2014-05-01T18:00:27\",\n" +
                        "\"channel_count\": 3145,\n" +
                        "\"status\": \"ready\"\n" +
                    "},\n" +
                    "{\n" +
                        "\"name\": \"silver_members\",\n" +
                        "\"description\": \"loyalty program silver members\",\n" +
                        "\"extra\": {\"key2\": \"value2\"},\n" +
                        "\"created\": \"2013-08-08T20:41:06\",\n" +
                        "\"last_updated\": \"2014-05-01T18:00:27\",\n" +
                        "\"channel_count\": 19999,\n" +
                        "\"status\": \"ready\"\n" +
                    "},\n" +
                    "{\n" +
                        "\"name\": \"gold_members\",\n" +
                        "\"description\": \"loyalty program gold members\",\n" +
                        "\"extra\": {\"key3\": \"value3\"},\n" +
                        "\"created\": \"2013-08-08T20:45:06\",\n" +
                        "\"last_updated\": \"2015-05-01T18:00:27\",\n" +
                        "\"channel_count\": 2142,\n" +
                        "\"status\": \"processing\"\n" +
                    "}\n" +
                "]\n" +
                "}";


        assertEquals(request.getResponseParser().parse(responseString), responseParser.parse(responseString));
    }
}
