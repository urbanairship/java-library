package com.urbanairship.api.createandsend.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.createandsend.model.notification.SmsTemplate;

import java.io.IOException;

public class SmsTemplateSerializer extends JsonSerializer<SmsTemplate> {
    @Override
    public void serialize(SmsTemplate smsTemplate, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (smsTemplate.getSmsFields().isPresent()) {
            jgen.writeObjectField("fields", smsTemplate.getSmsFields().get());
        }

        jgen.writeEndObject();
    }
}
