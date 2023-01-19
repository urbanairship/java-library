/*
 * Copyright (c) 2013-2017.  Urban Airship and Contributors
 */

package com.urbanairship.api.experiments.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.experiments.model.Variant;

import java.io.IOException;

public class VariantDeserializer extends JsonDeserializer<Variant> {

    private static final FieldParserRegistry<Variant, VariantReader> FIELD_PARSERS = new MapFieldParserRegistry<Variant, VariantReader>(

            ImmutableMap.<String, FieldParser<VariantReader>>builder()
                    .put("name", (reader, jsonParser, deserializationContext) -> reader.readName(jsonParser))
                    .put("description", (reader, jsonParser, deserializationContext) -> reader.readDescription(jsonParser))
                    .put("schedule", (reader, jsonParser, deserializationContext) -> reader.readSchedule(jsonParser))
                    .put("weight", (reader, jsonParser, deserializationContext) -> reader.readWeight(jsonParser))
                    .put("push", (reader, jsonParser, deserializationContext) -> reader.readVariantPushPayload(jsonParser))
                    .build()
            );

    private final StandardObjectDeserializer<Variant, ?> deserializer;

    public VariantDeserializer() {
        deserializer = new StandardObjectDeserializer<Variant, VariantReader>(
                FIELD_PARSERS,
                () -> new VariantReader()
        );
    }

    @Override
    public Variant deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
