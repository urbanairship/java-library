/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.audience.location;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.audience.location.SegmentDefinition;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class SegmentDefinitionDeserializer extends JsonDeserializer<SegmentDefinition> {

    private static final FieldParserRegistry<SegmentDefinition, SegmentDefinitionReader> FIELD_PARSERS = new MapFieldParserRegistry<SegmentDefinition, SegmentDefinitionReader>(
            ImmutableMap.<String, FieldParser<SegmentDefinitionReader>>builder()
                    .put("display_name", new FieldParser<SegmentDefinitionReader>() {
                        @Override
                        public void parse(SegmentDefinitionReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                            reader.readDisplayName(parser);
                        }
                    })
                    .put("criteria", new FieldParser<SegmentDefinitionReader>() {
                        @Override
                        public void parse(SegmentDefinitionReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                            reader.readCriteria(parser);
                        }
                    })
                    .build());

    private final StandardObjectDeserializer<SegmentDefinition, ?> deserializer;

    public SegmentDefinitionDeserializer() {
        deserializer = new StandardObjectDeserializer<SegmentDefinition, SegmentDefinitionReader>(
                FIELD_PARSERS,
                new Supplier<SegmentDefinitionReader>() {
                    @Override
                    public SegmentDefinitionReader get() {
                        return new SegmentDefinitionReader();
                    }
                }
        );
    }

    @Override
    public SegmentDefinition deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
