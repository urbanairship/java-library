/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments.parse;

import com.urbanairship.api.segments.model.AudienceSegment;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class AudienceSegmentSerializer extends JsonSerializer<AudienceSegment> {

    public static final AudienceSegmentSerializer INSTANCE =
            new AudienceSegmentSerializer(OperatorSerializer.INSTANCE, PredicateSerializer.INSTANCE);

    private final OperatorSerializer operatorSerializer;
    private final PredicateSerializer predicateSerializer;

    private AudienceSegmentSerializer(OperatorSerializer operatorSerializer, PredicateSerializer predicateSerializer) {
        this.operatorSerializer = operatorSerializer;
        this.predicateSerializer = predicateSerializer;
    }

    @Override
    public void serialize(AudienceSegment value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeFieldName("display_name");
        jgen.writeString(value.getDisplayName());

        jgen.writeFieldName("criteria");
        if (value.isOperatorRoot()) {
            operatorSerializer.serialize(value.getRootOperator(), jgen, provider);
        } else if (value.isPredicateRoot()) {
            predicateSerializer.serialize(value.getRootPredicate(), jgen, provider);
        }

        if (value.hasCount()) {
            jgen.writeFieldName("count");
            jgen.writeString(String.valueOf(value.getCount()));
        }

        jgen.writeEndObject();
    }
}
