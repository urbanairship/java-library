package com.urbanairship.api.staticlists;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.common.model.ErrorDetails;
import com.urbanairship.api.common.model.GenericResponse;

import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class StaticListDeleteRequestTest {
    private final static String STATIC_LIST_DELETE_PATH = "/api/lists/abc123";

    StaticListDeleteRequest request;

    @Before
    public void setup() {
        request = StaticListDeleteRequest.newRequest("abc123");
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

        URI expectedUri = URI.create("https://go.urbanairship.com" + STATIC_LIST_DELETE_PATH);
        assertEquals(request.getUri(baseURI), expectedUri);
    }

    @Test
    public void testScheduleParser() throws Exception {

        ErrorDetails errorDetails = new ErrorDetails("The key chanel is not allowed in this context", null);

        GenericResponse genericResponse = new GenericResponse(true, "1769297b-1640-43a4-af84-3e0ece89efe", "error", errorDetails);

        String responseJson = "{" +
                "\"ok\": true," +
                "\"operation_id\": \"1769297b-1640-43a4-af84-3e0ece89efe\"," +
                "\"error\": \"error\"," +
                "\"details\": {\"error\": \"The key chanel is not allowed in this context\"" +
                "}" +
                "}";
        assertEquals(request.getResponseParser().parse(responseJson), genericResponse);
    }
}