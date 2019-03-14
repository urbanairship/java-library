package com.urbanairship.api.push.parse.notification.ios;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.ios.IOSSoundData;

import java.io.IOException;

public class IOSSoundDataSerializer extends JsonSerializer<IOSSoundData> {
    @Override
    public void serialize(IOSSoundData payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (!payload.shouldBeDict()) {
            jgen.writeString(payload.getName().get());
        } else {
            jgen.writeStartObject();

            if (payload.getCritical().isPresent()) {
                jgen.writeBooleanField("critical", payload.getCritical().get());
            }

            if (payload.getVolume().isPresent()) {
                jgen.writeNumberField("volume", payload.getVolume().get());
            }

            if (payload.getName().isPresent()) {
                jgen.writeStringField("name", payload.getName().get());
            }

            jgen.writeEndObject();
        }
    }
}
