package com.urbanairship.api.nameduser.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.nameduser.model.NamedUserScope;
import com.urbanairship.api.nameduser.model.NamedUserScopeType;

import java.io.IOException;

public class NamedUserScopeSerializer extends JsonSerializer<NamedUserScope> {
    @Override
    public void serialize(NamedUserScope namedUserScope, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeArrayFieldStart("scope");
        for (NamedUserScopeType value : namedUserScope.getScope()) {
            jgen.writeString(value.getIdentifier());
        }
        jgen.writeEndArray();

        jgen.writeObjectFieldStart("subscription_lists");

        if (!namedUserScope.getSubscribeLists().isEmpty()) {
            jgen.writeArrayFieldStart("subscribe");
            for (String value : namedUserScope.getSubscribeLists()) {
                jgen.writeString(value);
            }
            jgen.writeEndArray();
        }

        if (!namedUserScope.getUnsubscribeLists().isEmpty()) {
            jgen.writeArrayFieldStart("unsubscribe");
            for (String value : namedUserScope.getUnsubscribeLists()) {
                jgen.writeString(value);
            }
            jgen.writeEndArray();
        }

        jgen.writeEndObject();
        
        jgen.writeEndObject();

    }
}
