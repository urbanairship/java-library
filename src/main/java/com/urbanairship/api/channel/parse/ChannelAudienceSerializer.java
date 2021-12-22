package com.urbanairship.api.channel.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.channel.model.ChannelAudience;
import com.urbanairship.api.channel.model.ChannelAudienceType;
import com.urbanairship.api.push.model.audience.sms.SmsSelector;

import java.io.IOException;

public class ChannelAudienceSerializer extends JsonSerializer<ChannelAudience> {

    @Override
    public void serialize(ChannelAudience audience, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (audience.getChannelDevices().size() > 0) {
            for (ChannelAudienceType audienceType : audience.getChannelDevices().keySet()) {
                jgen.writeObjectField(audienceType.getIdentifier(), audience.getChannelDevices().get(audienceType));
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
