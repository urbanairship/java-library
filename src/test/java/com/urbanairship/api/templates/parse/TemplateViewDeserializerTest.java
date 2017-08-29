package com.urbanairship.api.templates.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.templates.model.TemplateView;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TemplateViewDeserializerTest {

    private static final ObjectMapper MAPPER = TemplatesObjectMapper.getInstance();

    @Test
    public void testTemplateViewDeserializer() throws Exception {

        String templateViewString =
                "{" +
                    "\"id\" : \"ef34a8d9-0ad7-491c-86b0-aea74da15161\"," +
                    "\"created_at\" : \"2015-08-17T11:10:02Z\"," +
                    "\"modified_at\" : \"2015-08-17T11:10:02Z\"," +
                    "\"last_used\" : \"2015-08-17T11:10:01Z\"," +
                    "\"name\" : \"Welcome Message\"," +
                    "\"description\": \"Our welcome message\"," +
                    "\"variables\": [" +
                        "{" +
                            "\"key\": \"TITLE\"," +
                            "\"name\": \"Title\"," +
                            "\"description\": \"e.g. Mr, Ms, Dr, etc.\"," +
                            "\"default_value\": \"\"" +
                        "}," +
                        "{" +
                            "\"key\": \"FIRST_NAME\"," +
                            "\"name\": \"First Name\"," +
                            "\"description\": \"Given name\"," +
                            "\"default_value\": null" +
                        "}," +
                        "{" +
                            "\"key\": \"LAST_NAME\"," +
                            "\"name\": \"Last Name\"," +
                            "\"description\": \"Family name\"," +
                            "\"default_value\": null" +
                        "}" +
                    "]," +
                    "\"push\": {" +
                        "\"notification\": {" +
                            "\"alert\": \"Hello {{FIRST_NAME}}, this is your welcome message!\"" +
                        "}" +
                    "}" +
                "}";

        TemplateView templateView = MAPPER.readValue(templateViewString, TemplateView.class);
        assertNotNull(templateView);
        assertEquals(templateView.getId(), "ef34a8d9-0ad7-491c-86b0-aea74da15161");
        assertEquals(templateView.getCreatedAt(), DateTime.parse("2015-08-17T11:10:02Z"));
        assertEquals(templateView.getModifiedAt(), DateTime.parse("2015-08-17T11:10:02Z"));
        assertEquals(templateView.getLastUsed(), DateTime.parse("2015-08-17T11:10:01Z"));
        assertEquals(templateView.getName(), "Welcome Message");
        assertEquals(templateView.getDescription().get(), "Our welcome message");
        assertNotNull(templateView.getVariables());
        assertEquals(templateView.getVariables().size(), 3);
        assertNotNull(templateView.getPartialPushPayload().get());
    }
}
