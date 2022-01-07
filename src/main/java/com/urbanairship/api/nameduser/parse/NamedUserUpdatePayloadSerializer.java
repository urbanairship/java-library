package com.urbanairship.api.nameduser.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.channel.Constants;
import com.urbanairship.api.channel.model.attributes.Attribute;
import com.urbanairship.api.nameduser.model.NamedUserUpdateChannel;
import com.urbanairship.api.nameduser.model.NamedUserUpdatePayload;

import java.io.IOException;

public class NamedUserUpdatePayloadSerializer extends JsonSerializer<NamedUserUpdatePayload> {

    @Override
    public void serialize(NamedUserUpdatePayload payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {

        jgen.writeStartObject();

        jgen.writeArrayFieldStart(payload.getAction().getIdentifier());

        for (NamedUserUpdateChannel channel : payload.getChannels()) {
            jgen.writeObject(channel);
        }

        jgen.writeEndArray();    
        
        if (!payload.getAddTags().isEmpty() || !payload.getSetTags().isEmpty() || !payload.getRemoveTags().isEmpty()) {
            jgen.writeFieldName(Constants.TAGS);
            jgen.writeStartObject();
            if (!payload.getAddTags().isEmpty()) {
                jgen.writePOJOField(Constants.ADD_KEY, payload.getAddTags());
            } 
            if (!payload.getSetTags().isEmpty()) {
                jgen.writePOJOField(Constants.SET_KEY, payload.getSetTags());
            }
            if (!payload.getRemoveTags().isEmpty()) {
                jgen.writePOJOField(Constants.REMOVE_KEY, payload.getRemoveTags());
            }
            jgen.writeEndObject();
        }
        if (!payload.getAttributes().isEmpty()) {
            jgen.writeArrayFieldStart(Constants.ATTRIBUTES);

            for (Attribute attribute : payload.getAttributes()) {
                jgen.writeObject(attribute);
            }
    
            jgen.writeEndArray();
        }
        jgen.writeEndObject();
    }
}
