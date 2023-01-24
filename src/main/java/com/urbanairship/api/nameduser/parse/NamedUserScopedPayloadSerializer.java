package com.urbanairship.api.nameduser.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.nameduser.model.NamedUserScopedPayload;
import com.urbanairship.api.nameduser.model.NamedUserScope;

import java.io.IOException;

public class NamedUserScopedPayloadSerializer extends JsonSerializer<NamedUserScopedPayload> {
    @Override
    public void serialize(NamedUserScopedPayload payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeArrayFieldStart("scoped");

        for (NamedUserScope namedUserScope : payload.getScoped()) {
            jgen.writeObject(namedUserScope);
        }

        jgen.writeEndArray();

        jgen.writeEndObject();

    }
}
