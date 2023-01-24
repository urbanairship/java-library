/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.audience.location;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.audience.location.SegmentDefinition;

import java.io.IOException;

public class SegmentDefinitionDeserializer extends JsonDeserializer<SegmentDefinition> {

    private static final FieldParserRegistry<SegmentDefinition, SegmentDefinitionReader> FIELD_PARSERS = new MapFieldParserRegistry<SegmentDefinition, SegmentDefinitionReader>(
            ImmutableMap.<String, FieldParser<SegmentDefinitionReader>>builder()
                    .put("display_name", (reader, parser, context) -> reader.readDisplayName(parser))
                    .put("criteria", (reader, parser, context) -> reader.readCriteria(parser))
                    .build());

    private final StandardObjectDeserializer<SegmentDefinition, ?> deserializer;

    public SegmentDefinitionDeserializer() {
        deserializer = new StandardObjectDeserializer<SegmentDefinition, SegmentDefinitionReader>(
                FIELD_PARSERS,
                () -> new SegmentDefinitionReader()
        );
    }

    @Override
    public SegmentDefinition deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
