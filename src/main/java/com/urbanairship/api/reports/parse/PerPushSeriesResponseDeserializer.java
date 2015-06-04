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
import com.urbanairship.api.reports.model.PerPushSeriesResponse;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class PerPushSeriesResponseDeserializer extends JsonDeserializer<PerPushSeriesResponse> {

    private static final FieldParserRegistry<PerPushSeriesResponse, PerPushSeriesResponseReader> FIELD_PARSERS =
            new MapFieldParserRegistry<PerPushSeriesResponse, PerPushSeriesResponseReader>(ImmutableMap.<String, FieldParser<PerPushSeriesResponseReader>>builder()
                    .put("app_key", new FieldParser<PerPushSeriesResponseReader>() {
                        @Override
                        public void parse(PerPushSeriesResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readAppKey(jsonParser);
                        }
                    })
                    .put("push_id", new FieldParser<PerPushSeriesResponseReader>() {
                        @Override
                        public void parse(PerPushSeriesResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readPushID(jsonParser);
                        }
                    })
                    .put("start", new FieldParser<PerPushSeriesResponseReader>() {
                        @Override
                        public void parse(PerPushSeriesResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readStart(jsonParser);
                        }
                    })
                    .put("end", new FieldParser<PerPushSeriesResponseReader>() {
                        @Override
                        public void parse(PerPushSeriesResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readEnd(jsonParser);
                        }
                    })
                    .put("precision", new FieldParser<PerPushSeriesResponseReader>() {
                        @Override
                        public void parse(PerPushSeriesResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readPrecision(jsonParser);
                        }
                    })
                    .put("counts", new FieldParser<PerPushSeriesResponseReader>() {
                        @Override
                        public void parse(PerPushSeriesResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readCounts(jsonParser);
                        }
                    })
                    .build()
            );

    private final StandardObjectDeserializer<PerPushSeriesResponse, ?> deserializer;

    public PerPushSeriesResponseDeserializer() {
        deserializer = new StandardObjectDeserializer<PerPushSeriesResponse, PerPushSeriesResponseReader>(
                FIELD_PARSERS,
                new Supplier<PerPushSeriesResponseReader>() {
                    @Override
                    public PerPushSeriesResponseReader get() {
                        return new PerPushSeriesResponseReader();
                    }
                }
        );
    }

    @Override
    public PerPushSeriesResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
