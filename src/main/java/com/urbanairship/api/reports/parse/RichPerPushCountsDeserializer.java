/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.reports.model.RichPerPushCounts;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public final class RichPerPushCountsDeserializer extends JsonDeserializer<RichPerPushCounts> {

    private static final FieldParserRegistry<RichPerPushCounts, RichPerPushCountsReader> FIELD_PARSERS =
            new MapFieldParserRegistry<RichPerPushCounts, RichPerPushCountsReader>(ImmutableMap.<String, FieldParser<RichPerPushCountsReader>>builder()
                    .put("responses", new FieldParser<RichPerPushCountsReader>() {
                        @Override
                        public void parse(RichPerPushCountsReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readResponses(jsonParser);
                        }
                    })
                    .put("sends", new FieldParser<RichPerPushCountsReader>() {
                        @Override
                        public void parse(RichPerPushCountsReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readSends(jsonParser);
                        }
                    })
                    .build()
            );

    private final StandardObjectDeserializer<RichPerPushCounts, ?> deserializer;

    public RichPerPushCountsDeserializer() {
        deserializer = new StandardObjectDeserializer<RichPerPushCounts, RichPerPushCountsReader>(
                FIELD_PARSERS,
                new Supplier<RichPerPushCountsReader>() {
                    @Override
                    public RichPerPushCountsReader get() {
                        return new RichPerPushCountsReader();
                    }
                }
        );
    }

    @Override
    public RichPerPushCounts deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
