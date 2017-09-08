package com.urbanairship.api.channel.parse.open;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.channel.Constants;
import com.urbanairship.api.channel.model.open.Channel;

import java.io.IOException;

public class ChannelSerializer extends JsonSerializer<Channel> {

    @Override
    public void serialize(Channel payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeStringField(Constants.TYPE, payload.getType().getIdentifier());
        jgen.writeStringField(Constants.ADDRESS, payload.getAddress());
        jgen.writeObjectField(Constants.OPEN_CHANNEL, payload.getOpen());

        if (payload.getOptIn().isPresent()) {
            jgen.writeBooleanField(Constants.OPT_IN, payload.getOptIn().get());
        }

        if (payload.getSetTags().isPresent()) {
            jgen.writeBooleanField(Constants.SET_TAGS, payload.getSetTags().get());
        }

        if (payload.getTags().isPresent()) {
            jgen.writeObjectField(Constants.TAGS, payload.getTags().get());
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
