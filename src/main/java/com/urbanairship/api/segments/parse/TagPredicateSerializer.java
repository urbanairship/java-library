/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments.parse;


import com.urbanairship.api.segments.model.TagPredicate;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class TagPredicateSerializer extends JsonSerializer<TagPredicate> {

    public static final TagPredicateSerializer INSTANCE = new TagPredicateSerializer();

    private TagPredicateSerializer() {
    }

    @Override
    public void serialize(TagPredicate value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeFieldName("tag");
        jgen.writeString(value.getTag());
        if (!value.isDefaultClass() && value.getTagClass().isPresent()) {
            jgen.writeFieldName("tag_class");
            jgen.writeString(value.getTagClass().get());
        }
        if (value.getTagGroup().isPresent()) {
            jgen.writeFieldName("group");
            jgen.writeString(value.getTagGroup().get());
        }
        jgen.writeEndObject();
    }
}
