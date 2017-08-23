/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.templates.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.templates.model.TemplateSelector;

import java.io.IOException;

public class TemplateSelectorSerializer extends JsonSerializer<TemplateSelector> {

    @Override
    public void serialize(TemplateSelector value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeObjectField("template_id", value.getTemplateId());
        if (value.getSubstitutions().isPresent()) {
            jgen.writeObjectField("substitutions", value.getSubstitutions().get());
        }

        jgen.writeEndObject();
    }
}
