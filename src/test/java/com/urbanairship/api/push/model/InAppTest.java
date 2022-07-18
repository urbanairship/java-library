package com.urbanairship.api.push.model;


import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class InAppTest {

    @Test
    public void testInAppMessage() {

        DateTime timestamp = new DateTime(2017, 4, 15, 11, 30, DateTimeZone.UTC);
        PushExpiry expiry = PushExpiry.newBuilder().setExpiryTimeStamp(timestamp).build();

        InApp inApp = InApp.newBuilder()
                .setAlert("test alert")
                .setExpiry(expiry)
                .build();

        assertNotNull(inApp);
        assertEquals("test alert", inApp.getAlert());
        assertEquals(expiry, inApp.getExpiry().get());
    }
}
