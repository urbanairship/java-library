package com.urbanairship.api.templates.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.templates.model.TemplateSelector;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class TemplateSelectorSerializerTest {

    private static final ObjectMapper MAPPER = TemplatesObjectMapper.getInstance();

    @Test
    public void testTemplateSelectorSerializer() throws Exception {

        TemplateSelector templateSelector = TemplateSelector.newBuilder()
                .setTemplateId("id123")
                .addSubstitution("TITLE", "Dr.")
                .addSubstitution("FIRST", "Jen")
                .build();

        String templateSelectorJson =
                "{" +
                    "\"template_id\":\"id123\"," +
                    "\"substitutions\":{" +
                        "\"FIRST\":\"Jen\"," +
                        "\"TITLE\":\"Dr.\"" +
                    "}" +
                "}";


        String templateSelectorSerialized = MAPPER.writeValueAsString(templateSelector);

        JsonNode jsonFromObject = MAPPER.readTree(templateSelectorSerialized);
        JsonNode jsonFromString = MAPPER.readTree(templateSelectorJson);

        assertEquals(jsonFromObject, jsonFromString);
    }
}
