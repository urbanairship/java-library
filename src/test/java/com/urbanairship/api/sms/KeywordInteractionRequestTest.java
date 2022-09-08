package com.urbanairship.api.sms;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import org.apache.http.entity.ContentType;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class KeywordInteractionRequestTest {

    KeywordInteractionRequest request = KeywordInteractionRequest.newRequest("1234556")
        .addKeyword("stop")
        .addSenderId("test")
        .addSenderId("test2");

    @Test
    public void testContentType() throws Exception {
        assertEquals(request.getContentType(), ContentType.APPLICATION_JSON);

    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(request.getHttpMethod(), Request.HttpMethod.POST);
    }

    @Test
    public void testBody() throws Exception {
        assertEquals(request.getRequestBody(), "{\"keyword\":\"stop\",\"sender_ids\":[\"test\",\"test2\"]}");

    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(request.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedURI = URI.create("https://go.urbanairship.com/api/sms/1234556/keywords");
        assertEquals(request.getUri(baseURI), expectedURI);
    }

    @Test
    public void testPushParser() throws Exception {
        String response = "{\"ok\" : true}";
        assertEquals(response, request.getResponseParser().parse(response));
    }
}