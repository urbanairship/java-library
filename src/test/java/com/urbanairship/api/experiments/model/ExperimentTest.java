package com.urbanairship.api.experiments.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.push.model.Campaigns;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.model.notification.email.MessageType;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class ExperimentTest {
    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testExperiment() throws Exception{

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
                .setControl(new BigDecimal("0.1"))
                .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS))
                .setAudience(Selectors.namedUser("birdperson"))
                .addVariant(variantOne)
                .addVariant(variantTwo)
                .setMessageType(MessageType.COMMERCIAL)
                .setCampaigns(Campaigns.newBuilder().addCategory("test").build())
                .build();

        List<Variant> variants = new ArrayList<>();
        variants.add(variantOne);
        variants.add(variantTwo);

        assertNotNull(experiment);
        assertEquals(experiment.getName().get(), "name");
        assertEquals(experiment.getDescription().get(), "description");
        assertEquals(experiment.getControl().get(), new BigDecimal("0.1"));
        assertEquals(experiment.getAudience(), Selectors.namedUser("birdperson"));
        assertEquals(experiment.getDeviceTypes(), DeviceTypeData.of(DeviceType.IOS));
        assertEquals(experiment.getVariants(), variants);

        String pushPayloadStr = mapper.writeValueAsString(experiment);
        JsonNode actualJson = mapper.readTree(pushPayloadStr);
        String json = "{\"name\":\"name\",\"description\":\"description\",\"control\":0.1,\"audience\":{\"named_user\":\"birdperson\"},\"deviceTypes\":[\"ios\"],\"variants\":[{\"variantPushPayload\":{\"notification\":{\"alert\":\"Hello\"}}},{\"variantPushPayload\":{\"notification\":{\"alert\":\"Goodbye\"}}}],\"messageType\":\"COMMERCIAL\",\"campaigns\":{\"categories\":[\"test\"]},\"broadcast\":false}";
        JsonNode expectedJson = mapper.readTree(json);
        assertEquals(expectedJson, actualJson);
    }

    @Test(expected = Exception.class)
    public void testInvalidExperiment() {
        Experiment.newBuilder()
                .setAudience(Selectors.tag("tag"))
                .build();
    }
}
