package com.urbanairship.api.templates.model;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TemplateVariableTest {

    @Test
    public void testMinimalTemplateVariable() {
        TemplateVariable variable = TemplateVariable.newBuilder()
                .setKey("key")
                .setName("name")
                .setDescription("description")
                .setDefaultValue("defaultz")
                .build();

        assertNotNull(variable);
        assertEquals(variable.getKey(), "key");
        assertEquals(variable.getName().get(), "name");
        assertEquals(variable.getDescription().get(), "description");
        assertEquals(variable.getDefaultValue(), "defaultz");
    }

    @Test
    public void testMaximalTemplateVariable() {
        TemplateVariable variable = TemplateVariable.newBuilder()
                .setKey("key")
                .build();

        assertNotNull(variable);
        assertEquals(variable.getKey(), "key");
    }

    @Test(expected=Exception.class)
    public void testInvalidTemplateVariable() {
        TemplateVariable variable = TemplateVariable.newBuilder()
                .setName("this_wont_work")
                .build();
    }
}
