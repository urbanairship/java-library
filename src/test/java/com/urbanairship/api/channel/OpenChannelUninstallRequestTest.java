package com.urbanairship.api.channel;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.model.ChannelUninstallResponse;
import com.urbanairship.api.client.Request;

import org.apache.http.entity.ContentType;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class OpenChannelUninstallRequestTest {
    OpenChannelUninstallRequest openChannelUninstallRequest = OpenChannelUninstallRequest.newRequest("address", "openPlatformName");

    URI baseURI = URI.create("https://go.urbanairship.com");

    @Test
    public void testBody() throws Exception {
        assertEquals(openChannelUninstallRequest.getRequestBody(), "{\"open_platform_name\":\"openPlatformName\",\"address\":\"address\"}");
    }

    @Test
    public void testHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(headers, openChannelUninstallRequest.getRequestHeaders());
    }

    @Test
    public void testMethod() {
        assertEquals(Request.HttpMethod.POST, openChannelUninstallRequest.getHttpMethod());
    }

    @Test
    public void testContentType() {
        assertEquals(ContentType.APPLICATION_JSON, openChannelUninstallRequest.getContentType());
    }

    @Test
    public void testUninstallURI() throws URISyntaxException {
        URI expectedUri = URI.create("https://go.urbanairship.com/api/channels/open/uninstall");
        assertEquals(expectedUri, openChannelUninstallRequest.getUri(baseURI));
    }

    @Test
    public void testResponseParser() throws Exception {

        ChannelUninstallResponse channelUninstallResponse = ChannelUninstallResponse.newBuilder()
                .setOk(true)
                .build();

        String response = "{\"ok\" : true}";
        assertEquals(openChannelUninstallRequest.getResponseParser().parse(response), channelUninstallResponse);
    }
}
