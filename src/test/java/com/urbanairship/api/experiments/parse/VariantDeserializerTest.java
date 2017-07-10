package com.urbanairship.api.experiments.parse;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.experiments.model.Variant;
import org.codehaus.jackson.map.ObjectMapper;
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
                        "\"push\":{\"notification\":{\"alert\":\"Hello there\"}}" +
                        "}";

        Variant variant = MAPPER.readValue(variantString, Variant.class);
        assertNotNull(variant);
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
