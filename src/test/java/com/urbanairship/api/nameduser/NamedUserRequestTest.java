package com.urbanairship.api.nameduser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.model.ChannelType;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.nameduser.parse.NamedUserObjectMapper;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.entity.ContentType;
import org.junit.Assert;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class NamedUserRequestTest {

    private final ObjectMapper mapper = NamedUserObjectMapper.getInstance();
    private final String channelId = UUID.randomUUID().toString();
    private final String namedUserId = RandomStringUtils.random(10);

    private final NamedUserRequest associationRequest = NamedUserRequest.newAssociationRequest()
        .setChannel(channelId, ChannelType.IOS)
        .setNamedUserId(namedUserId);
    private final NamedUserRequest disassociationRequest = NamedUserRequest.newDisassociationRequest()
        .setChannel(channelId, ChannelType.IOS)
        .setNamedUserId(namedUserId);

    @Test
    public void testContentType() throws Exception {
        assertEquals(associationRequest.getContentType(), ContentType.APPLICATION_JSON);
        assertEquals(disassociationRequest.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(associationRequest.getHttpMethod(), Request.HttpMethod.POST);
        assertEquals(disassociationRequest.getHttpMethod(), Request.HttpMethod.POST);
    }

    @Test
    public void testBody() throws Exception {
        String CHANNEL_KEY = "channel_id";
        String DEVICE_TYPE_KEY = "device_type";
        String NAMED_USER_ID_KEY = "named_user_id";

        Map<String, String> associationPayload = new HashMap<String, String>();
        associationPayload.put(CHANNEL_KEY, channelId);
        associationPayload.put(DEVICE_TYPE_KEY, ChannelType.IOS.getIdentifier());
        associationPayload.put(NAMED_USER_ID_KEY,namedUserId);

        Map<String, String> disassociationPayload = new HashMap<String, String>();
        disassociationPayload.put(CHANNEL_KEY, channelId);
        disassociationPayload.put(DEVICE_TYPE_KEY, ChannelType.IOS.getIdentifier());
        disassociationPayload.put(NAMED_USER_ID_KEY,namedUserId);

        String associateRequestBody =mapper.writeValueAsString(associationPayload);
        String disassociateRequestBody = mapper.writeValueAsString(disassociationPayload);

        assertEquals(associationRequest.getRequestBody(), associateRequestBody);
        assertEquals(disassociationRequest.getRequestBody(), disassociateRequestBody);
    }

    @Test
    public void testEmailChannelRequestBody() throws Exception {
        String namedUserId = "namedUser";

        NamedUserRequest namedUserRequest = NamedUserRequest.newAssociationRequest();
        namedUserRequest.setChannel(channelId, ChannelType.EMAIL);
        namedUserRequest.setNamedUserId(namedUserId);

        NamedUserRequest disassociateRequest = NamedUserRequest.newDisassociationRequest();
        disassociateRequest.setNamedUserId(namedUserId);
        disassociateRequest.setChannel(channelId, ChannelType.EMAIL);

        String jsonStr = "{\"channel_id\":\"" + channelId + "\",\"named_user_id\":\""+ namedUserId +"\"}";

        JsonNode expectedJson = mapper.readTree(jsonStr);
        JsonNode actualJson = mapper.readTree(namedUserRequest.getRequestBody());
        JsonNode actualJsonDisassociate = mapper.readTree(disassociateRequest.getRequestBody());

        assertEquals(expectedJson, actualJson);
        assertEquals(expectedJson, actualJsonDisassociate);
    }

    @Test
    public void testEmailRequestBody() throws Exception {
        String emailAddress = "testEmail@test.com";
        String namedUserId = "namedUser";

        NamedUserRequest namedUserRequest = NamedUserRequest.newAssociationRequest();
        namedUserRequest.setNamedUserId(namedUserId);
        namedUserRequest.setEmail(emailAddress);

        NamedUserRequest disassociateRequest = NamedUserRequest.newDisassociationRequest();
        disassociateRequest.setNamedUserId(namedUserId);
        disassociateRequest.setEmail(emailAddress);

        String jsonStr = "{\"email_address\":\"" + emailAddress + "\",\"named_user_id\":\""+ namedUserId +"\"}";

        JsonNode expectedJson = mapper.readTree(jsonStr);
        JsonNode actualJson = mapper.readTree(namedUserRequest.getRequestBody());
        JsonNode actualJsonDisassociate = mapper.readTree(disassociateRequest.getRequestBody());

        assertEquals(expectedJson, actualJson);
        assertEquals(expectedJson, actualJsonDisassociate);
    }

    @Test
    public void testSmsRequestBody() throws Exception {
        String namedUserId = "namedUser";

        NamedUserRequest namedUserRequest = NamedUserRequest.newAssociationRequest();
        namedUserRequest.setChannel(channelId, ChannelType.SMS);
        namedUserRequest.setNamedUserId(namedUserId);

        NamedUserRequest disassociateRequest = NamedUserRequest.newDisassociationRequest();
        disassociateRequest.setNamedUserId(namedUserId);
        disassociateRequest.setChannel(channelId, ChannelType.SMS);

        String jsonStr = "{\"channel_id\":\"" + channelId + "\",\"named_user_id\":\""+ namedUserId +"\"}";

        JsonNode expectedJson = mapper.readTree(jsonStr);
        JsonNode actualJson = mapper.readTree(namedUserRequest.getRequestBody());
        JsonNode actualJsonDisassociate = mapper.readTree(disassociateRequest.getRequestBody());

        assertEquals(expectedJson, actualJson);
        assertEquals(expectedJson, actualJsonDisassociate);
    }

    @Test
    public void testEmailAndChannel() {
        Exception exception = Assert.assertThrows(IllegalArgumentException.class, () -> {
            NamedUserRequest namedUserRequest = NamedUserRequest.newAssociationRequest();
            namedUserRequest.setChannel(channelId, ChannelType.EMAIL);
            namedUserRequest.setNamedUserId("namedUserId");
            namedUserRequest.setEmail("testEmail@test.com");
            namedUserRequest.getRequestBody();
    
        });
    
        String expectedMessage = "Both Channel ID and email cannot be set. Set either the channel ID or the email address.";
        String actualMessage = exception.getMessage();

        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(associationRequest.getRequestHeaders(), headers);
        assertEquals(disassociationRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedURI = URI.create("https://go.urbanairship.com/api/named_users/associate/");
        assertEquals(associationRequest.getUri(baseURI), expectedURI);

        expectedURI = URI.create("https://go.urbanairship.com/api/named_users/disassociate/");
        assertEquals(disassociationRequest.getUri(baseURI), expectedURI);
    }

    @Test
    public void testParser() throws Exception {
        String response = "{\"ok\" : true}";
        assertEquals(response, associationRequest.getResponseParser().parse(response));
        assertEquals(response, disassociationRequest.getResponseParser().parse(response));
    }
}
