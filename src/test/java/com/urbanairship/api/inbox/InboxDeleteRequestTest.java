package com.urbanairship.api.inbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.common.model.GenericResponse;
import com.urbanairship.api.common.parse.CommonObjectMapper;
import com.urbanairship.api.push.parse.PushObjectMapper;

import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class InboxDeleteRequestTest {

    private final static ObjectMapper mapper = CommonObjectMapper.getInstance();
    InboxDeleteRequest InboxDeleteRequest = com.urbanairship.api.inbox.InboxDeleteRequest.newRequest("id");

    @Test
    public void testContentType() throws Exception {
        assertEquals(InboxDeleteRequest.getContentType(), null);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(InboxDeleteRequest.getHttpMethod(), Request.HttpMethod.DELETE);
    }

    @Test
    public void testBody() throws Exception {
        assertEquals(InboxDeleteRequest.getRequestBody(), null);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(InboxDeleteRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expextedURI = URI.create("https://go.urbanairship.com/api/user/messages/id");
        assertEquals(InboxDeleteRequest.getUri(baseURI), expextedURI);
    }

    @Test
    public void testResponseParser() throws Exception {

        String responseJson = "{\"ok\": true}";
        final ResponseParser responseParser = new ResponseParser<GenericResponse>() {
            @Override
            public GenericResponse parse(String response) throws IOException {
                return mapper.readValue(response, GenericResponse.class);
            }
        };
        assertEquals(InboxDeleteRequest.getResponseParser().parse(responseJson), responseParser.parse(responseJson));

    }
}
