/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.channel.information.parse;

import com.urbanairship.api.channel.information.model.TagMutationPayload;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class TagMutationPayloadSerializer extends JsonSerializer<TagMutationPayload> {
    @Override
    public void serialize(TagMutationPayload value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        
        jgen.writeObjectField("audience", value.getAudience());

        if (value.getSetTags().isPresent()) {
            jgen.writeObjectField("set", value.getSetTags().get());
        }

        if (value.getAddedTags().isPresent()) {
            jgen.writeObjectField("add", value.getAddedTags().get());
        }

        if (value.getRemovedTags().isPresent()) {
            jgen.writeObjectField("remove", value.getRemovedTags().get());
        }

        jgen.writeEndObject();
    }
}
