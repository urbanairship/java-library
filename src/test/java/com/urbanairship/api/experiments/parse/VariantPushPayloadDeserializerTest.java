package com.urbanairship.api.experiments.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.experiments.model.VariantPushPayload;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class VariantPushPayloadDeserializerTest {

    private static final ObjectMapper MAPPER = ExperimentObjectMapper.getInstance();

    @Test
    public void testPartialPushPayload() throws Exception {
        String partialPushPayloadString =
                "{" +
                        "\"notification\": {\"alert\": \"hello everyone\"}," +
                        "\"in_app\": {" +
                        "\"alert\": \"This is in-app!\"" +
                        "}" +
                        "}";

        VariantPushPayload payload = MAPPER.readValue(partialPushPayloadString, VariantPushPayload.class);
        assertNotNull(payload);
        assertTrue(payload.getNotification().isPresent());
        assertTrue(payload.getInApp().isPresent());
        assertFalse(payload.getPushOptions().isPresent());
    }

    @Test
    public void testEmptyPartialPushPayload() throws Exception {
        Exception exception = Assert.assertThrows(APIParsingException.class, () -> {
            String emptyPayloadString = "{}";
            MAPPER.readValue(emptyPayloadString, VariantPushPayload.class);
        });
        
        String expectedMessage = "At least one of 'notification' or 'inApp' must be set.";
        String actualMessage = exception.getMessage();

        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }

}
