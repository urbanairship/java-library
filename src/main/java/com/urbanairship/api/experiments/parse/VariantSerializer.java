/*
 * Copyright (c) 2013-2017.  Urban Airship and Contributors
 */

package com.urbanairship.api.experiments.parse;

import com.urbanairship.api.experiments.model.Experiment.Variant;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class VariantSerializer extends JsonSerializer<Variant>{

    public static final VariantSerializer INSTANCE = new VariantSerializer();

    @Override
    public void serialize(Variant variant, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (variant.getName().isPresent()) {
            jgen.writeObjectField("name", variant.getName().get());
        }
        if (variant.getDescription().isPresent()) {
            jgen.writeObjectField("description", variant.getDescription().get());
        }
        if (variant.getWeight().isPresent()) {
            jgen.writeObjectField("weight", variant.getWeight().get());
        }
        jgen.writeObjectField("push", variant.getPartialPushPayload());

        jgen.writeEndObject();
    }
}
