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

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CustomEventRequestTest {
    CustomEventUser customEventUser = CustomEventUser.newBuilder()
            .setCustomEventChannelType(CustomEventChannelType.ANDROID_CHANNEL)
            .setChannel("channel")
            .build();

    CustomEventBody customEventBody = CustomEventBody.newBuilder()
            .setName("purchased")
            .setSessionId("sessionId")
            .build();

    DateTime occurred = new DateTime(2015, 5, 2, 2, 31, 22, DateTimeZone.UTC);

    CustomEventPayload customEventPayload = CustomEventPayload.newBuilder()
            .setCustomEventBody(customEventBody)
            .setCustomEventUser(customEventUser)
            .setOccurred(occurred)
            .build();

    CustomEventRequest customEventRequest = CustomEventRequest.newRequest(customEventPayload);

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(customEventRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testBody() throws Exception {
        assertEquals(customEventRequest.getRequestBody(), customEventPayload.toJSON());
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(customEventRequest.getHttpMethod(), Request.HttpMethod.POST);
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(customEventRequest.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedURI = URI.create("https://go.urbanairship.com/api/custom-events/");
        assertEquals(customEventRequest.getUri(baseURI), expectedURI);
    }

    @Test
    public void testCustomEventParser() throws Exception {
        ResponseParser responseParser = new ResponseParser<CustomEventResponse>() {
            @Override
            public CustomEventResponse parse(String response) throws IOException {
                return PushObjectMapper.getInstance().readValue(response, CustomEventResponse.class);
            }
        };

        String response = "{\"ok\" : true,\"operation_id\" : \"df6a6b50\"}";
        assertEquals(customEventRequest.getResponseParser().parse(response), responseParser.parse(response));
    }
}
