package com.urbanairship.api.experiments.parse;

import com.urbanairship.api.experiments.model.Experiment;
import com.urbanairship.api.experiments.model.PartialPushPayload;
import com.urbanairship.api.experiments.model.Variant;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notification;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class ExperimentSerializerTest {

    private static final ObjectMapper MAPPER = ExperimentObjectMapper.getInstance();

    @Test
    public void testExperimentSerializer() throws Exception {

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

        String experimentSerialized = MAPPER.writeValueAsString(experiment);
        String experimentString =
                "{" +
                        "\"audience\":{\"named_user\":\"birdperson\"}," +
                        "\"device_types\":[\"ios\"]," +
                        "\"variants\":[" +
                        "{\"push\":{\"notification\":{\"alert\":\"Hello Jenn\"}}}," +
                        "{\"push\":{\"notification\":{\"alert\":\"Boogaloo\"}}}]," +
                        "\"name\":\"Another test\"," +
                        "\"description\":\"Its a test hoo boy\"" +
                "}";

        assertEquals(experimentSerialized, experimentString);
    }
}
