package com.urbanairship.api.push.parse.notification.android;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.push.model.notification.android.PublicNotification;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class PublicNotificationSerializerTest {
    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testPublicNotificationSerializer() throws Exception {
        String json =
                "{" +
                        "\"summary\":\"A summary\"," +
                        "\"alert\":\"Hello!\"," +
                        "\"title\":\"A greeting\"" +
                "}";

        PublicNotification publicNotification = PublicNotification.newBuilder()
                .setAlert("Hello!")
                .setTitle("A greeting")
                .setSummary("A summary")
                .build();

        String serializedJson = MAPPER.writeValueAsString(publicNotification);
        assertEquals(json, serializedJson);
    }
}
