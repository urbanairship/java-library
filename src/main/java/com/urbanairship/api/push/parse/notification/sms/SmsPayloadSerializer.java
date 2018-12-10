package com.urbanairship.api.push.parse.notification.sms;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.sms.SmsPayload;

import java.io.IOException;

public class SmsPayloadSerializer extends JsonSerializer<SmsPayload> {
    @Override
    public void serialize(SmsPayload payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (payload.getAlert().isPresent()) {
            jgen.writeStringField("alert", payload.getAlert().get());
        }

        if (payload.getExpiry().isPresent()) {
            jgen.writeObjectField("expiry", payload.getExpiry().get());
        }

        jgen.writeEndObject();
    }
}