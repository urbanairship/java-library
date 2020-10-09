package com.urbanairship.api.channel.parse.attributes.audience;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.channel.model.attributes.audience.AttributeAudience;
import com.urbanairship.api.channel.model.attributes.audience.AttributeAudienceType;
import com.urbanairship.api.push.model.audience.sms.SmsSelector;

import java.io.IOException;

public class AttributeAudienceSerializer extends JsonSerializer<AttributeAudience> {

    @Override
    public void serialize(AttributeAudience audience, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (audience.getAttributeDevices().size() > 0) {
            for (AttributeAudienceType audienceType : audience.getAttributeDevices().keySet()) {
                jgen.writeObjectField(audienceType.getIdentifier(), audience.getAttributeDevices().get(audienceType));
            }
        }

        if (audience.getSmsSelectors().size() > 0) {
            jgen.writeArrayFieldStart("sms_id");

            for (SmsSelector smsSelector : audience.getSmsSelectors()){
                jgen.writeStartObject();
                jgen.writeStringField("msisdn", smsSelector.getMsisdn());
                jgen.writeStringField("sender", smsSelector.getSender());
                jgen.writeEndObject();
            }
            jgen.writeEndArray();
        }

        jgen.writeEndObject();
    }
}
