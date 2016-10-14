package com.urbanairship.api.push.model.notification.ios;

import com.urbanairship.api.push.model.PushExpiry;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IOSDevicePayloadTest {
    @Test
    public void testExpiryEquality() {
        IOSDevicePayload p1 = IOSDevicePayload.newBuilder()
                .setExpiry(PushExpiry.newBuilder().setExpirySeconds(3600).build())
                .build();
        IOSDevicePayload p2 = IOSDevicePayload.newBuilder()
                .setExpiry(PushExpiry.newBuilder().setExpirySeconds(3600).build())
                .build();
        IOSDevicePayload p3 = IOSDevicePayload.newBuilder()
                .setExpiry(PushExpiry.newBuilder().setExpirySeconds(3600).build())
                .build();

        assertEquals(p1, p2);
        assertEquals(p2, p1);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertEquals(p1, p3);
        assertEquals(p3, p1);
        assertEquals(p1.hashCode(), p3.hashCode());
    }

    @Test
    public void testBuilder() {
        Crop crop = Crop.newBuilder()
                .setHeight(0.2f)
                .setWidth(0.2f)
                .setX(0.1f)
                .setY(0.1f)
                .build();

        Options options = Options.newBuilder()
                .setTime(10)
                .setCrop(crop)
                .build();

        Content content = Content.newBuilder()
                .setBody("content body")
                .setTitle("content title")
                .setSubtitle("content subtitle")
                .build();

        MediaAttachment mediaAttachment = MediaAttachment.newBuilder()
                .setUrl("https://media.giphy.com/media/JYsWwF82EGnpC/giphy.gif")
                .setOptions(options)
                .setContent(content)
                .build();

        IOSDevicePayload m = IOSDevicePayload.newBuilder()
                .setSound("this is a sound")
                .setContentAvailable(true)
                .setAlert(IOSAlertData.newBuilder().build())
                .setBadge(IOSBadgeData.newBuilder().setType(IOSBadgeData.Type.VALUE).setValue(1).build())
                .setExpiry(PushExpiry.newBuilder().setExpirySeconds(600).build())
                .setPriority(10)
                .addExtraEntry("this", "that")
                .setTitle("title")
                .setSubtitle("subtitle")
                .setMediaAttachment(mediaAttachment)
                .setMutableContent(true)
                .build();

        assertTrue(m.getExtra().isPresent());
        assertEquals(1, m.getExtra().get().size());
        assertTrue(m.getExtra().get().containsKey("this"));
        assertEquals("that", m.getExtra().get().get("this"));
        assertTrue(m.getExpiry().isPresent());
        Integer t = 600;
        assertEquals(t, m.getExpiry().get().getExpirySeconds().get());
        assertTrue(m.getAlertData().isPresent());
        assertTrue(m.getBadge().isPresent());
        assertEquals("this is a sound", m.getSound().get());
        assertTrue(m.getContentAvailable().get());
        assertEquals("title", m.getTitle().get());
        assertEquals("subtitle", m.getSubtitle().get());
        assertEquals("https://media.giphy.com/media/JYsWwF82EGnpC/giphy.gif", m.getMediaAttachment().get().getUrl());
        assertEquals("content body", m.getMediaAttachment().get().getContent().get().getBody().get());
        assertEquals("content title", m.getMediaAttachment().get().getContent().get().getTitle().get());
        assertEquals("content subtitle", m.getMediaAttachment().get().getContent().get().getSubtitle().get());
        Integer time = 10;
        assertEquals(time, m.getMediaAttachment().get().getOptions().get().getTime().get());
        Float height = 0.2f;
        Float width = 0.2f;
        Float x = 0.1f;
        Float y = 0.1f;
        assertEquals(height, m.getMediaAttachment().get().getOptions().get().getCrop().get().getHeight().get());
        assertEquals(width, m.getMediaAttachment().get().getOptions().get().getCrop().get().getWidth().get());
        assertEquals(x, m.getMediaAttachment().get().getOptions().get().getCrop().get().getX().get());
        assertEquals(y, m.getMediaAttachment().get().getOptions().get().getCrop().get().getY().get());
    }

}
