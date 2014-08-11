package com.urbanairship.api.push.parse.notification.richpush;

import com.urbanairship.api.push.model.notification.richpush.RichPushMessage;
import com.urbanairship.api.push.parse.PushObjectMapper;
import com.urbanairship.api.common.parse.APIParsingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import java.util.Map;
import static org.junit.Assert.*;

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
            + "  \"extra\": {"
            + "    \"foo\" : \"bar\""
            + "  }"
            + "}";

        RichPushMessage expected = RichPushMessage.newBuilder()
            .setTitle("T")
            .setBody("B")
            .setContentType("A/B")
            .setContentEncoding("utf8")
            .addExtraEntry("foo", "bar")
            .build();

        RichPushMessage payload = mapper.readValue(json, RichPushMessage.class);
        assertTrue(payload.getExtra().isPresent());
        assertEquals(expected, payload);
    }
}
