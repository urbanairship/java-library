package com.urbanairship.api.push.parse.notification.open;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.open.OpenPayload;

import java.io.IOException;

public class OpenPayloadSerializer extends JsonSerializer<OpenPayload> {
    @Override
    public void serialize(OpenPayload payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (payload.getAlert().isPresent()) {
            jgen.writeStringField("alert", payload.getAlert().get());
        }

        if (payload.getExtras().isPresent()) {
            jgen.writeObjectField("extra", payload.getExtras().get());
        }

        if (payload.getInteractive().isPresent()) {
            jgen.writeObjectField("interactive", payload.getInteractive().get());
        }

        if (payload.getMediaAttachment().isPresent()) {
            jgen.writeStringField("media_attachment", payload.getMediaAttachment().get());
        }

        if (payload.getTitle().isPresent()) {
            jgen.writeStringField("title", payload.getTitle().get());
        }

        if (payload.getSummary().isPresent()) {
            jgen.writeStringField("summary", payload.getSummary().get());
        }

        jgen.writeEndObject();
    }
}
