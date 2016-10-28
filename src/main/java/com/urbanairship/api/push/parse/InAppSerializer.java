package com.urbanairship.api.push.parse;

import com.urbanairship.api.push.model.InApp;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class InAppSerializer extends JsonSerializer<InApp> {

    @Override
    public void serialize(InApp inApp, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("alert", inApp.getAlert());
        jsonGenerator.writeStringField("display_type", inApp.getDisplayType());

        if (inApp.getExpiry().isPresent()) {
            jsonGenerator.writeStringField("expiry", inApp.getExpiry().get().toString());
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
