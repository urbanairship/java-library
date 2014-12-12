/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments.parse;

import com.google.common.collect.Maps;
import com.urbanairship.api.segments.model.LocationPredicate;
import com.urbanairship.api.segments.model.Predicate;
import com.urbanairship.api.segments.model.TagPredicate;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;
import java.util.Map;

public class PredicateSerializer {

    public static final PredicateSerializer INSTANCE = new PredicateSerializer();

    private static final Map<Class<? extends Predicate>, JsonSerializer<? extends Predicate>> SERIALIZERS = Maps.newHashMap();

    static {
        register(TagPredicate.class, TagPredicateSerializer.INSTANCE);
        register(LocationPredicate.class, LocationPredicateSerializer.INSTANCE);
    }

    private PredicateSerializer() {
    }

    private static <T extends Predicate> void register(Class<T> clazz, JsonSerializer<T> serializer) {
        SERIALIZERS.put(clazz, serializer);
    }

    public <T extends Predicate> void serialize(T value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        @SuppressWarnings("unchecked")  // Safe because we enforce when additions made to the map
                JsonSerializer<T> jsonSerializer = (JsonSerializer<T>) SERIALIZERS.get(value.getClass());
        if (jsonSerializer == null) {
            throw new InvalidAudienceSegmentException("Unknown Predicate class " + value.getClass());
        } else {
            jsonSerializer.serialize(value, jgen, provider);
        }
    }
}
