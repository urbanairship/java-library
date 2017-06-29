package com.urbanairship.api.experiments.parse;

import com.urbanairship.api.experiments.model.Variant;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class VariantDeserializerTest {

    private static final ObjectMapper MAPPER = ExperimentObjectMapper.getInstance();

    @Test
    public void testVariant() throws Exception {
        String variantString =
                "{" +
                        "\"push\":{\"notification\":{\"alert\":\"Hello Jenn\"}}" +
                        "}";

        Variant variant = MAPPER.readValue(variantString, Variant.class);
        assertNotNull(variant);
        assertTrue(variant.getVariantPushPayload().getNotification().isPresent());
    }

    @Test(expected = Exception.class)
    public void testEmptyVariant() {
        Variant variant = Variant.newBuilder().build();
    }

}
