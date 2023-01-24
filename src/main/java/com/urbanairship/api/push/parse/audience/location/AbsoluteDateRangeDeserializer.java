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
import com.urbanairship.api.push.model.audience.location.AbsoluteDateRange;

import java.io.IOException;

public class AbsoluteDateRangeDeserializer extends JsonDeserializer<AbsoluteDateRange.Builder> {

    private static final FieldParserRegistry<AbsoluteDateRange.Builder, AbsoluteDateRangeReader> FIELD_PARSERS = new MapFieldParserRegistry<AbsoluteDateRange.Builder, AbsoluteDateRangeReader>(
        ImmutableMap.<String, FieldParser<AbsoluteDateRangeReader>>builder()
            .put("start", (reader, jsonParser, deserializationContext) -> reader.readStart(jsonParser))
            .put("end", (reader, jsonParser, deserializationContext) -> reader.readEnd(jsonParser))
            .build()
    );

    private final StandardObjectDeserializer<AbsoluteDateRange.Builder, ?> deserializer;

    public AbsoluteDateRangeDeserializer() {
        deserializer = new StandardObjectDeserializer<AbsoluteDateRange.Builder, AbsoluteDateRangeReader>(
            FIELD_PARSERS,
                AbsoluteDateRangeReader::new
        );
    }

    @Override
    public AbsoluteDateRange.Builder deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
