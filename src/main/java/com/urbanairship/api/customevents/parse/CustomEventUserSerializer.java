package com.urbanairship.api.customevents.parse;

import com.urbanairship.api.customevents.model.CustomEventUser;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class CustomEventUserSerializer extends JsonSerializer<CustomEventUser> {

    @Override
    public void serialize(CustomEventUser eventUser, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeStringField(eventUser.getChannelType().getIdentifier(), eventUser.getChannel());

        jgen.writeEndObject();
    }
}
