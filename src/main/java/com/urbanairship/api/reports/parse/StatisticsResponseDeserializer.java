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
import com.urbanairship.api.reports.model.StatisticsResponse;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public final class StatisticsResponseDeserializer extends JsonDeserializer<StatisticsResponse> {

    private static final FieldParserRegistry<StatisticsResponse, StatisticsResponseReader> FIELD_PARSERS =
            new MapFieldParserRegistry<StatisticsResponse, StatisticsResponseReader>(ImmutableMap.<String, FieldParser<StatisticsResponseReader>>builder()
                    .put("start", new FieldParser<StatisticsResponseReader>() {
                        @Override
                        public void parse(StatisticsResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readStartTime(jsonParser);
                        }
                    })
                    .put("messages", new FieldParser<StatisticsResponseReader>() {
                        @Override
                        public void parse(StatisticsResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readiOSCount(jsonParser);
                        }
                    })
                    .put("bb_messages", new FieldParser<StatisticsResponseReader>() {
                        @Override
                        public void parse(StatisticsResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readBlackBerryCount(jsonParser);
                        }
                    })
                    .put("c2dm_messages", new FieldParser<StatisticsResponseReader>() {
                        @Override
                        public void parse(StatisticsResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readC2DMCount(jsonParser);
                        }
                    })
                    .put("gcm_messages", new FieldParser<StatisticsResponseReader>() {
                        @Override
                        public void parse(StatisticsResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readGCMCount(jsonParser);
                        }
                    })
                    .put("wns_messages", new FieldParser<StatisticsResponseReader>() {
                        @Override
                        public void parse(StatisticsResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readWindows8Count(jsonParser);
                        }
                    })
                    .put("mpns_messages", new FieldParser<StatisticsResponseReader>() {
                        @Override
                        public void parse(StatisticsResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readWindowsPhone8Count(jsonParser);
                        }
                    })
                    .build()
            );

    private final StandardObjectDeserializer<StatisticsResponse, ?> deserializer;

    public StatisticsResponseDeserializer() {
        deserializer = new StandardObjectDeserializer<StatisticsResponse, StatisticsResponseReader>(
                FIELD_PARSERS,
                new Supplier<StatisticsResponseReader>() {
                    @Override
                    public StatisticsResponseReader get() {
                        return new StatisticsResponseReader();
                    }
                }
        );
    }

    @Override
    public StatisticsResponse deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
