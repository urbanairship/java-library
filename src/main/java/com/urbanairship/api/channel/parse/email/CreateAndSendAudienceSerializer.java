package com.urbanairship.api.channel.parse.email;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.channel.Constants;
import com.urbanairship.api.channel.model.email.RegisterEmailChannel;
import com.urbanairship.api.push.model.audience.CreateAndSendAudience;
import sun.jvm.hotspot.asm.Register;

import java.io.IOException;
import java.util.ArrayList;

public class CreateAndSendAudienceSerializer extends JsonSerializer<CreateAndSendAudience> {

    @Override
    public void serialize(CreateAndSendAudience payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {

        jgen.writeStartObject();
        jgen.writeArrayFieldStart("create_and_send");

        for (RegisterEmailChannel channel : payload.getEmailChannelList()) {
            jgen.writeStartObject();
            jgen.writeStringField("ua_address",
                    channel.getUaAddress().get());
            jgen.writeStringField(channel.getCreateAndSendOptInLevel().get(),
                    channel.getCreateAndSendTimestamp().get());
            jgen.writeEndObject();
        }
        jgen.writeEndArray();
        jgen.writeEndObject();
    }
}
