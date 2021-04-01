package com.urbanairship.api.push.parse.notification.web;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.web.Button;

import java.io.IOException;

public class ButtonSerializer extends JsonSerializer<Button> {

    @Override
    public void serialize(Button button, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeStringField("id", button.getId());
        jgen.writeStringField("label", button.getLabel());

        if (button.getActions().isPresent()) {
            jgen.writeObjectField("actions", button.getActions().get());
        }

        jgen.writeEndObject();
    }
}
