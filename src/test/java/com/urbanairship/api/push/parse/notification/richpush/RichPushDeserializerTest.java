package com.urbanairship.api.push.parse.notification.richpush;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.notification.richpush.RichPushMessage;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class RichPushDeserializerTest {
    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testSimple() throws Exception {
        String json
                = "{"
                + "  \"title\": \"T\","
                + "  \"body\": \"B\""
                + "}";

        RichPushMessage expected = RichPushMessage.newBuilder()
                .setTitle("T")
                .setBody("B")
                .build();

        RichPushMessage payload = mapper.readValue(json, RichPushMessage.class);
        assertNotNull(payload);
        assertNotNull(payload.getTitle());
        assertNotNull(payload.getBody());
        assertFalse(payload.getExtra().isPresent());
        assertEquals(expected, payload);
    }

    @Test
    public void testAll() throws Exception {
        String json
                = "{"
                + "  \"title\": \"T\","
                + "  \"body\": \"B\","
                + "  \"content-type\": \"A/B\","
                + "  \"content-encoding\": \"utf8\","
                + "  \"expiry\" : \"2018-01-01T00:00:00\","
                + "  \"extra\": {"
                + "    \"foo\" : \"bar\""
                + "  }"
                + "}";

        RichPushMessage expected = RichPushMessage.newBuilder()
                .setTitle("T")
                .setBody("B")
                .setContentType("A/B")
                .setContentEncoding("utf8")
                .setExpiry(PushExpiry.newBuilder().setExpiryTimeStamp(new DateTime(2018, 1, 1, 0, 0, 0, DateTimeZone.UTC)).build())
                .addExtraEntry("foo", "bar")
                .build();

        RichPushMessage payload = mapper.readValue(json, RichPushMessage.class);
        assertTrue(payload.getExtra().isPresent());
        assertEquals(expected, payload);
    }
}
