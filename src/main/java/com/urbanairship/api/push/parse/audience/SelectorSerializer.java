/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.audience;

import com.urbanairship.api.push.model.audience.CompoundSelector;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.audience.ValueSelector;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;
import java.util.Map;

public class SelectorSerializer extends JsonSerializer<Selector> {
    @Override
    public void serialize(Selector s, JsonGenerator jgen, SerializerProvider provider) throws IOException {

        if (s instanceof ValueSelector) {
            ValueSelector vs = (ValueSelector)s;
            jgen.writeStartObject();
            jgen.writeStringField(vs.getType().getIdentifier(), vs.getValue());
            if (vs.getAttributes().isPresent()) {
                Map<String, String> attributes = vs.getAttributes().get();
                for (String key : attributes.keySet()) {
                    jgen.writeStringField(key, attributes.get(key));
                }
            }
            jgen.writeEndObject();

        } else if (s instanceof CompoundSelector) {
            CompoundSelector cs = (CompoundSelector)s;
            jgen.writeStartObject();
            jgen.writeArrayFieldStart(cs.getType().getIdentifier());
            for (Selector child : cs.getChildren()) {
                serialize(child, jgen, provider);
            }
            jgen.writeEndArray();
            jgen.writeEndObject();

        } else {
            jgen.writeString(s.getType().name());
        }

    }
}
