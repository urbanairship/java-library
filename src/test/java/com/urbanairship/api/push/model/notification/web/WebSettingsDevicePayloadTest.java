package com.urbanairship.api.push.model.notification.web;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WebSettingsDevicePayloadTest {

    @Test
    public void testBuilder() {
        WebIcon webIcon = WebIcon.newBuilder()
                .setUrl("https://media.giphy.com/media/JYsWwF82EGnpC/giphy.gif")
                .build();

        WebDevicePayload m = WebDevicePayload.newBuilder()
                .setAlert("alert")
                .setTitle("title")
                .addExtraEntry("key", "value")
                .addExtraEntry("key2", "value2")
                .setWebIcon(webIcon)
                .build();

        assertTrue(m.getExtra().isPresent());
        assertEquals(2, m.getExtra().get().size());

        assertTrue(m.getExtra().get().containsKey("key"));
        assertEquals("value", m.getExtra().get().get("key"));
        assertTrue(m.getExtra().get().containsKey("key2"));
        assertEquals("value2", m.getExtra().get().get("key2"));

        assertTrue(m.getAlert().isPresent());
        assertEquals("alert", m.getAlert().get());

        assertTrue(m.getTitle().isPresent());
        assertEquals("title", m.getTitle().get());

        assertTrue(m.getWebIcon().isPresent());
        assertEquals(m.getWebIcon().get(), webIcon);
    }
}


