package com.urbanairship.api.push.parse.audience.sms;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.audience.sms.SmsSelector;

import java.io.IOException;

public class SmsSelectorSerializer extends JsonSerializer<SmsSelector> {

    @Override
    public void serialize(SmsSelector selector, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeObjectFieldStart("sms_id");
        jgen.writeStringField("msisdn", selector.getMsisdn());
        jgen.writeStringField("sender", selector.getSender());
        jgen.writeEndObject();
        jgen.writeEndObject();
    }
}
