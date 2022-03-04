package com.urbanairship.api.experiments.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.experiments.model.VariantPushPayload;
import com.urbanairship.api.push.model.InApp;
import com.urbanairship.api.push.model.notification.Notification;

import org.junit.Assert;
import org.junit.Test;

public class VariantPushPayloadSerializerTest {

    private static final ObjectMapper MAPPER = ExperimentObjectMapper.getInstance();

    @Test
    public void testPartialPushPayloadSerializer() throws Exception {

        VariantPushPayload partialPushPayload = VariantPushPayload.newBuilder()
                .setNotification(Notification.newBuilder()
                        .setAlert("hello everyone")
                        .build())
                .setInApp(InApp.newBuilder()
                        .setAlert("This is in-app!")
                        .build())
                .build();

        String partialPushPayloadSerialized = MAPPER.writeValueAsString(partialPushPayload);
        VariantPushPayload partialPushPayloadFromJson = MAPPER.readValue(partialPushPayloadSerialized, VariantPushPayload.class);

        Assert.assertEquals(partialPushPayloadFromJson, partialPushPayload);
    }
}
