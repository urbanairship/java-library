package com.urbanairship.api.experiments.model;

import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notification;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ExperimentTest {

    @Test
    public void testExperiment() {

        Variant variantOne = Variant.newBuilder()
                .setPushPayload(PartialPushPayload.newBuilder()
                        .setNotification(Notification.newBuilder()
                                .setAlert("Hello Jenn")
                                .build()
                        )
                        .build())
                .build();

        Variant variantTwo = Variant.newBuilder()
                .setPushPayload(PartialPushPayload.newBuilder()
                        .setNotification(Notification.newBuilder()
                                .setAlert("Boogaloo")
                                .build()
                        )
                        .build())
                .build();

        Experiment experiment = Experiment.newBuilder()
                .setName("Another test")
                .setDescription("Its a test hoo boy")
                .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS))
                .setAudience(Selectors.namedUser("birdperson"))
                .addVariant(variantOne)
                .addVariant(variantTwo)
                .build();

        List variants = new ArrayList();
        variants.add(variantOne);
        variants.add(variantTwo);

        assertNotNull(experiment);
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
