package com.urbanairship.api.push.model;


import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class InAppTest {

    @Test
    public void testInAppMessage() {

        DateTime expiry = new DateTime(2017, 4, 15, 11, 30);

        InApp inApp = InApp.newBuilder()
                .setAlert("test alert")
                .setExpiry(expiry)
                .build();

        assertNotNull(inApp);
        assertEquals("test alert", inApp.getAlert());
        assertEquals(expiry, inApp.getExpiry().get());
        assertTrue(inApp.getExpiry().get() instanceof DateTime);
    }
}
