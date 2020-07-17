package com.urbanairship.api.customevents.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.customevents.model.CustomEventPropValue;

import java.io.IOException;
import java.lang.reflect.Type;


public class CustomEventCustomEventPropValueSerializer  {

    @Override

     public void serialize(CustomEventPropValue customEventPropValue, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {

        jgen.writeStartObject();

        jgen.writeStringField(customEventPropValue.get.getIdentifier(), eventUser.getChannel());


        if (src.isArray()) {
            return context.serialize(src.getAsList(), typeOfList);
        }
        if (src.isObject()) {
            return context.serialize(src.getAsMap(), typeOfMap);
        }
        if (src.isBoolean()) {
            return new JsonPrimitive(src.getAsBoolean());
        }
        if (src.isNumber()) {
            return new JsonPrimitive(src.getAsNumber());
        }
        return new JsonPrimitive(src.getAsString());


        jgen.writeEndObject();
    }

}

