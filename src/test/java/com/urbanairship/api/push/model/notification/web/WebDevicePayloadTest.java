package com.urbanairship.api.push.model.notification.web;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WebDevicePayloadTest {

    @Test
    public void testBuilder() {
        WebIcon webIcon = WebIcon.newBuilder()
                .setUrl("https://media.giphy.com/media/JYsWwF82EGnpC/giphy.gif")
                .build();

        WebDevicePayload m = WebDevicePayload.newBuilder()
                .setAlert("alert")
                .setTitle("title")
                .addExtraEntry("key", "value")
                .setWebIcon(webIcon)
                .build();

        assertTrue(m.getExtra().isPresent());
        assertEquals(1, m.getExtra().get().size());
        assertTrue(m.getExtra().get().containsKey("key"));
        assertEquals("value", m.getExtra().get().get("key"));
        assertTrue(m.getAlert().isPresent());
        assertEquals("title", m.getTitle().get());
    }
}


