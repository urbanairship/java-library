package com.urbanairship.api.channel.parse.open;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.channel.Constants;
import com.urbanairship.api.channel.model.open.OpenChannel;

import java.io.IOException;

public class OpenChannelSerializer extends JsonSerializer<OpenChannel> {

    @Override
    public void serialize(OpenChannel openChannel, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeStringField(Constants.OPEN_PLATFORM_NAME, openChannel.getOpenPlatformName());

        if (openChannel.getOldAddress().isPresent()) {
            jgen.writeStringField(Constants.OLD_ADDRESS, openChannel.getOldAddress().get());
        }

        if (openChannel.getIdentifiers().isPresent()) {
            jgen.writeObjectField(Constants.IDENTIFIERS, openChannel.getIdentifiers().get());
        }

        jgen.writeEndObject();
    }
}