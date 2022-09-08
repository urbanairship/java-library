package com.urbanairship.api.experiments.model;

import com.urbanairship.api.push.model.notification.Notification;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class VariantTest {

    @Test
    public void testVariant() {

        VariantPushPayload payload = VariantPushPayload.newBuilder()
                .setNotification(Notification.newBuilder()
                        .setAlert("Hello there!")
                        .build()
                )
                .build();

        Variant variant = Variant.newBuilder()
                .setName("name")
                .setDescription("description")
                .setPushPayload(payload)
                .setWeight(1)
                .build();

        assertNotNull(variant);
        assertEquals(variant.getName().get(), "name");
        assertEquals(variant.getDescription().get(), "description");
        assertEquals(variant.getWeight().get(), new Integer(1));
        assertEquals(variant.getVariantPushPayload(), payload);

    }

    @Test(expected = Exception.class)
    public void testInvalidVariant() {
        Variant.newBuilder()
                .setName("name")
                .build();
    }
}
