package com.urbanairship.api.templates.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.templates.model.TemplateVariable;
import com.urbanairship.api.templates.model.TemplateView;

import java.io.IOException;

public class TemplateViewSerializer extends JsonSerializer<TemplateView> {

    @Override
    public void serialize(TemplateView value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();

        jgen.writeStringField("name", value.getName());
        if (value.getDescription().isPresent()) {
            jgen.writeObjectField("description", value.getDescription().get());
        }
        if (value.getPartialPushPayload().isPresent()) {
            jgen.writeObjectField("push", value.getPartialPushPayload().get());
        }

        jgen.writeArrayFieldStart("variables");
        for (TemplateVariable var : value.getVariables()) {
            jgen.writeObject(var);
        }
        jgen.writeEndArray();
        jgen.writeEndObject();
    }
}
