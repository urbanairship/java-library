package com.urbanairship.api.channel.parse.sms;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.channel.model.sms.UpdateSmsChannel;
import com.urbanairship.api.common.parse.DateFormats;

import java.io.IOException;

public class UpdateSmsChannelSerializer extends JsonSerializer<UpdateSmsChannel> {

    @Override
    public void serialize(UpdateSmsChannel payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("msisdn", payload.getMsisdn());
        jgen.writeStringField("sender", payload.getSender());

        if (payload.getOptedIn().isPresent()) {
            jgen.writeStringField("opted_in", DateFormats.DATE_FORMATTER.print(payload.getOptedIn().get()));
        }

        if (payload.getTimezone().isPresent()) {
            jgen.writeStringField("timezone", payload.getTimezone().get());
        }

        if (payload.getLocaleCountry().isPresent()) {
            jgen.writeStringField("locale_country", payload.getLocaleCountry().get());
        }

        if (payload.getLocaleLanguage().isPresent()) {
            jgen.writeStringField("locale_language", payload.getLocaleLanguage().get());
        }

        jgen.writeEndObject();
    }
}