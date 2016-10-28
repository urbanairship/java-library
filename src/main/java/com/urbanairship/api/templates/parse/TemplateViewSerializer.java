package com.urbanairship.api.templates.parse;

import com.urbanairship.api.templates.model.TemplateVariable;
import com.urbanairship.api.templates.model.TemplateView;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

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
