package com.urbanairship.api.templates.parse;

import com.urbanairship.api.push.model.InApp;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.templates.model.PartialPushPayload;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class PartialPushPayloadSerializerTest {
    private static final ObjectMapper MAPPER = TemplatesObjectMapper.getInstance();

    @Test
    public void testPartialPushPayloadSerializer() throws Exception {

        PartialPushPayload partialPushPayload = PartialPushPayload.newBuilder()
                .setNotification(Notification.newBuilder()
                        .setAlert("hello everyone")
                        .build())
                .setInApp(InApp.newBuilder()
                        .setAlert("This is in-app!")
                        .build())
                .build();

        String partialPushPayloadSerialized = MAPPER.writeValueAsString(partialPushPayload);
        String partialPushPayloadString =
                "{" +
                    "\"notification\":{\"alert\":\"hello everyone\"}," +
                    "\"in_app\":{\"alert\":\"This is in-app!\",\"display_type\":\"banner\"}" +
                "}";

        assertEquals(partialPushPayloadSerialized, partialPushPayloadString);
    }
}
