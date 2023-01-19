package com.urbanairship.api.customevents;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.customevents.model.CustomEventBody;
import com.urbanairship.api.customevents.model.CustomEventChannelType;
import com.urbanairship.api.customevents.model.CustomEventPayload;
import com.urbanairship.api.customevents.model.CustomEventResponse;
import com.urbanairship.api.customevents.model.CustomEventUser;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.apache.http.entity.ContentType;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CustomEventRequestTest {
    CustomEventUser customEventUser1 = CustomEventUser.newBuilder()
            .setCustomEventChannelType(CustomEventChannelType.ANDROID_CHANNEL)
            .setChannel("channel")
            .build();

    CustomEventBody customEventBody1 = CustomEventBody.newBuilder()
            .setName("purchased")
            .setSessionId("sessionId")
            .build();

    // The date and time when the event occurred.
    DateTime occurred = new DateTime(2015, 5, 2, 2, 31, 22, DateTimeZone.UTC);

    CustomEventPayload customEventPayload1 = CustomEventPayload.newBuilder()
            .setCustomEventBody(customEventBody1)
            .setCustomEventUser(customEventUser1)
            .setOccurred(occurred)
            .build();

    CustomEventUser customEventUser2 = CustomEventUser.newBuilder()
            .setCustomEventChannelType(CustomEventChannelType.IOS_CHANNEL)
            .setChannel("channel")
            .build();

    CustomEventBody customEventBody2 = CustomEventBody.newBuilder()
            .setName("rented")
            .setSessionId("sessionId")
            .build();
    CustomEventPayload customEventPayload2 = CustomEventPayload.newBuilder()
            .setCustomEventBody(customEventBody2)
            .setCustomEventUser(customEventUser2)
            .setOccurred(occurred)
            .build();

    CustomEventRequest customEventRequestUnique = CustomEventRequest.newRequest(customEventPayload1);

    List<CustomEventPayload> customEventPayloads = Arrays.asList(customEventPayload1,customEventPayload2);

    CustomEventRequest customEventRequestMultiple = CustomEventRequest.newRequest(customEventPayloads);

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(customEventRequestUnique.getRequestHeaders(), headers);
        assertEquals(customEventRequestMultiple.getRequestHeaders(), headers);

    }

    @Test
    public void testBody() throws Exception {
        assertEquals(customEventRequestUnique.getRequestBody(), "[" + customEventPayload1.toJSON() + "]");
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(customEventRequestUnique.getHttpMethod(), Request.HttpMethod.POST);
        assertEquals(customEventRequestMultiple.getHttpMethod(), Request.HttpMethod.POST);
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(customEventRequestUnique.getContentType(), ContentType.APPLICATION_JSON);
        assertEquals(customEventRequestMultiple.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expextedURI = URI.create("https://go.urbanairship.com/api/custom-events/");
        assertEquals(customEventRequestUnique.getUri(baseURI), expextedURI);
        assertEquals(customEventRequestMultiple.getUri(baseURI), expextedURI);
    }

    @Test
    public void testCustomEventParser() throws Exception {
        ResponseParser<CustomEventResponse> responseParser = response -> PushObjectMapper.getInstance().readValue(response, CustomEventResponse.class);

        String response = "{\"ok\" : true,\"operation_id\" : \"df6a6b50\"}";
        assertEquals(customEventRequestUnique.getResponseParser().parse(response), responseParser.parse(response));
        assertEquals(customEventRequestMultiple.getResponseParser().parse(response), responseParser.parse(response));
    }
}
