package com.urbanairship.api.inbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.common.model.GenericResponse;
import com.urbanairship.api.common.parse.CommonObjectMapper;

import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class InboxDeleteRequestTest {

    private final static ObjectMapper mapper = CommonObjectMapper.getInstance();
    InboxDeleteRequest InboxDeleteRequest = com.urbanairship.api.inbox.InboxDeleteRequest.newRequest("id");

    @Test
    public void testContentType() throws Exception {
        assertNull(InboxDeleteRequest.getContentType());
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(InboxDeleteRequest.getHttpMethod(), Request.HttpMethod.DELETE);
    }

    @Test
    public void testBody() throws Exception {
        assertNull(InboxDeleteRequest.getRequestBody());
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(InboxDeleteRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedURI = URI.create("https://go.urbanairship.com/api/user/messages/id");
        assertEquals(InboxDeleteRequest.getUri(baseURI), expectedURI);
    }

    @Test
    public void testResponseParser() throws Exception {

        GenericResponse genericResponse = new GenericResponse(true,null,null,null, null, null);

        String responseJson = "{\"ok\": true}";

        assertEquals(InboxDeleteRequest.getResponseParser().parse(responseJson), genericResponse);

    }
}
