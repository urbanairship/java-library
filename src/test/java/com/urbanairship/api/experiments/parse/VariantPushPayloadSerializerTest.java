package com.urbanairship.api.experiments.parse;

import com.urbanairship.api.experiments.model.VariantPushPayload;
import com.urbanairship.api.push.model.InApp;
import com.urbanairship.api.push.model.notification.Notification;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

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

        String partialPushPayloadString =
                "{" +
                        "\"notification\":{\"alert\":\"hello everyone\"}," +
                        "\"in_app\":{\"alert\":\"This is in-app!\",\"display_type\":\"banner\"}" +
                        "}";

        JsonNode fromObject = MAPPER.readTree(partialPushPayloadSerialized);
        JsonNode fromString = MAPPER.readTree(partialPushPayloadString);

        assertEquals(fromObject, fromString);
        assertEquals(partialPushPayloadFromJson, partialPushPayload);
    }
}
