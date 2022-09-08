package com.urbanairship.api.staticlists;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.common.model.ErrorDetails;
import com.urbanairship.api.staticlists.model.StaticListView;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class StaticListLookupRequestTest {
    private final static String STATIC_LIST_LOOKUP_PATH = "/api/lists/abc123";
    StaticListLookupRequest request;

    @Before
    public void setup() {
        request = StaticListLookupRequest.newRequest("abc123");
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

        URI expectedUri = URI.create("https://go.urbanairship.com" + STATIC_LIST_LOOKUP_PATH);
        assertEquals(request.getUri(baseURI), expectedUri);
    }

    @Test
    public void testParser() throws Exception {

        StaticListView staticListView = StaticListView.newBuilder()
                .setChannelCount(3145)
                .setName("platinum_members")
                .setCreated(DateTime.parse("2013-08-08T20:41:06.000Z"))
                .addExtra("key", "value")
                .setLastUpdated(DateTime.parse("2014-05-01T18:00:27.000Z"))
                .setStatus("ready")
                .setOk(true)
                .setError("error")
                .setErrorDetails(new ErrorDetails("an error", null))
                .build();

        String responseString = "{\n" +
                    "\"ok\": true,\n" +
                    "\"name\": \"platinum_members\",\n" +
                    "\"extra\": {\"key\": \"value\"},\n" +
                    "\"created\": \"2013-08-08T20:41:06\",\n" +
                    "\"last_updated\": \"2014-05-01T18:00:27\",\n" +
                    "\"channel_count\": 3145,\n" +
                    "\"status\": \"ready\",\n" +
                    "\"error\": \"error\",\n" +
                    "\"details\": {\"error\": \"an error\"}\n" +
                "}";

        assertEquals(request.getResponseParser().parse(responseString), staticListView);
    }

}
