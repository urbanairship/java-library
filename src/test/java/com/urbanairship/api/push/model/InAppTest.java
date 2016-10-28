package com.urbanairship.api.push.model;


import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class InAppTest {

    @Test
    public void testInAppMessage() {

        DateTime now = DateTime.now();

        InApp inApp = InApp.newBuilder()
                .setAlert("test alert")
                .setExpiry(now)
                .build();

        assertNotNull(inApp);
        assertEquals("test alert", inApp.getAlert());
        assertEquals(now, inApp.getExpiry().get());
    }
}
