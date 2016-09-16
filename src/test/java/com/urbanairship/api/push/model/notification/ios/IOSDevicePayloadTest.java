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
    }

}
