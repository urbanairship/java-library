package com.urbanairship.api.templates.parse;

import com.urbanairship.api.templates.model.TemplateSelector;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.util.Map;

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
        Map<String, Object> expectedMap = MAPPER.readValue(templateSelectorJson, Map.class);
        Map<String, Object> actualMap = MAPPER.readValue(templateSelectorSerialized, Map.class);
        assertEquals(expectedMap, actualMap);
    }
}
