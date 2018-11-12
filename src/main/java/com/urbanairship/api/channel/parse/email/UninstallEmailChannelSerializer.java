package com.urbanairship.api.channel.parse.email;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.channel.Constants;
import com.urbanairship.api.channel.model.email.UninstallEmailChannel;

import java.io.IOException;

public class UninstallEmailChannelSerializer extends JsonSerializer<UninstallEmailChannel> {

    @Override
    public void serialize(UninstallEmailChannel payload, JsonGenerator jgen,
                          SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();

        jgen.writeStringField(Constants.EMAIL_ADDRESS, payload.getEmailAddress());

        jgen.writeEndObject();
    }
}