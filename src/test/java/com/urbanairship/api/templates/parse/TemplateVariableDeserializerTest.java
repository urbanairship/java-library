package com.urbanairship.api.templates.parse;

import com.urbanairship.api.templates.model.TemplateVariable;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TemplateVariableDeserializerTest {

    private static final ObjectMapper MAPPER = TemplatesObjectMapper.getInstance();

    @Test
    public void testTemplateVariableDeserializer() throws Exception {
        String templateVariableString =
                "{" +
                        "\"key\":\"TITLE\"," +
                        "\"description\":\"A person's title.\"," +
                        "\"name\":\"Title\"," +
                        "\"default_value\":\"Dr.\"" +
                "}";

        TemplateVariable templateVariable = MAPPER.readValue(templateVariableString, TemplateVariable.class);
        assertNotNull(templateVariable);
        assertEquals(templateVariable.getKey(), "TITLE");
        assertEquals(templateVariable.getDescription().get(), "A person's title.");
        assertEquals(templateVariable.getName().get(), "Title");
        assertEquals(templateVariable.getDefaultValue(), "Dr.");
    }
}
