package com.urbanairship.api.templates.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.templates.model.TemplateVariable;

import org.junit.Assert;
import org.junit.Test;

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

        Assert.assertEquals(templateVariableJson, rawJson);
    }
}
