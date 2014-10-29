/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.audience.location;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.audience.location.AbsoluteDateRange;
import com.urbanairship.api.common.parse.*;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class AbsoluteDateRangeDeserializer extends JsonDeserializer<AbsoluteDateRange.Builder> {

    private static final FieldParserRegistry<AbsoluteDateRange.Builder, AbsoluteDateRangeReader> FIELD_PARSERS = new MapFieldParserRegistry<AbsoluteDateRange.Builder, AbsoluteDateRangeReader>(
        ImmutableMap.<String, FieldParser<AbsoluteDateRangeReader>>builder()
            .put("start", new FieldParser<AbsoluteDateRangeReader>() {
                @Override
                public void parse(AbsoluteDateRangeReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readStart(jsonParser);
                }
            })
            .put("end", new FieldParser<AbsoluteDateRangeReader>() {
                @Override
                public void parse(AbsoluteDateRangeReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readEnd(jsonParser);
                }
                })
            .build()
    );

    private final StandardObjectDeserializer<AbsoluteDateRange.Builder, ?> deserializer;

    public AbsoluteDateRangeDeserializer() {
        deserializer = new StandardObjectDeserializer<AbsoluteDateRange.Builder, AbsoluteDateRangeReader>(
            FIELD_PARSERS,
            new Supplier<AbsoluteDateRangeReader>() {
                @Override
                public AbsoluteDateRangeReader get() {
                    return new AbsoluteDateRangeReader();
                }
            }
        );
    }

    @Override
    public AbsoluteDateRange.Builder deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
