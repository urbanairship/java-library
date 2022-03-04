package com.urbanairship.api.sms.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.sms.model.CustomSmsResponseMmsPayload;

import java.io.IOException;

public class CustomSmsResponseMmsPayloadSerializer extends JsonSerializer<CustomSmsResponseMmsPayload> {

    @Override
    public void serialize(CustomSmsResponseMmsPayload payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("mobile_originated_id", payload.getMobileOriginatedId());

        jgen.writeObjectFieldStart("mms");
        jgen.writeStringField("fallback_text", payload.getFallbackText());
        jgen.writePOJO(payload.getSlides());

        jgen.writeEndObject();
        jgen.writeEndObject();
    }
}