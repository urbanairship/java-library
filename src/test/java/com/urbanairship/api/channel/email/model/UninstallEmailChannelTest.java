package com.urbanairship.api.channel.email.model;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.model.email.EmailChannelResponse;
import com.urbanairship.api.channel.model.email.UninstallEmailChannel;
import com.urbanairship.api.channel.model.email.UninstallEmailChannelRequest;
import com.urbanairship.api.client.Request;
import org.apache.http.entity.ContentType;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class UninstallEmailChannelTest {
    private final static String UNINSTALL_EMAIL_CHANNEL_PATH = "/api/channels/email/uninstall";

    UninstallEmailChannelRequest UninstallEmailRequest;

    @Before
    public void setupCreate() {
        UninstallEmailChannel uninstallEmailChannel = UninstallEmailChannel.newBuilder()
                .setEmailAddress("name@example.com")
                .build();

        UninstallEmailRequest = UninstallEmailChannelRequest.newRequest(uninstallEmailChannel);
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(UninstallEmailRequest.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(UninstallEmailRequest.getHttpMethod(), Request.HttpMethod.POST);
    }

    @Test
    public void testBody() throws Exception {
        assertEquals(UninstallEmailRequest.getRequestBody(), "{\"email_address\":\"name@example.com\"}");

    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);

        assertEquals(UninstallEmailRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedCreateUri = URI.create("https://go.urbanairship.com" + UNINSTALL_EMAIL_CHANNEL_PATH);
        assertEquals(UninstallEmailRequest.getUri(baseURI), expectedCreateUri);
    }

    @Test
    public void testUninstallEmailChannelParser() throws Exception {
        EmailChannelResponse emailChannelResponse = EmailChannelResponse.newBuilder()
                .setOk(true)
                .build();

        String response = "{\"ok\" : true}";
        assertEquals(UninstallEmailRequest.getResponseParser().parse(response), emailChannelResponse);
    }
}