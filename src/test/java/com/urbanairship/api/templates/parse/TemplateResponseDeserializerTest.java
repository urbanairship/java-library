package com.urbanairship.api.templates.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.templates.model.TemplateResponse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class TemplateResponseDeserializerTest {

    private static final ObjectMapper MAPPER = TemplatesObjectMapper.getInstance();

    @Test
    public void testTemplateResponseDeserializer() throws Exception {
        String templateCreateResponseString =
                "{" +
                    "\"ok\": true," +
                    "\"operation_id\": \"op-id-123\"," +
                    "\"template_id\": \"template-id-123\"" +
                "}";

        TemplateResponse templateCreateResponse = MAPPER.readValue(templateCreateResponseString, TemplateResponse.class);
        assertNotNull(templateCreateResponse);
        assertEquals(templateCreateResponse.getOk(), true);
        assertEquals(templateCreateResponse.getTemplateId().get(), "template-id-123");
        assertEquals(templateCreateResponse.getOperationId().get(), "op-id-123");
        assertFalse(templateCreateResponse.getPushIds().isPresent());
    }
}
