package com.urbanairship.api.push.parse;

import com.urbanairship.api.push.model.Display;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class DisplaySerializer extends JsonSerializer<Display> {

    @Override
    public void serialize(Display display, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        jsonGenerator.writeStartObject();

        if (display.getPrimaryColor().isPresent()) {
            jsonGenerator.writeStringField("primary_color", display.getPrimaryColor().get());
        }

        if (display.getSecondaryColor().isPresent()) {
            jsonGenerator.writeStringField("secondary_color", display.getSecondaryColor().get());
        }

        if (display.getDuration().isPresent()) {
            jsonGenerator.writeNumberField("duration", display.getDuration().get());
        }

        if (display.getPosition().isPresent()) {
            jsonGenerator.writeStringField("position", display.getPosition().get().getType());
        }

        jsonGenerator.writeEndObject();
    }
}
