package com.urbanairship.api.experiments.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.experiments.model.Variant;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class VariantDeserializerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static final ObjectMapper MAPPER = ExperimentObjectMapper.getInstance();

    @Test
    public void testVariant() throws Exception {
        String variantString =
                "{" +
                        "\"schedule\": {" +
                        "\"scheduled_time\": \"2017-07-27T18:27:25.000Z\"" +
                        "}," +
                        "\"push\":{\"notification\":{\"alert\":\"Hello there\"}}," +
                        "\"name\":\"A name\"," +
                        "\"description\":\"A description\"," +
                        "\"weight\":\"4\"" +
                        "}";

        Variant variant = MAPPER.readValue(variantString, Variant.class);
        assertNotNull(variant);
        assertTrue(variant.getSchedule().isPresent());
        assertTrue(variant.getName().isPresent());
        assertTrue(variant.getDescription().isPresent());
        assertTrue(variant.getWeight().isPresent());
        assertTrue(variant.getVariantPushPayload().getNotification().isPresent());
    }

    @Test
    public void testEmptyVariant() throws Exception {
        expectedException.expect(APIParsingException.class);
        expectedException.expectMessage("'variant_push_payload' must be provided.");

        String emptyPayloadString = "{}";
        Variant variant = MAPPER.readValue(emptyPayloadString, Variant.class);
    }

}
