package com.urbanairship.api.nameduser.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.channel.model.attributes.Attribute;
import com.urbanairship.api.nameduser.model.NamedUserAttributePayload;

import java.io.IOException;

public class NamedUserAttributePayloadSerializer extends JsonSerializer<NamedUserAttributePayload> {
    @Override
    public void serialize(NamedUserAttributePayload payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeArrayFieldStart("attributes");

        for (Attribute attribute : payload.getAttributes()) {
            jgen.writeObject(attribute);
        }

        jgen.writeEndArray();

        jgen.writeEndObject();

    }
}
