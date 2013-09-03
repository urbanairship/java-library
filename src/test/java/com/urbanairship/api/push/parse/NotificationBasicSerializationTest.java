package com.urbanairship.api.push.parse;

import com.urbanairship.api.push.model.notification.Notification;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NotificationBasicSerializationTest {

    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testAlert() throws Exception {

        Notification notification = Notification.newBuilder()
            .setAlert("alert")
            .build();

        String json = mapper.writeValueAsString(notification);
        assertTrue(json.equals("{\"alert\":\"alert\"}"));

    }

}
