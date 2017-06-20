package com.urbanairship.api.experiments.model;

import com.urbanairship.api.push.model.notification.Notification;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class VariantTest {

    @Test
    public void testVariant() {

        PartialPushPayload payload = PartialPushPayload.newBuilder()
                .setNotification(Notification.newBuilder()
                        .setAlert("Hello Jenn")
                        .build()
                )
                .build();

        Variant variant = Variant.newBuilder()
                .setPushPayload(payload)
                .build();

        assertNotNull(variant);
        assertEquals(variant.getPartialPushPayload(), payload);

    }

    @Test(expected = Exception.class)
    public void testInvalidVariant() {
        Variant variant = Variant.newBuilder()
                .setName("name")
                .build();
    }
}
