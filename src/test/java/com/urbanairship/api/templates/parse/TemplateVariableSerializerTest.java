package com.urbanairship.api.templates.parse;

import com.urbanairship.api.templates.model.TemplateVariable;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class TemplateVariableSerializerTest {

    private static final ObjectMapper MAPPER = TemplatesObjectMapper.getInstance();

    @Test
    public void testTemplateVariableSerialization() throws Exception {
        TemplateVariable templateVariable = TemplateVariable.newBuilder()
                .setKey("TITLE")
                .setDefaultValue("Dr.")
                .setName("Title")
                .setDescription("A person's title.")
                .build();

        String templateVariableJson = MAPPER.writeValueAsString(templateVariable);
        String rawJson =
                "{" +
                        "\"key\":\"TITLE\"," +
                        "\"description\":\"A person's title.\"," +
                        "\"name\":\"Title\"," +
                        "\"default_value\":\"Dr.\"" +
                "}";

        assertEquals(templateVariableJson, rawJson);
    }
}
