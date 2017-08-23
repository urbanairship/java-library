package com.urbanairship.api.experiments.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.experiments.model.VariantPushPayload;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class VariantPushPayloadDeserializerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

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
        expectedException.expect(APIParsingException.class);
        expectedException.expectMessage("At least one of 'notification' or 'inApp' must be set.");

        String emptyPayloadString = "{}";
        VariantPushPayload payload = MAPPER.readValue(emptyPayloadString, VariantPushPayload.class);
    }

}
