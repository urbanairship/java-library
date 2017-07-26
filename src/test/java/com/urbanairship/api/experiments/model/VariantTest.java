package com.urbanairship.api.experiments.model;

import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.schedule.model.Schedule;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import java.math.BigDecimal;

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
                .setWeight(new BigDecimal(0.1))
                .build();

        assertNotNull(variant);
        assertEquals(variant.getName().get(), "name");
        assertEquals(variant.getDescription().get(), "description");
        assertEquals(variant.getWeight().get(), new BigDecimal(0.1));
        assertEquals(variant.getVariantPushPayload(), payload);

    }

    @Test(expected = Exception.class)
    public void testInvalidVariant() {
        Variant variant = Variant.newBuilder()
                .setName("name")
                .build();
    }
}
