package com.urbanairship.api.push.model.notification.ios;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IOSAlertDataTest {

    @Test
    public void testIsCompound() {
        IOSAlertData iosAlertData = IOSAlertData.newBuilder()
                .setActionLocKey("test")
                .setBody("test")
                .setTitle("test")
                .setLaunchImage("image")
                .build();
        assertTrue(iosAlertData.isCompound());

        IOSAlertData iosAlertDataOnlyBody = IOSAlertData.newBuilder()
                .setBody("test")
                .build();
        assertFalse(iosAlertDataOnlyBody.isCompound());
    }
}
