package com.urbanairship.api.customevents.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.customevents.model.CustomEventUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomEventUserSerializer extends JsonSerializer<CustomEventUser> {

    @Override
    public void serialize(CustomEventUser eventUser, JsonGenerator jgen, SerializerProvider provider) throws IOException {

        jgen.writeStartArray();
        Map<String, String> eventMap = new HashMap<>();

        if (eventUser.getChannel() != null) {
            eventMap.put("channel", eventUser.getChannel());
        }
        if (eventUser.getNamedUser() != null) {
            eventMap.put("named_user_id", eventUser.getNamedUser());
        }

        jgen.writeObject(eventMap);
        jgen.writeEndArray();
    }
}
