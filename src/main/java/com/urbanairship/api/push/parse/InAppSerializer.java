package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.push.model.InApp;

import java.io.IOException;

public class InAppSerializer extends JsonSerializer<InApp> {

    @Override
    public void serialize(InApp inApp, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("alert", inApp.getAlert());
        jsonGenerator.writeStringField("display_type", inApp.getDisplayType());

        if (inApp.getExpiry().isPresent()) {
            jsonGenerator.writeObjectField("expiry", inApp.getExpiry().get());
        }

        if (inApp.getDisplay().isPresent()) {
            jsonGenerator.writeObjectField("display", inApp.getDisplay().get());
        }

        if (inApp.getActions().isPresent()) {
            jsonGenerator.writeObjectField("actions", inApp.getActions().get());
        }

        if (inApp.getInteractive().isPresent()) {
            jsonGenerator.writeObjectField("interactive", inApp.getInteractive().get());
        }

        if (inApp.getExtra().isPresent()) {
            jsonGenerator.writeObjectField("extra", inApp.getExtra().get());
        }

        jsonGenerator.writeEndObject();
    }
}
