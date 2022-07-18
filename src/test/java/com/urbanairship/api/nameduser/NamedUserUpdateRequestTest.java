package com.urbanairship.api.nameduser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.model.attributes.Attribute;
import com.urbanairship.api.channel.model.attributes.AttributeAction;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.nameduser.model.NamedUserUpdateChannel;
import com.urbanairship.api.nameduser.model.NamedUserUpdateChannelAction;
import com.urbanairship.api.nameduser.model.NamedUserUpdateDeviceType;
import com.urbanairship.api.nameduser.model.NamedUserUpdatePayload;
import com.urbanairship.api.nameduser.parse.NamedUserObjectMapper;
import org.apache.http.entity.ContentType;
import org.junit.Test;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;


public class NamedUserUpdateRequestTest {

    private final String channelId = UUID.randomUUID().toString();
    private static final ObjectMapper MAPPER = NamedUserObjectMapper.getInstance();

    @Test
    public void testNamedUserUpdate() throws Exception {

        List<String> tagsList = new ArrayList<>();
        tagsList.add("tag1");
        tagsList.add("tag2");

        NamedUserUpdateChannel namedUserUpdateChannel = NamedUserUpdateChannel.newBuilder()
        .addChannel(NamedUserUpdateDeviceType.ANDROID_CHANNEL, channelId)
        .build();

        NamedUserUpdateChannel namedUserUpdateEmail = NamedUserUpdateChannel.newBuilder()
                .addEmailAddress("test@email.com")
                .build();

        Attribute pseudo = Attribute.newBuilder()
                .setAction(AttributeAction.SET)
                .setKey("pseudo")
                .setValue("Pataki")
                .build();

        NamedUserUpdatePayload namedUserUpdatePayload = NamedUserUpdatePayload.newBuilder()
                .addAttribute(pseudo)
                .addTags("test1", tagsList)
                .setTags("tes2", tagsList)
                .removeTags("test3", tagsList)
                .addNamedUserUpdateChannel(namedUserUpdateEmail)
                .addNamedUserUpdateChannel(namedUserUpdateChannel)
                .setAction(NamedUserUpdateChannelAction.ASSOCIATE)
                .build();

        NamedUserUpdateRequest request = NamedUserUpdateRequest.newRequest("named_user_id", namedUserUpdatePayload);

        String expected = "{\"associate\":[{\"email_address\":\"test@email.com\"},{\"channel_id\":\"" + channelId + "\",\"device_type\":\"android\"}],\"tags\":{\"add\":{\"test1\":[\"tag1\",\"tag2\"]},\"set\":{\"tes2\":[\"tag1\",\"tag2\"]},\"remove\":{\"test3\":[\"tag1\",\"tag2\"]}},\"attributes\":[{\"key\":\"pseudo\",\"action\":\"set\",\"value\":\"Pataki\"}]}";

        JsonNode jsonFromObject = MAPPER.readTree(request.getRequestBody());
        JsonNode jsonFromString = MAPPER.readTree(expected);

        assertEquals(jsonFromObject, jsonFromString);
    }

    NamedUserUpdateChannel namedUserUpdateChannel = NamedUserUpdateChannel.newBuilder()
    .addChannel(NamedUserUpdateDeviceType.ANDROID_CHANNEL, channelId)
    .build();

    NamedUserUpdatePayload namedUserUpdatePayload = NamedUserUpdatePayload.newBuilder()
    .addNamedUserUpdateChannel(namedUserUpdateChannel)
    .setAction(NamedUserUpdateChannelAction.ASSOCIATE)
    .build();

    NamedUserUpdateRequest request = NamedUserUpdateRequest.newRequest("named_user_id", namedUserUpdatePayload);

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

        URI expectedURI = URI.create("https://go.urbanairship.com/api/named_users/named_user_id");
        assertEquals(request.getUri(baseURI), expectedURI);
    }
}
