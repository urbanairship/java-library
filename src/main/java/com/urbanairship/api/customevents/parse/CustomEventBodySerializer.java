package com.urbanairship.api.customevents.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.customevents.model.CustomEventBody;

import java.io.IOException;

public class CustomEventBodySerializer extends JsonSerializer<CustomEventBody> {

    @Override
    public void serialize(CustomEventBody body, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeStringField("name", body.getName());
        jgen.writeStringField("session_id", body.getSessionId());

        if (body.getInteractionId().isPresent()) {
            jgen.writeStringField("interaction_id", body.getInteractionId().get());
        }

        if (body.getInteractionType().isPresent()) {
            jgen.writeStringField("interaction_type", body.getInteractionType().get());
        }

        if (body.getTransaction().isPresent()) {
            jgen.writeStringField("transaction", body.getTransaction().get());
        }

        if (body.getProperties().isPresent()) {
            jgen.writeObjectField("properties", body.getProperties().get());
        }

        if (body.getValue().isPresent()) {
            jgen.writeObjectField("value", body.getValue().get());
        }

        jgen.writeEndObject();
    }
}
