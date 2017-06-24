/*
 * Copyright (c) 2013-2016. Urban Airship and Contributors
 */

package com.urbanairship.api.templates.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.templates.model.TemplateVariable;

import java.io.IOException;

public class TemplateVariableSerializer extends JsonSerializer<TemplateVariable> {

    @Override
    public void serialize(TemplateVariable value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeObjectField("key", value.getKey());
        if (value.getDescription().isPresent()) {
            jgen.writeObjectField("description", value.getDescription().get());
        }
        if (value.getName().isPresent()) {
            jgen.writeObjectField("name", value.getName().get());
        }
        jgen.writeObjectField("default_value", value.getDefaultValue());

        jgen.writeEndObject();
    }
}