package com.urbanairship.api.experiments.model;

import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notification;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ExperimentTest {

    @Test
    public void testExperiment() {

        VariantPushPayload payloadOne = VariantPushPayload.newBuilder()
                .setNotification(Notification.newBuilder()
                        .setAlert("Hello")
                        .build()
                )
                .build();

        Variant variantOne = Variant.newBuilder()
                .setPushPayload(payloadOne)
                .build();

        VariantPushPayload payloadTwo = VariantPushPayload.newBuilder()
                .setNotification(Notification.newBuilder()
                        .setAlert("Goodbye")
                        .build()
                )
                .build();

        Variant variantTwo = Variant.newBuilder()
                .setPushPayload(payloadTwo)
                .build();

        Experiment experiment = Experiment.newBuilder()
                .setName("name")
                .setDescription("description")
                .setControl(new BigDecimal(0.1))
                .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS))
                .setAudience(Selectors.namedUser("birdperson"))
                .addVariant(variantOne)
                .addVariant(variantTwo)
                .build();

        List variants = new ArrayList();
        variants.add(variantOne);
        variants.add(variantTwo);

        assertNotNull(experiment);
        assertEquals(experiment.getName().get(), "name");
        assertEquals(experiment.getDescription().get(), "description");
        assertEquals(experiment.getControl().get(), new BigDecimal(0.1));
        assertEquals(experiment.getAudience(), Selectors.namedUser("birdperson"));
        assertEquals(experiment.getDeviceTypes(), DeviceTypeData.of(DeviceType.IOS));
        assertEquals(experiment.getVariants(), variants);
    }

    @Test(expected = Exception.class)
    public void testInvalidExperiment() {
        Experiment experiment = Experiment.newBuilder()
                .setAudience(Selectors.tag("tag"))
                .build();
    }
}
