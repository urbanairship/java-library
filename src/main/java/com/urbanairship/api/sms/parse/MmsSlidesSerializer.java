package com.urbanairship.api.sms.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.sms.model.MmsSlides;

import java.io.IOException;

public class MmsSlidesSerializer extends JsonSerializer<MmsSlides> {

    @Override
    public void serialize(MmsSlides payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeArrayFieldStart("slides");
        jgen.writeStartObject();

        if (payload.getText().isPresent()) {
            jgen.writeStringField("text", payload.getText().get());
        }

        jgen.writeObjectFieldStart("media");
        jgen.writeStringField("url", payload.getMediaUrl());
        jgen.writeStringField("content_type", payload.getMediaContentType());

        if (payload.getMediaContentLength().isPresent()) {
            jgen.writePOJOField("content_length", payload.getMediaContentLength().get());
        }

        jgen.writeEndObject();
        jgen.writeEndObject();
        jgen.writeEndArray();
    }
}