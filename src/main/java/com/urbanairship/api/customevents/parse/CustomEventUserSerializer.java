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

        List<Map<String, String>> userList = new ArrayList<>();
        Map<String, String> channelMap = new HashMap<>();

        if (eventUser.getChannel() != null) {
            channelMap.put(eventUser.getChannelType().getIdentifier(), eventUser.getChannel());
        }

        userList.add(channelMap);
        jgen.writeObject(channelMap);
    }
}
