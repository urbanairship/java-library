package com.urbanairship.api.templates.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.templates.parse.TemplatesObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TemplateResponseTest {

    @Test
    public void testTemplateResponse() {
        TemplateResponse response = TemplateResponse.newBuilder()
                .setOk(true)
                .setOperationId("op123")
                .addPushId("id1")
                .addPushId("id2")
                .addPushId("id3")
                .build();

        assertNotNull(response);
        assertTrue(response.getOk());
        assertEquals(response.getOperationId().get(), "op123");
        assertEquals(response.getPushIds().get().get(0), "id1");
        assertEquals(response.getPushIds().get().get(1), "id2");
        assertEquals(response.getPushIds().get().get(2), "id3");
        assertFalse(response.getTemplateId().isPresent());
    }

    @Test
    public void testErrorAPITemplateResponse() throws IOException {
        String jsonResponse = "{\n" +
                "    \"ok\": false,\n" +
                "    \"error\": \"error\",\n" +
                "    \"details\": {\n" +
                "        \"error\": \"error\"\n" +
                "    }\n" +
                "}";

        ObjectMapper mapper = TemplatesObjectMapper.getInstance();
        TemplateResponse response = mapper.readValue(jsonResponse, TemplateResponse.class);
        assertEquals("error", response.getError().get());
        assertEquals("error", response.getErrorDetails().get().getError().get());
        assertFalse(response.getOk());
    }

}