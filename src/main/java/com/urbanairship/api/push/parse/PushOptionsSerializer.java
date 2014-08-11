package com.urbanairship.api.push.parse;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMultimap;
import com.urbanairship.api.push.model.Platform;
import com.urbanairship.api.push.model.PushOptions;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class PushOptionsSerializer extends JsonSerializer<PushOptions> {

    @Override
    public void serialize(PushOptions payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeEndObject();
    }
}
