package com.urbanairship.api.push.model.notification.richpush;

import com.urbanairship.api.push.model.PushExpiry;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RichPushMessageTest {
    @Test
    public void testEquality1() {
        assertEquals(RichPushMessage.newBuilder()
                        .setTitle("T")
                        .setBody("B")
                        .build(),
                RichPushMessage.newBuilder()
                        .setBody("B")
                        .setTitle("T")
                        .build());
    }

    @Test
    public void testHash1() {
        assertEquals(RichPushMessage.newBuilder()
                        .setTitle("T")
                        .setBody("B")
                        .build()
                        .hashCode(),
                RichPushMessage.newBuilder()
                        .setBody("B")
                        .setTitle("T")
                        .build()
                        .hashCode());
    }

    @Test
    public void testDefaults() {
        RichPushMessage m = RichPushMessage.newBuilder()
                .setTitle("T").setBody("B").build();
        assertEquals("text/html", m.getContentType());
        assertEquals("utf8", m.getContentEncoding());
    }

    @Test
    public void testBuilder() {
        RichPushMessage m = RichPushMessage.newBuilder()
                .setTitle("T")
                .setBody("B")
                .setContentType("application/json")
                .setContentEncoding("base64")
                .setExpiry(PushExpiry.newBuilder().setExpirySeconds(3600).build())
                .addExtraEntry("this", "that")
                .build();
        assertEquals("T", m.getTitle());
        assertEquals("B", m.getBody());
        assertEquals("application/json", m.getContentType());
        assertEquals("base64", m.getContentEncoding());
        assertTrue(m.getExtra().isPresent());
        assertEquals(1, m.getExtra().get().size());
        assertTrue(m.getExtra().get().containsKey("this"));
        assertEquals("that", m.getExtra().get().get("this"));
        assertTrue(m.getExpiry().get().getExpirySeconds().isPresent());

    }

    @Test(expected = Exception.class)
    public void testValidation1() {
        RichPushMessage.newBuilder()
                .build();
    }

    @Test(expected = Exception.class)
    public void testValidation2() {
        RichPushMessage.newBuilder()
                .setTitle("T")
                .build();
    }

    @Test(expected = Exception.class)
    public void testValidation3() {
        RichPushMessage.newBuilder()
                .setBody("B")
                .build();
    }
}
