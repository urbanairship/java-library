/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments.parse;

import com.urbanairship.api.segments.model.Operator;
import com.urbanairship.api.segments.model.OperatorChild;
import com.urbanairship.api.segments.model.OperatorType;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class OperatorSerializer extends JsonSerializer<Operator> {

    public static final OperatorSerializer INSTANCE = new OperatorSerializer(PredicateSerializer.INSTANCE);

    private final PredicateSerializer predicateSerializer;

    private OperatorSerializer(PredicateSerializer predicateSerializer) {
        this.predicateSerializer = predicateSerializer;
    }

    @Override
    public void serialize(Operator value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeFieldName(value.getType().getIdentifier());

        if (value.getType() != OperatorType.NOT) {
            jgen.writeStartArray();
        }

        // Note that for the NOT operator, the Operator object itself will guarantee that there is only a single child, be it an operator or
        // a predicate, and so this loop is safe in all cases.  A bit of intimate knowledge here...but it keeps the code cleaner...
        for (OperatorChild child : value.getChildren()) {
            if (child.isPredicateChild()) {
                predicateSerializer.serialize(child.getPredicate(), jgen, provider);
            } else {
                serialize(child.getOperator(), jgen, provider);
            }
        }

        if (value.getType() != OperatorType.NOT) {
            jgen.writeEndArray();
        }

        jgen.writeEndObject();
    }
}
