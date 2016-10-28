package com.urbanairship.api.push.parse.notification.android;

import com.urbanairship.api.push.model.notification.android.PublicNotification;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PublicNotificationDeserializerTest {
    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testPublicNotification() throws Exception {
        String publicNotificationMinimalJson =
                "{" +
                        "\"alert\": \"Hello!\"" +
                "}";

        String publicNotificationMaximalJson =
                "{" +
                        "\"alert\": \"Hello!\"," +
                        "\"title\": \"A greeting\"," +
                        "\"summary\": \"A summary\"" +
                "}";

        PublicNotification minimalPublicNotification = MAPPER.readValue(publicNotificationMinimalJson, PublicNotification.class);
        assertNotNull(minimalPublicNotification);
        assertEquals(minimalPublicNotification.getAlert().get(), "Hello!");

        PublicNotification maximalPublicNotification = MAPPER.readValue(publicNotificationMaximalJson, PublicNotification.class);
        assertNotNull(maximalPublicNotification);
        assertEquals(maximalPublicNotification.getAlert().get(), "Hello!");
        assertEquals(maximalPublicNotification.getTitle().get(), "A greeting");
        assertEquals(maximalPublicNotification.getSummary().get(), "A summary");
    }
}
