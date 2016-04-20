/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.tag.parse;

import com.urbanairship.api.tag.model.AddRemoveNamedUserFromTagPayload;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;
import java.util.List;

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

            for (String k : payload.getAddedTags().keySet()) {
                List<String> v = payload.getAddedTags().get(k);

                jgen.writeArrayFieldStart(k);

                for (String s : v) {
                    jgen.writeString(s);
                }
                jgen.writeEndArray();
            }
            jgen.writeEndObject();
        }
        if (payload.getRemovedTags() != null) {
            jgen.writeObjectFieldStart("remove");
            for (String k : payload.getRemovedTags().keySet()) {
                List<String> v = payload.getRemovedTags().get(k);

                jgen.writeArrayFieldStart(k);
                for (String s : v) {
                    jgen.writeString(s);
                }
                jgen.writeEndArray();

            }
            jgen.writeEndObject();
        }

        jgen.writeEndObject();

    }
}
