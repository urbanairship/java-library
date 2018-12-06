package com.urbanairship.api.createandsend.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.createandsend.model.notification.SmsPayload;

import java.io.IOException;

public class SmsPayloadSerializer extends JsonSerializer<SmsPayload> {

    @Override
    public void serialize(SmsPayload smsPayload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (smsPayload.getAlert().isPresent()) {
            jgen.writeStringField("alert", smsPayload.getAlert().get());

            if (smsPayload.getPushExpiry().isPresent()) {
                jgen.writeObjectField("expiry", smsPayload.getPushExpiry().get());
            }
        } else if (smsPayload.getSmsTemplate().isPresent()) {
            jgen.writeObjectField("template", smsPayload.getSmsTemplate().get());

            if (smsPayload.getPushExpiry().isPresent()) {
                jgen.writeObjectField("expiry", smsPayload.getPushExpiry().get());
            }
        }

        jgen.writeEndObject();
    }
}
