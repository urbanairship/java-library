package com.urbanairship.api.sms.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.sms.model.CustomSmsResponseSmsPayload;

import java.io.IOException;

public class CustomSmsResponseSmsPayloadSerializer extends JsonSerializer<CustomSmsResponseSmsPayload> {

    @Override
    public void serialize(CustomSmsResponseSmsPayload payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("mobile_originated_id", payload.getMobileOriginatedId());
        jgen.writeObjectFieldStart("sms");
        jgen.writeStringField("alert", payload.getAlert());

        if (payload.getShortenLinks().isPresent()) {
            jgen.writePOJOField("shorten_links", payload.getShortenLinks().get());
        }
        jgen.writeEndObject();
        jgen.writeEndObject();
    }
}