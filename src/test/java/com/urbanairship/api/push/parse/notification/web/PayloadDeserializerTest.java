package com.urbanairship.api.push.parse.notification.web;

import com.urbanairship.api.push.model.notification.web.WebDevicePayload;
import com.urbanairship.api.push.model.notification.web.WebIcon;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PayloadDeserializerTest {
    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testAlert() throws Exception {
        String json
                = "{"
                + "\"alert\":\"web override\""
                + "}";

        WebDevicePayload expected = WebDevicePayload.newBuilder()
                .setAlert("web override")
                .build();

        WebDevicePayload payload = MAPPER.readValue(json, WebDevicePayload.class);

        assertNotNull(payload);
        assertNotNull(payload.getAlert());
        assertFalse(payload.getExtra().isPresent());
        assertTrue(payload.getAlert().isPresent());
        assertTrue(payload.getAlert().isPresent());
        assertEquals("web override", payload.getAlert().get());
        assertEquals(expected, payload);
    }

    @Test
    public void testExtra() throws Exception {
        String json
                = "{"
                + "  \"extra\": {"
                + "    \"k1\" : \"v1\","
                + "    \"k2\" : \"v2\""
                + "  }"
                + "}";

        WebDevicePayload expected = WebDevicePayload.newBuilder()
                .addExtraEntry("k1", "v1")
                .addExtraEntry("k2", "v2")
                .build();

        WebDevicePayload payload = MAPPER.readValue(json, WebDevicePayload.class);
        assertNotNull(payload);
        assertNotNull(payload.getExtra());
        assertTrue(payload.getExtra().isPresent());
        assertFalse(payload.getAlert().isPresent());
        Map<String, String> extra = payload.getExtra().get();
        assertTrue(extra.containsKey("k1"));
        assertTrue(extra.containsKey("k2"));
        assertEquals("v1", extra.get("k1"));
        assertEquals("v2", extra.get("k2"));
        assertEquals(expected, payload);
    }

    @Test
    public void testTitle() throws Exception {
        String json
                = "{"
                + "  \"title\": \"title\""
                + "}";

        WebDevicePayload expected = WebDevicePayload.newBuilder()
                .setTitle("title")
                .build();

        WebDevicePayload payload = MAPPER.readValue(json, WebDevicePayload.class);

        assertTrue(payload.getTitle().get().equals("title"));
        assertEquals(expected, payload);
    }

    @Test
    public void testWebIcon() throws Exception {
        String json =
                "{" +
                    "\"icon\":{" +
                        "\"url\":\"https://i.ytimg.com/vi/PNgykntrIzE/maxresdefault.jpg\"" +
                    "}" +
                "}";

        WebIcon webIcon = WebIcon.newBuilder()
                .setUrl("https://i.ytimg.com/vi/PNgykntrIzE/maxresdefault.jpg")
                .build();

        WebDevicePayload payload = MAPPER.readValue(json, WebDevicePayload.class);
        assertNotNull(payload);
        assertEquals(webIcon, payload.getWebIcon().get());
    }
}
