/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.reports.model.PerPushCounts;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

/**
 * @deprecated Marked to be removed in 2.0.0. Urban Airship stopped recommending use of these endpoints in October 2015,
 * so we are now completing their removal from our libraries.
 */
@Deprecated
public final class PerPushCountsDeserializer extends JsonDeserializer<PerPushCounts> {

    private static final FieldParserRegistry<PerPushCounts, PerPushCountsReader> FIELD_PARSERS =
            new MapFieldParserRegistry<PerPushCounts, PerPushCountsReader>(ImmutableMap.<String, FieldParser<PerPushCountsReader>>builder()
                    .put("direct_responses", new FieldParser<PerPushCountsReader>() {
                        @Override
                        public void parse(PerPushCountsReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readDirectResponses(jsonParser);
                        }
                    })
                    .put("influenced_responses", new FieldParser<PerPushCountsReader>() {
                        @Override
                        public void parse(PerPushCountsReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readInfluencedResponses(jsonParser);
                        }
                    })
                    .put("sends", new FieldParser<PerPushCountsReader>() {
                        @Override
                        public void parse(PerPushCountsReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readSends(jsonParser);
                        }
                    })
                    .build()
            );

    private final StandardObjectDeserializer<PerPushCounts, ?> deserializer;

    public PerPushCountsDeserializer() {
        deserializer = new StandardObjectDeserializer<PerPushCounts, PerPushCountsReader>(
                FIELD_PARSERS,
                new Supplier<PerPushCountsReader>() {
                    @Override
                    public PerPushCountsReader get() {
                        return new PerPushCountsReader();
                    }
                }
        );
    }

    @Override
    public PerPushCounts deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
