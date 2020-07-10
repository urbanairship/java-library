package com.urbanairship.api.customevents.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.customevents.model.CustomEventUser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomEventUserSerializer extends JsonSerializer<CustomEventUser> {

    @Override
    public void serialize(CustomEventUser eventUser, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (eventUser.getChannel().isPresent()) {
            jgen.writeStringField(eventUser.getChannelType().get().getIdentifier(), eventUser.getChannel().get());
        }
        if (eventUser.getNamedUserId().isPresent()) {
            jgen.writeStringField("named_user_id", eventUser.getNamedUserId().get());
        }
        jgen.writeEndObject();
    }
}
