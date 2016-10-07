package com.urbanairship.api.templates.parse;

import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.templates.model.PartialPushPayload;
import com.urbanairship.api.templates.model.TemplateVariable;
import com.urbanairship.api.templates.model.TemplateView;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class TemplateViewSerializerTest {

    private static final ObjectMapper MAPPER = TemplatesObjectMapper.getInstance();

    @Test
    public void testTemplateViewSerializer() throws Exception {

        TemplateVariable titleVariable = TemplateVariable.newBuilder()
                .setKey("TITLE")
                .setName("Title")
                .setDescription("e.g. Mr, Ms, Dr, etc")
                .setDefaultValue("")
                .build();

        TemplateVariable firstNameVariable = TemplateVariable.newBuilder()
                .setKey("FIRST_NAME")
                .setName("First Name")
                .setDescription("Given name")
                .setDefaultValue("Firsty")
                .build();

        TemplateVariable lastNameVariable = TemplateVariable.newBuilder()
                .setKey("LAST_NAME")
                .setName("Last Name")
                .setDescription("Family name")
                .setDefaultValue("Lasty")
                .build();


        TemplateView templateView = TemplateView.newBuilder()
                .setName("hello")
                .setDescription("Description")
                .addVariable(titleVariable)
                .addVariable(firstNameVariable)
                .addVariable(lastNameVariable)
                .setPushPayload(PartialPushPayload.newBuilder()
                        .setNotification(Notification.newBuilder()
                                .setAlert("a notification")
                                .build())
                        .build())
                .build();

        String templateViewSerialized = MAPPER.writeValueAsString(templateView);
        String templateViewString =
                "{" +
                    "\"name\":\"hello\"," +
                    "\"description\":\"Description\"," +
                    "\"push\":{\"notification\":{\"alert\":\"a notification\"}}," +
                    "\"variables\":[" +
                        "{" +
                            "\"key\":\"TITLE\"," +
                            "\"description\":\"e.g. Mr, Ms, Dr, etc\"," +
                            "\"name\":\"Title\"," +
                            "\"default_value\":\"\"" +
                        "}," +
                        "{" +
                            "\"key\":\"FIRST_NAME\"," +
                            "\"description\":\"Given name\"," +
                            "\"name\":\"First Name\"," +
                            "\"default_value\":\"Firsty\"" +
                        "}," +
                        "{" +
                            "\"key\":\"LAST_NAME\"," +
                            "\"description\":\"Family name\"," +
                            "\"name\":\"Last Name\"," +
                            "\"default_value\":\"Lasty\"" +
                        "}" +
                    "]" +
                "}";

        assertEquals(templateViewString, templateViewSerialized);
    }
}
