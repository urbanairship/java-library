/*
 * Copyright (c) 2013-2017.  Urban Airship and Contributors
 */

package com.urbanairship.api.experiments.parse;

import com.urbanairship.api.experiments.model.Variant;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class VariantSerializer extends JsonSerializer<Variant>{

    @Override
    public void serialize(Variant variant, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (variant.getName().isPresent()) {
            jgen.writeObjectField("name", variant.getName().get());
        }
        if (variant.getDescription().isPresent()) {
            jgen.writeObjectField("description", variant.getDescription().get());
        }
        if (variant.getSchedule().isPresent()) {
            jgen.writeObjectField("schedule", variant.getSchedule().get());
        }
        if (variant.getWeight().isPresent()) {
            jgen.writeObjectField("weight", variant.getWeight().get());
        }
        jgen.writeObjectField("push", variant.getVariantPushPayload());

        jgen.writeEndObject();
    }
}
