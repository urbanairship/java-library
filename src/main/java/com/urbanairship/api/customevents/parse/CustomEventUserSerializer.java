package com.urbanairship.api.customevents.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.customevents.model.CustomEventUser;

import java.io.IOException;

public class CustomEventUserSerializer extends JsonSerializer<CustomEventUser> {

    @Override
    public void serialize(CustomEventUser eventUser, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeStringField(eventUser.getChannelType().getIdentifier(), eventUser.getChannel());

        jgen.writeEndObject();
    }
}
