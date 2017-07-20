package com.urbanairship.api.push.parse.richpush;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.notification.richpush.RichPushMessage;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RichPushDevicePayloadSerializerTest {
    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testOptions() throws Exception {
        RichPushMessage message = RichPushMessage.newBuilder()
                .setTitle("T")
                .setBody("B")
                .setExpiry(PushExpiry.newBuilder().setExpirySeconds(3600).build())
                .build();

        assertTrue(message.getExpiry().isPresent());

        String pushJson = mapper.writeValueAsString(message);
        String json
                = "{"
                + "\"title\":\"T\","
                + "\"body\":\"B\","
                + "\"content_type\":\"text/html\","
                + "\"content_encoding\":\"utf8\","
                + "\"expiry\":3600"
                + "}";
        assertEquals(json, pushJson);
    }
}
