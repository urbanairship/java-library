package com.urbanairship.api.push.parse.notification.android;

import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.model.notification.actions.Actions;
import com.urbanairship.api.push.model.notification.actions.ShareAction;
import com.urbanairship.api.push.model.notification.android.AndroidDevicePayload;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PayloadDeserializerTest {
    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testAlert() throws Exception {
        String json
                = "{"
                + "  \"alert\": \"android override\""
                + "}";

        AndroidDevicePayload expected = AndroidDevicePayload.newBuilder()
                .setAlert("android override")
                .build();

        AndroidDevicePayload payload = mapper.readValue(json, AndroidDevicePayload.class);
        assertNotNull(payload);
        assertTrue(payload.getAlert().isPresent());
        assertFalse(payload.getCollapseKey().isPresent());
        assertFalse(payload.getExtra().isPresent());
        assertEquals("android override", payload.getAlert().get());
        assertEquals(expected, payload);
    }

    @Test
    public void testCollapseKey() throws Exception {
        String json
                = "{"
                + "  \"collapse_key\": \"1234\""
                + "}";

        AndroidDevicePayload expected = AndroidDevicePayload.newBuilder()
                .setCollapseKey("1234")
                .build();

        AndroidDevicePayload payload = mapper.readValue(json, AndroidDevicePayload.class);
        assertNotNull(payload);
        assertNotNull(payload.getCollapseKey());
        assertFalse(payload.getAlert().isPresent());
        assertFalse(payload.getExtra().isPresent());
        assertTrue(payload.getCollapseKey().isPresent());
        assertEquals("1234", payload.getCollapseKey().get());
        assertEquals(expected, payload);
    }

    @Test
    public void testTimeToLive() throws Exception {
        String json
                = "{"
                + "  \"time_to_live\": 1234"
                + "}";

        AndroidDevicePayload expected = AndroidDevicePayload.newBuilder()
                .setTimeToLive(PushExpiry.newBuilder()
                        .setExpirySeconds(1234)
                        .build())
                .build();

        AndroidDevicePayload payload = mapper.readValue(json, AndroidDevicePayload.class);
        assertEquals(expected, payload);
        assertTrue(payload.getTimeToLive().isPresent());
        assertEquals(1234, payload.getTimeToLive().get().getExpirySeconds().get().intValue());
    }

    @Test(expected = APIParsingException.class)
    public void testTimeToLiveBadFormat() throws Exception {
        String json
                = "{"
                + "  \"time_to_live\": true"
                + "}";
        mapper.readValue(json, AndroidDevicePayload.class);
    }

    @Test
    public void testDelayWhileIdle() throws Exception {
        String json
                = "{"
                + "  \"delay_while_idle\": true"
                + "}";

        AndroidDevicePayload expected = AndroidDevicePayload.newBuilder()
                .setDelayWhileIdle(true)
                .build();

        AndroidDevicePayload payload = mapper.readValue(json, AndroidDevicePayload.class);
        assertEquals(expected, payload);
        assertTrue(payload.getDelayWhileIdle().isPresent());
        assertEquals(true, payload.getDelayWhileIdle().get());
    }

    @Test(expected = APIParsingException.class)
    public void testDelayWhileIdleBadFormat() throws Exception {
        String json
                = "{"
                + "  \"delay_while_idle\": 1010"
                + "}";
        mapper.readValue(json, AndroidDevicePayload.class);
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

        AndroidDevicePayload expected = AndroidDevicePayload.newBuilder()
                .addExtraEntry("k1", "v1")
                .addExtraEntry("k2", "v2")
                .build();

        AndroidDevicePayload payload = mapper.readValue(json, AndroidDevicePayload.class);
        assertNotNull(payload);
        assertNotNull(payload.getExtra());
        assertTrue(payload.getExtra().isPresent());
        assertFalse(payload.getAlert().isPresent());
        assertFalse(payload.getCollapseKey().isPresent());
        Map<String, String> extra = payload.getExtra().get();
        assertTrue(extra.containsKey("k1"));
        assertTrue(extra.containsKey("k2"));
        assertEquals("v1", extra.get("k1"));
        assertEquals("v2", extra.get("k2"));
        assertEquals(expected, payload);
    }

    @Test
    public void testValidate_Empty() throws Exception {
        AndroidDevicePayload payload = mapper.readValue("{}", AndroidDevicePayload.class);
        assertNotNull(payload);
        assertFalse(payload.getAlert().isPresent());
        assertFalse(payload.getExtra().isPresent());
        assertFalse(payload.getCollapseKey().isPresent());
    }

    @Test
    public void testInteractiveNotificationActions() throws Exception {
        String json
            = "{"
            + "  \"interactive\": {"
            + "    \"type\" : \"ua_yes_no_foreground\","
            + "    \"button_actions\" : {"
            + "      \"yes\" : {"
            + "        \"share\" : \"foo\""
            + "      }"
            + "    }"
            + "  }"
            + "}";
        AndroidDevicePayload payload = mapper.readValue(json, AndroidDevicePayload.class);

        Interactive interactive = Interactive.newBuilder()
            .setType("ua_yes_no_foreground")
            .setButtonActions(
                ImmutableMap.of(
                    "yes",
                    Actions.newBuilder()
                        .setShare(new ShareAction("foo"))
                        .build()))
            .build();
        AndroidDevicePayload expected = AndroidDevicePayload.newBuilder()
            .setInteractive(interactive)
            .build();

        assertEquals(expected, payload);
        Interactive returned = payload.getInteractive().get();
        assertEquals(interactive, returned);
    }
}
