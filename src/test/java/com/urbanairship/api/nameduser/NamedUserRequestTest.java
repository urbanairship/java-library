package com.urbanairship.api.nameduser;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.model.ChannelType;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.nameduser.parse.NamedUserObjectMapper;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.entity.ContentType;
import org.codehaus.jackson.map.ObjectMapper;
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
        .setChannelId(channelId)
        .setDeviceType(ChannelType.IOS)
        .setNamedUserid(namedUserId);
    private final NamedUserRequest disassociationRequest = NamedUserRequest.newDisassociationRequest()
        .setChannelId(channelId)
        .setDeviceType(ChannelType.IOS)
        .setNamedUserid(namedUserId);

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
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION);

        assertEquals(associationRequest.getRequestHeaders(), headers);
        assertEquals(disassociationRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedURI = URI.create("https://go.urbanairship.com/api/named_users/associate");
        assertEquals(associationRequest.getUri(baseURI), expectedURI);

        expectedURI = URI.create("https://go.urbanairship.com/api/named_users/disassociate");
        assertEquals(disassociationRequest.getUri(baseURI), expectedURI);
    }

    @Test
    public void testParser() throws Exception {
        String response = "{\"ok\" : true}";
        assertEquals(response, associationRequest.getResponseParser().parse(response));
        assertEquals(response, disassociationRequest.getResponseParser().parse(response));
    }

}
