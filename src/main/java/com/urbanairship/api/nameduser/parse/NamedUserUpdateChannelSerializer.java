package com.urbanairship.api.nameduser.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.channel.Constants;
import com.urbanairship.api.nameduser.model.NamedUserUpdateChannel;

import java.io.IOException;

public class NamedUserUpdateChannelSerializer extends JsonSerializer<NamedUserUpdateChannel> {

    @Override
    public void serialize(NamedUserUpdateChannel channel, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (channel.getChannelId().isPresent()) {
            jgen.writeStringField(Constants.CHANNEL_ID, channel.getChannelId().get());
        }

        if (channel.getDeviceType().isPresent()) {
            jgen.writeStringField(Constants.DEVICE_TYPE, channel.getDeviceType().get().getIdentifier());
        }

        if (channel.getEmailAddress().isPresent()) {
            jgen.writeStringField(Constants.EMAIL_ADDRESS, channel.getEmailAddress().get());
        }

        jgen.writeEndObject();
    }
}