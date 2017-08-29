package com.urbanairship.api.experiments.model;

import com.urbanairship.api.push.model.InApp;
import com.urbanairship.api.push.model.notification.Notification;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class VariantPushPayloadTest {
    @Test
    public void testPartialPushPayload() {
        Notification notification = Notification.newBuilder()
                .setAlert("hello world")
                .build();

        InApp inApp = InApp.newBuilder()
                .setAlert("hello world")
                .build();

        VariantPushPayload payload = VariantPushPayload.newBuilder()
                .setNotification(notification)
                .setInApp(inApp)
                .build();

        assertNotNull(payload);
        assertEquals(payload.getInApp().get(), inApp);
        assertEquals(payload.getNotification().get(), notification);
    }
}
