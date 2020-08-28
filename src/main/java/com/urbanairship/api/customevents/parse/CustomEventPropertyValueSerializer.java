package com.urbanairship.api.customevents.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.customevents.model.CustomEventPropertyValue;

import java.io.IOException;

public class CustomEventPropertyValueSerializer extends JsonSerializer<CustomEventPropertyValue> {

    @Override
     public void serialize(CustomEventPropertyValue customEventPropertyValue, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {

        if (customEventPropertyValue.isArray()) {
            jgen.writeObject(customEventPropertyValue.getAsList());
        }
        if (customEventPropertyValue.isObject()) {
            jgen.writeObject(customEventPropertyValue.getAsMap());
        }
        if (customEventPropertyValue.isBoolean()) {
            jgen.writeBoolean(customEventPropertyValue.getAsBoolean());
        }
        if (customEventPropertyValue.isNumber()) {
            jgen.writeNumber(String.valueOf(customEventPropertyValue.getAsNumber()));
        }
        if (customEventPropertyValue.isString()) {
            jgen.writeString(customEventPropertyValue.getAsString());
        }
    }
}

