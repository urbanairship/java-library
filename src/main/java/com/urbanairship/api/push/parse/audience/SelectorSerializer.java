/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.audience;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.audience.CompoundSelector;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.audience.SelectorType;
import com.urbanairship.api.push.model.audience.ValueSelector;
import com.urbanairship.api.push.model.audience.location.LocationSelector;

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
            if (cs.getType() == SelectorType.NOT) {
                jgen.writeObjectField("not", cs.getChildren().iterator().next());
            } else {
                jgen.writeArrayFieldStart(cs.getType().getIdentifier());
                for (Selector child : cs.getChildren()) {
                    serialize(child, jgen, provider);
                }
                jgen.writeEndArray();
            }
            jgen.writeEndObject();
        } else if (s instanceof LocationSelector) {
            LocationSelector ls = (LocationSelector)s;
            jgen.writeStartObject();
            jgen.writeObjectField("location", s);
            jgen.writeEndObject();
        } else {
            jgen.writeString(s.getType().name());
        }

    }
}
