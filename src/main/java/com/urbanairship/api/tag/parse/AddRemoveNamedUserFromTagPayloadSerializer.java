/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.tag.parse;

import com.urbanairship.api.tag.model.AddRemoveDeviceFromTagPayload;
import com.urbanairship.api.tag.model.AddRemoveNamedUserFromTagPayload;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public final class AddRemoveNamedUserFromTagPayloadSerializer extends JsonSerializer<AddRemoveNamedUserFromTagPayload> {

    @Override
    public void serialize(AddRemoveNamedUserFromTagPayload payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();


        jgen.writeObjectFieldStart("audience");
        jgen.writeArrayFieldStart(payload.getAudienceName());

        for (String s : payload.getAudience()) {
                    jgen.writeString(s);
                }
        jgen.writeEndArray();
        jgen.writeEndObject();


        if (payload.getAddedTags() != null) {
            jgen.writeObjectFieldStart("add");
            payload.getAddedTags().forEach((k, v) -> {
                try {
                jgen.writeArrayFieldStart(k);

                    for (String s : v) {
                        jgen.writeString(s);
                    }
                jgen.writeEndArray();

                } catch (IOException io){

                }
            });
            jgen.writeEndObject();
        }
        if (payload.getRemovedTags() != null) {
            jgen.writeObjectFieldStart("remove");
            payload.getRemovedTags().forEach((k, v) -> {
                try{
                    jgen.writeArrayFieldStart(k);
                for (String s : v) {
                    jgen.writeString(s);
                }
                jgen.writeEndArray();
            } catch (IOException io){

            }
            });
            jgen.writeEndObject();
        }

        jgen.writeEndObject();
    }
}
