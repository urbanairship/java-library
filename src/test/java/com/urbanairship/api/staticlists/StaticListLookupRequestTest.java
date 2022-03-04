package com.urbanairship.api.staticlists;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.staticlists.model.StaticListView;
import com.urbanairship.api.staticlists.parse.StaticListsObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class StaticListLookupRequestTest {
    private final static ObjectMapper mapper = StaticListsObjectMapper.getInstance();
    private final static String STATIC_LIST_LOOKUP_PATH = "/api/lists/abc123";
    StaticListLookupRequest request;

    @Before
    public void setup() {
        request = StaticListLookupRequest.newRequest("abc123");
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

        URI expectedUri = URI.create("https://go.urbanairship.com" + STATIC_LIST_LOOKUP_PATH);
        assertEquals(request.getUri(baseURI), expectedUri);
    }

    @Test
    public void testParser() throws Exception {
        ResponseParser<StaticListView> responseParser = new ResponseParser<StaticListView>() {
            @Override
            public StaticListView parse(String response) throws IOException {
                return mapper.readValue(response, StaticListView.class);
            }
        };

        String responseString = "{\n" +
                    "\"ok\": true,\n" +
                    "\"name\": \"platinum_members\",\n" +
                    "\"description\": \"loyalty program platinum members\",\n" +
                    "\"extra\": {\"key\": \"value\"},\n" +
                    "\"created\": \"2013-08-08T20:41:06\",\n" +
                    "\"last_updated\": \"2014-05-01T18:00:27\",\n" +
                    "\"channel_count\": 3145,\n" +
                    "\"status\": \"ready\"\n" +
                "}";

        assertEquals(request.getResponseParser().parse(responseString), responseParser.parse(responseString));
    }

}
