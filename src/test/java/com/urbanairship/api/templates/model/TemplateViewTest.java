package com.urbanairship.api.templates.model;

import com.urbanairship.api.push.model.notification.Notification;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class TemplateViewTest {

    @Test
    public void testTemplateView() {
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

        assertNotNull(obj);
        assertEquals(obj.getId(), "id-123-abc");
        assertEquals(obj.getCreatedAt(), created);
        assertEquals(obj.getModifiedAt(), updated);
        assertEquals(obj.getLastUsed(), lastUsed);
        assertEquals(obj.getName(), "template-name");
        assertEquals(obj.getDescription().get(), "blah");
        assertFalse(obj.getVariables().isEmpty());
        assertEquals(obj.getVariables().get(0), variable);
        assertEquals(obj.getPartialPushPayload().get(), payload);
    }
}
