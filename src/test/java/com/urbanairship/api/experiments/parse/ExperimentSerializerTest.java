package com.urbanairship.api.experiments.parse;

import com.urbanairship.api.experiments.model.Experiment;
import com.urbanairship.api.experiments.model.VariantPushPayload;
import com.urbanairship.api.experiments.model.Variant;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notification;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class ExperimentSerializerTest {

    private static final ObjectMapper MAPPER = ExperimentObjectMapper.getInstance();

    @Test
    public void testExperimentSerializer() throws Exception {

        Variant variantOne = Variant.newBuilder()
                .setPushPayload(VariantPushPayload.newBuilder()
                        .setNotification(Notification.newBuilder()
                                .setAlert("Hello there")
                                .build()
                        )
                        .build())
                .build();

        Variant variantTwo = Variant.newBuilder()
                .setPushPayload(VariantPushPayload.newBuilder()
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

        String experimentSerialized = MAPPER.writeValueAsString(experiment);
        Experiment experimentFromJson = MAPPER.readValue(experimentSerialized, Experiment.class);

        assertEquals(experimentFromJson, experiment);
    }
}
