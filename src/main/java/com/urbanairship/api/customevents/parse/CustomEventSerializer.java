package com.urbanairship.api.customevents.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.customevents.model.CustomEventPayload;

import java.io.IOException;

public class CustomEventSerializer extends JsonSerializer<CustomEventPayload> {

    @Override
    public void serialize(CustomEventPayload event, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartArray();
        jgen.writeStartObject();
        //"occurred' object used instead of datetime for consistency and ease of serializing.
        jgen.writeObjectField("occurred", event.getOccurred());
        jgen.writeObjectField("user", event.getCustomEventUser());
        jgen.writeObjectField("body", event.getCustomEventBody());

        jgen.writeEndObject();
        jgen.writeEndArray();
    }
}
