package com.urbanairship.api.templates.model;

import com.urbanairship.api.push.model.InApp;
import com.urbanairship.api.push.model.notification.Notification;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class VariantPushPayloadTest {
    @Test
    public void testPartialPushPayload() {
        Notification notification = Notification.newBuilder()
                .setAlert("hi {{NAME}}")
                .build();

        InApp inApp = InApp.newBuilder()
                .setAlert("hi {{NAME}}")
                .build();

        PartialPushPayload payload = PartialPushPayload.newBuilder()
                .setNotification(notification)
                .setInApp(inApp)
                .build();

        assertNotNull(payload);
        assertEquals(payload.getInApp().get(), inApp);
        assertEquals(payload.getNotification().get(), notification);
        assertFalse(payload.getRichPushMessage().isPresent());
        assertFalse(payload.getRichPushMessage().isPresent());
    }
}
