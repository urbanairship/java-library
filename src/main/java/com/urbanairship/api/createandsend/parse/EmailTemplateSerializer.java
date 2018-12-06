package com.urbanairship.api.createandsend.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.createandsend.model.notification.EmailTemplate;

import java.io.IOException;

public class EmailTemplateSerializer extends JsonSerializer<EmailTemplate> {

    @Override
    public void serialize(EmailTemplate template, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (template.getTemplateId().isPresent()) {
            jgen.writeStringField("template_id", template.getTemplateId().get());
            if (template.getVariableDetails().isPresent()) {
                jgen.writeObjectField("variable_details", template.getVariableDetails().get());
            }

        } else if (template.getFields().isPresent()) {
            jgen.writeObjectField("fields", template.getFields().get());
            if (template.getVariableDetails().isPresent()) {
                jgen.writeObjectField("variable_details", template.getVariableDetails().get());
            }
        }

        jgen.writeEndObject();
    }
}
