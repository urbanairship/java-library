package com.urbanairship.api.channel.parse.email;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.channel.Constants;
import com.urbanairship.api.channel.model.email.RegisterEmailChannel;
import com.urbanairship.api.channel.model.open.Channel;
import sun.jvm.hotspot.asm.Register;

import java.io.IOException;

public class RegisterEmailChannelSerializer extends JsonSerializer<RegisterEmailChannel> {

    @Override
    public void serialize(RegisterEmailChannel payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeObjectFieldStart(Constants.CHANNEL);
        jgen.writeStringField(Constants.TYPE, payload.getType().getIdentifier());


        if (payload.getAddress().isPresent()) {
            jgen.writeStringField(Constants.ADDRESS, payload.getAddress().get());
        }

        if (payload.getEmailOptInLevel().isPresent()) {
            jgen.writeStringField(Constants.EMAIL_OPT_IN_LEVEL, payload.getEmailOptInLevel().get().getIdentifier());
        }

        if (payload.getTimezone().isPresent()) {
            jgen.writeStringField(Constants.TIMEZONE, payload.getTimezone().get());
        }

        if (payload.getLocaleCountry().isPresent()) {
            jgen.writeStringField(Constants.LOCALE_COUNTRY, payload.getLocaleCountry().get());
        }

        if (payload.getLocaleLanguage().isPresent()) {
            jgen.writeStringField(Constants.LOCALE_LANGUAGE, payload.getLocaleLanguage().get());
        }

        jgen.writeEndObject();
    }
}
