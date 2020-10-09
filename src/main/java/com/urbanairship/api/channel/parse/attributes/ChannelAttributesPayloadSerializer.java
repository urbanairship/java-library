package com.urbanairship.api.channel.parse.attributes;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.channel.model.attributes.Attribute;
import com.urbanairship.api.channel.model.attributes.ChannelAttributesPayload;

import java.io.IOException;

public class ChannelAttributesPayloadSerializer extends JsonSerializer<ChannelAttributesPayload> {
    @Override
    public void serialize(ChannelAttributesPayload payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeObjectField("audience", payload.getAudience());

        jgen.writeArrayFieldStart("attributes");

        for (Attribute attribute : payload.getAttributes()) {
            jgen.writeObject(attribute);
        }

        jgen.writeEndArray();

        jgen.writeEndObject();
    }
}
