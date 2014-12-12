/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.*;
import com.urbanairship.api.reports.model.PlatformCounts;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class PlatformCountsDeserializer extends JsonDeserializer<PlatformCounts> {

    private static final FieldParserRegistry<PlatformCounts, PlatformCountsReader> FIELD_PARSERS =
            new MapFieldParserRegistry<PlatformCounts, PlatformCountsReader>(ImmutableMap.<String, FieldParser<PlatformCountsReader>>builder()
            .put("push_platforms", new FieldParser<PlatformCountsReader>() {
                @Override
                public void parse(PlatformCountsReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readPlatformCounts(jsonParser);
                }
            })
            .put("rich_push_platforms", new FieldParser<PlatformCountsReader>() {
                @Override
                public void parse(PlatformCountsReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readRichPlatformCounts(jsonParser);
                }
            })
            .put("time", new FieldParser<PlatformCountsReader>() {
                @Override
                public void parse(PlatformCountsReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readTime(jsonParser);
                }
            })
            .build()
    );

    private final StandardObjectDeserializer<PlatformCounts, ?> deserializer;

    public PlatformCountsDeserializer() {
        deserializer = new StandardObjectDeserializer<PlatformCounts, PlatformCountsReader>(
                FIELD_PARSERS,
                new Supplier<PlatformCountsReader>() {
                    @Override
                    public PlatformCountsReader get() {
                        return new PlatformCountsReader();
                    }
                }
        );
    }

    @Override
    public PlatformCounts deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
