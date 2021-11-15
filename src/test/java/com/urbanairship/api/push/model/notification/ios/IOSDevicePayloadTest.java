package com.urbanairship.api.push.model.notification.ios;

import com.urbanairship.api.push.model.PushExpiry;
import org.junit.Test;

import java.math.BigDecimal;

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
        BigDecimal x = new BigDecimal("0.1");
        BigDecimal y = new BigDecimal("0.2");
        BigDecimal width = new BigDecimal("0.3");
        BigDecimal height = new BigDecimal("0.4");

        Crop crop = Crop.newBuilder()
                .setX(x)
                .setY(y)
                .setWidth(width)
                .setHeight(height)
                .build();

        IOSMediaOptions options = IOSMediaOptions.newBuilder()
                .setTime(10)
                .setCrop(crop)
                .setHidden(true)
                .build();

        IOSMediaContent content = IOSMediaContent.newBuilder()
                .setBody("content body")
                .setTitle("content title")
                .setSubtitle("content subtitle")
                .build();

        MediaAttachment mediaAttachment = MediaAttachment.newBuilder()
                .setUrl("https://media.giphy.com/media/JYsWwF82EGnpC/giphy.gif")
                .setOptions(options)
                .setContent(content)
                .build();

        IOSSoundData iosSoundData = IOSSoundData.newBuilder()
                .setCritical(true)
                .setVolume(0.5)
                .setName("really cool name")
                .build();

        IOSDevicePayload m = IOSDevicePayload.newBuilder()
                .setContentAvailable(true)
                .setAlert(IOSAlertData.newBuilder().build())
                .setBadge(IOSBadgeData.newBuilder().setType(IOSBadgeData.Type.VALUE).setValue(1).build())
                .setExpiry(PushExpiry.newBuilder().setExpirySeconds(600).build())
                .setPriority(10)
                .addExtraEntry("this", "that")
                .setTitle("title")
                .setSubtitle("subtitle")
                .setMediaAttachment(mediaAttachment)
                .setSoundData(iosSoundData)
                .setMutableContent(true)
                .setThreadId("unique ID")
                .setRelevanceScore(0.5)
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
        assertTrue(m.getContentAvailable().get());
        assertEquals("title", m.getTitle().get());
        assertEquals("subtitle", m.getSubtitle().get());
        assertEquals("https://media.giphy.com/media/JYsWwF82EGnpC/giphy.gif", m.getMediaAttachment().get().getUrl());
        assertEquals("content body", m.getMediaAttachment().get().getContent().get().getBody().get());
        assertEquals("content title", m.getMediaAttachment().get().getContent().get().getTitle().get());
        assertEquals("content subtitle", m.getMediaAttachment().get().getContent().get().getSubtitle().get());
        assertEquals(true, m.getSoundData().get().getCritical().get());
        assertEquals(0.5, m.getSoundData().get().getVolume().get(), 0.0f);
        assertEquals("really cool name", m.getSound().get());
        Integer time = 10;
        assertEquals(time, m.getMediaAttachment().get().getOptions().get().getTime().get());
        assertEquals(height, m.getMediaAttachment().get().getOptions().get().getCrop().get().getHeight().get());
        assertEquals(width, m.getMediaAttachment().get().getOptions().get().getCrop().get().getWidth().get());
        assertEquals(x, m.getMediaAttachment().get().getOptions().get().getCrop().get().getX().get());
        assertEquals(y, m.getMediaAttachment().get().getOptions().get().getCrop().get().getY().get());
        Boolean hidden = true;
        assertEquals(hidden, m.getMediaAttachment().get().getOptions().get().getHidden().get());
        assertEquals("unique ID", m.getThreadId().get());
        assertEquals(0.5, m.getRelevanceScore().get().doubleValue(), 0.0f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectTemplateConfiguration() {
        IOSFields iosFields = IOSFields.newBuilder()
                .setAlert("alert field")
                .build();

        IOSTemplate iosTemplate = IOSTemplate.newBuilder()
                .setTemplateId("templateId")
                .setFields(iosFields)
                .build();

    }

}
