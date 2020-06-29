package com.urbanairship.api.customevents.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.customevents.model.CustomEventOccurred;
import com.urbanairship.api.customevents.model.CustomEventUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomEventOccurredSerializer extends JsonSerializer<CustomEventOccurred> {

    @Override
    public void serialize(CustomEventOccurred eventOccurred, JsonGenerator jgen, SerializerProvider provider) throws IOException {

        jgen.writeStartArray();

        if (eventOccurred.getOccurred() != null){
            jgen.writeObject(eventOccurred.getOccurred());
        }

        jgen.writeEndArray();
    }
}