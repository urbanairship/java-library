package com.urbanairship.api.templates.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.templates.parse.TemplatesObjectMapper;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TemplateListingResponseTest {

    @Test
    public void testTemplateLookupResponse() {
        DateTime created = new DateTime(2014, 10, 1, 12, 0, 0, 0);
        DateTime updated = created.plus(Period.hours(48));
        DateTime lastUsed = created.plus(Period.hours(96));

        TemplateVariable variable = TemplateVariable.newBuilder()
                .setKey("varKey")
                .setName("name")
                .setDescription("v descriptive")
                .build();

        PartialPushPayload payload = PartialPushPayload.newBuilder()
                .setNotification(Notification.newBuilder()
                        .setAlert("hello!")
                        .build())
                .build();

        TemplateView obj = TemplateView.newBuilder()
                .setId("id-123-abc")
                .setCreatedAt(created)
                .setModifiedAt(updated)
                .setLastUsed(lastUsed)
                .setName("template-name")
                .setDescription("blah")
                .addVariable(variable)
                .setPushPayload(payload)
                .build();

        TemplateListingResponse response = TemplateListingResponse.newBuilder()
                .setTemplate(obj)
                .setOk(true)
                .build();

        assertNotNull(response);
        assertEquals(response.getOk(), true);
        assertEquals(response.getTemplate().get(), obj);
    }

    @Test
    public void testTemplateListingResponse() {
        DateTime created = new DateTime(2014, 10, 1, 12, 0, 0, 0);
        DateTime updated = created.plus(Period.hours(48));
        DateTime lastUsed = created.plus(Period.hours(96));

        TemplateVariable variable = TemplateVariable.newBuilder()
                .setKey("varKey")
                .setName("name")
                .setDescription("v descriptive")
                .build();

        PartialPushPayload payload = PartialPushPayload.newBuilder()
                .setNotification(Notification.newBuilder()
                        .setAlert("hello!")
                        .build())
                .build();

        TemplateView template1 = TemplateView.newBuilder()
                .setId("id-123-abc")
                .setCreatedAt(created)
                .setModifiedAt(updated)
                .setLastUsed(lastUsed)
                .setName("template-name")
                .setDescription("blah")
                .addVariable(variable)
                .setPushPayload(payload)
                .build();

        TemplateView template2 = TemplateView.newBuilder()
                .setId("id-123-abc")
                .setCreatedAt(created)
                .setModifiedAt(updated)
                .setLastUsed(lastUsed)
                .setName("template-name")
                .setDescription("blah")
                .addVariable(variable)
                .setPushPayload(payload)
                .build();

        List<TemplateView> templates = new ArrayList<>();
        templates.add(template1);
        templates.add(template2);

        TemplateListingResponse response = TemplateListingResponse.newBuilder()
                .setOk(true)
                .setTemplates(templates)
                .setCount(2)
                .setTotalCount(100)
                .setNextPage("nextpage.com")
                .setPrevPage("prevpage.com")
                .build();

        assertNotNull(response);
        assertEquals(response.getOk(), true);
        assertEquals(response.getTemplates().get().get(0), template1);
        assertEquals(response.getTemplates().get().get(1), template2);
        assertEquals(response.getCount().get(), Integer.valueOf(2));
        assertEquals(response.getTotalCount().get(), Integer.valueOf(100));
        assertEquals(response.getNextPage().get(), "nextpage.com");
        assertEquals(response.getPrevPage().get(), "prevpage.com");
    }

    @Test(expected=Exception.class)
    public void testInvalidTemplateListingResponse() {
        DateTime created = new DateTime(2014, 10, 1, 12, 0, 0, 0);
        DateTime updated = created.plus(Period.hours(48));
        DateTime lastUsed = created.plus(Period.hours(96));

        TemplateVariable variable = TemplateVariable.newBuilder()
                .setKey("varKey")
                .setName("name")
                .setDescription("v descriptive")
                .build();

        PartialPushPayload payload = PartialPushPayload.newBuilder()
                .setNotification(Notification.newBuilder()
                        .setAlert("hello!")
                        .build())
                .build();

        TemplateView template1 = TemplateView.newBuilder()
                .setId("id-123-abc")
                .setCreatedAt(created)
                .setModifiedAt(updated)
                .setLastUsed(lastUsed)
                .setName("template-name")
                .setDescription("blah")
                .addVariable(variable)
                .setPushPayload(payload)
                .build();

        TemplateView template2 = TemplateView.newBuilder()
                .setId("id-123-abc")
                .setCreatedAt(created)
                .setModifiedAt(updated)
                .setLastUsed(lastUsed)
                .setName("template-name")
                .setDescription("blah")
                .addVariable(variable)
                .setPushPayload(payload)
                .build();

        List<TemplateView> templates = new ArrayList<>();
        templates.add(template1);
        templates.add(template2);

        TemplateListingResponse.newBuilder()
                .setOk(true)
                .setTemplates(templates)
                .setTemplate(template1)
                .build();
    }

    @Test
    public void testErrorAPITemplateLookupResponse() throws IOException {
        String jsonResponse = "{\n" +
                "    \"ok\": false,\n" +
                "    \"error\": \"error\",\n" +
                "    \"details\": {\n" +
                "        \"error\": \"error\"\n" +
                "    }\n" +
                "}";

        ObjectMapper mapper = TemplatesObjectMapper.getInstance();
        TemplateListingResponse response = mapper.readValue(jsonResponse, TemplateListingResponse.class);
        assertEquals("error", response.getError().get());
        assertEquals("error", response.getErrorDetails().get().getError().get());
        assertEquals(false, response.getOk());
    }

}
