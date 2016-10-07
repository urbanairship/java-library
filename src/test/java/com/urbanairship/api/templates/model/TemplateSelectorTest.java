package com.urbanairship.api.templates.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TemplateSelectorTest {

    @Test
    public void testTemplateSelector() {
        TemplateSelector selector = TemplateSelector.newBuilder()
                .setTemplateId("id123")
                .addSubstitution("NAME", "Sam")
                .addSubstitution("TITLE", "Ms.")
                .build();

        assertNotNull(selector);
        assertEquals(selector.getTemplateId(), "id123");
        assertEquals(selector.getSubstitutions().get().get("NAME"), "Sam");
        assertEquals(selector.getSubstitutions().get().get("TITLE"), "Ms.");
    }

    @Test(expected=Exception.class)
    public void testInvalidTemplateSelector() {
        TemplateSelector selector = TemplateSelector.newBuilder()
                .addSubstitution("NAME", "Sam")
                .addSubstitution("TITLE", "Ms.")
                .build();
    }
}
