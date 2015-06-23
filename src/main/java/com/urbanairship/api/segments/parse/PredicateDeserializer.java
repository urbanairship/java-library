/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments.parse;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.segments.model.Predicate;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;
import java.util.Map;

public class PredicateDeserializer extends JsonDeserializer<Predicate> {

    public static final PredicateDeserializer INSTANCE = new PredicateDeserializer();

    private static final Map<String, JsonDeserializer<? extends Predicate>> PREDICATE_DESERIALIZERS =
            ImmutableMap.of(
                    "tag", TagPredicateDeserializer.INSTANCE,
                    "tag_class", TagPredicateDeserializer.INSTANCE,
                    "group", TagPredicateDeserializer.INSTANCE,
                    "location", LocationPredicateDeserializer.INSTANCE
            );

    private static final String INVALID_PREDICATE = "Segment criteria predicates must be simple key value pair objects";
    private static final String UNRECOGNIZED_PREDICATE_TYPE = "Unrecognized predicate type.  Valid predicate types are - " +
            Joiner.on(", ").join(PREDICATE_DESERIALIZERS.keySet()) + ".";

    private PredicateDeserializer() {
    }

    @Override
    public Predicate deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken token = jp.getCurrentToken();
        if (token == JsonToken.START_OBJECT) {
            token = jp.nextToken();
        }

        if (token != JsonToken.FIELD_NAME) {
            throw new InvalidAudienceSegmentException(INVALID_PREDICATE);
        }

        JsonDeserializer<? extends Predicate> predicateDeserializer = PREDICATE_DESERIALIZERS.get(jp.getText());
        if (predicateDeserializer == null) {
            throw new InvalidAudienceSegmentException(UNRECOGNIZED_PREDICATE_TYPE);
        }

        jp.nextToken();
        Predicate predicate = predicateDeserializer.deserialize(jp, ctxt);
        JsonToken currentToken = jp.getCurrentToken();
        if (currentToken.equals(JsonToken.END_OBJECT)) {
            return predicate;
        } else if (jp.nextToken().equals(JsonToken.END_OBJECT)) {
            return predicate;
        } else {
            throw new InvalidAudienceSegmentException(INVALID_PREDICATE);
        }
    }
}
