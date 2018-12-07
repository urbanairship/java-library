package com.urbanairship.api.createandsend.parse.notification.sms;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.createandsend.model.notification.sms.SmsFields;

import java.io.IOException;

public class SmsFieldsSerializer extends JsonSerializer<SmsFields> {

    @Override
    public void serialize(SmsFields smsFields, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (smsFields.getAlert().isPresent()) {
            jgen.writeStringField("alert", smsFields.getAlert().get());
        }

        jgen.writeEndObject();
    }
}
