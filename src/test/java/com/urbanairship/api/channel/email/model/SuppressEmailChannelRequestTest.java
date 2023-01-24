package com.urbanairship.api.channel.email.model;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.OpenChannelUninstallRequest;
import com.urbanairship.api.channel.model.ChannelUninstallResponse;
import com.urbanairship.api.channel.model.email.SuppressEmailChannelRequest;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.common.model.GenericResponse;
import org.apache.http.entity.ContentType;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SuppressEmailChannelRequestTest {
    private final SuppressEmailChannelRequest suppressEmailChannelRequest = SuppressEmailChannelRequest.newRequest("address", "test");

    URI baseURI = URI.create("https://go.urbanairship.com");

    @Test
    public void testBody() throws Exception {
        assertEquals(suppressEmailChannelRequest.getRequestBody(), "{\"reason\":\"test\",\"address\":\"address\"}");
    }

    @Test
    public void testHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(headers, suppressEmailChannelRequest.getRequestHeaders());
    }

    @Test
    public void testMethod() {
        assertEquals(Request.HttpMethod.POST, suppressEmailChannelRequest.getHttpMethod());
    }

    @Test
    public void testContentType() {
        assertEquals(ContentType.APPLICATION_JSON, suppressEmailChannelRequest.getContentType());
    }

    @Test
    public void testUninstallURI() throws URISyntaxException {
        URI expectedUri = URI.create("https://go.urbanairship.com/api/channels/email/suppress");
        assertEquals(expectedUri, suppressEmailChannelRequest.getUri(baseURI));
    }

    @Test
    public void testResponseParser() throws Exception {

        GenericResponse genericResponse = new GenericResponse(true, null, null, null, null, null);

        String response = "{\"ok\" : true}";
        assertEquals(suppressEmailChannelRequest.getResponseParser().parse(response), genericResponse);
    }
}
