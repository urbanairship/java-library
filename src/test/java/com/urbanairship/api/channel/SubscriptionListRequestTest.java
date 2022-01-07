package com.urbanairship.api.channel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.model.ChannelAudience;
import com.urbanairship.api.channel.model.ChannelAudienceType;
import com.urbanairship.api.channel.model.subscriptionlist.SubscriptionList;
import com.urbanairship.api.channel.model.subscriptionlist.SubscriptionListAction;
import com.urbanairship.api.channel.model.subscriptionlist.SubscriptionListPayload;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import com.urbanairship.api.client.Request;

import org.apache.http.entity.ContentType;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class SubscriptionListRequestTest {

    private static final ObjectMapper MAPPER = ChannelObjectMapper.getInstance();

    @Test
    public void testSubscriptionLists() throws Exception {

        String androidChannel = UUID.randomUUID().toString();
        String iosChannel = UUID.randomUUID().toString();

        SubscriptionList subscriptionList = SubscriptionList.newBuilder()
            .setAction(SubscriptionListAction.SUBSCRIBE)
            .setListId("intriguing_ideas")
            .build();

        SubscriptionList subscriptionList2 = SubscriptionList.newBuilder()
            .setAction(SubscriptionListAction.UNSUBSCRIBE)
            .setListId("animal_facts")
            .build();

        SubscriptionListPayload payload = SubscriptionListPayload.newBuilder()
            .addSubscriptionList(subscriptionList)
            .addSubscriptionList(subscriptionList2)
            .setAudience(ChannelAudience.newBuilder()
            .addDeviceId(ChannelAudienceType.ANDROID_CHANNEL, androidChannel)
            .addDeviceId(ChannelAudienceType.IOS_CHANNEL, iosChannel).build())
            .build();

        SubscriptionListRequest request = SubscriptionListRequest.newRequest(payload);

        String expected = "{\n" +
            "  \"audience\": {\n" +
            "    \"android_channel\": [\n" +
            "      \"" + androidChannel + "\"\n" +
            "    ],\n" +
            "    \"ios_channel\": [\n" +
            "      \"" + iosChannel + "\"\n" +
            "    ]\n" +
            "    },\n" +
            "  \"subscription_lists\": [\n" +
            "    {\n" +
            "      \"action\": \"subscribe\",\n" +
            "      \"list_id\": \"intriguing_ideas\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"action\": \"unsubscribe\",\n" +
            "      \"list_id\": \"animal_facts\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

        JsonNode jsonFromObject = MAPPER.readTree(request.getRequestBody());
        JsonNode jsonFromString = MAPPER.readTree(expected);

        assertEquals(jsonFromObject, jsonFromString);
    }

    String androidChannel = UUID.randomUUID().toString();

    SubscriptionList subscriptionList = SubscriptionList.newBuilder()
        .setAction(SubscriptionListAction.SUBSCRIBE)
        .setListId("intriguing_ideas")
        .build();

    SubscriptionListPayload payload = SubscriptionListPayload.newBuilder()
        .addSubscriptionList(subscriptionList)
        .setAudience(ChannelAudience.newBuilder()
        .addDeviceId(ChannelAudienceType.ANDROID_CHANNEL, androidChannel).build())
        .build();

    SubscriptionListRequest request = SubscriptionListRequest.newRequest(payload);

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
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(request.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expextedURI = URI.create("https://go.urbanairship.com/api/channels/subscription_lists/");
        assertEquals(request.getUri(baseURI), expextedURI);
    }

}
