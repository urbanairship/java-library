package com.urbanairship.api.channel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.model.ChannelUninstallDevice;
import com.urbanairship.api.channel.model.ChannelUninstallDeviceType;
import com.urbanairship.api.channel.model.ChannelUninstallPayload;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import com.urbanairship.api.client.Request;
import org.apache.http.entity.ContentType;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class ChannelUninstallRequestTest {

    private static final ObjectMapper MAPPER = ChannelObjectMapper.getInstance();

    @Test
    public void testUninstallChannel() throws Exception {
    String iosChannel = UUID.randomUUID().toString();
    String androidChannel = UUID.randomUUID().toString();

    
    Set<ChannelUninstallDevice> channels = ImmutableSet.of(
        new ChannelUninstallDevice(iosChannel, ChannelUninstallDeviceType.IOS),
        new ChannelUninstallDevice(androidChannel, ChannelUninstallDeviceType.ANDROID)
    );
    
    ChannelUninstallPayload payload = ChannelUninstallPayload.newBuilder()
        .setChannels(channels)
        .build();

    ChannelUninstallRequest request = ChannelUninstallRequest.newRequest(payload);
    
    String expected = "[" +
            "{" +
                "\"device_type\":\"ios\"," +
                "\"channel_id\":\"" + iosChannel + "\"" +
            "}," +
            "{" +
                "\"device_type\":\"android\"," +
                "\"channel_id\":\"" + androidChannel + "\"" +
            "}" +
        "]";

        JsonNode jsonFromObject = MAPPER.readTree(request.getRequestBody());
        JsonNode jsonFromString = MAPPER.readTree(expected);

        assertEquals(jsonFromObject, jsonFromString);
    }


    String iosChannel = UUID.randomUUID().toString();
    String androidChannel = UUID.randomUUID().toString();

    
    Set<ChannelUninstallDevice> channels = ImmutableSet.of(
        new ChannelUninstallDevice(iosChannel, ChannelUninstallDeviceType.IOS),
        new ChannelUninstallDevice(androidChannel, ChannelUninstallDeviceType.ANDROID)
    );
    
    ChannelUninstallPayload payload = ChannelUninstallPayload.newBuilder()
        .setChannels(channels)
        .build();

    ChannelUninstallRequest request = ChannelUninstallRequest.newRequest(payload);
        
    @Test
    public void testContentType() throws Exception {
        assertEquals(request.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(request.getHttpMethod(), Request.HttpMethod.POST);
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

        URI expectedURI = URI.create("https://go.urbanairship.com/api/channels/uninstall/");
        assertEquals(request.getUri(baseURI), expectedURI);
    }
}
