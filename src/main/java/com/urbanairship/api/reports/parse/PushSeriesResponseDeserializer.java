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
import com.urbanairship.api.reports.model.PushSeriesResponse;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class PushSeriesResponseDeserializer extends JsonDeserializer<PushSeriesResponse> {

    private static final FieldParserRegistry<PushSeriesResponse, PushSeriesResponseReader> FIELD_PARSERS =
            new MapFieldParserRegistry<PushSeriesResponse, PushSeriesResponseReader>(ImmutableMap.<String, FieldParser<PushSeriesResponseReader>>builder()
                    .put("app_key", new FieldParser<PushSeriesResponseReader>() {
                        @Override
                        public void parse(PushSeriesResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readAppKey(jsonParser);
                        }
                    })
                    .put("push_id", new FieldParser<PushSeriesResponseReader>() {
                        @Override
                        public void parse(PushSeriesResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readPushID(jsonParser);
                        }
                    })
                    .put("start", new FieldParser<PushSeriesResponseReader>() {
                        @Override
                        public void parse(PushSeriesResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readStart(jsonParser);
                        }
                    })
                    .put("end", new FieldParser<PushSeriesResponseReader>() {
                        @Override
                        public void parse(PushSeriesResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readEnd(jsonParser);
                        }
                    })
                    .put("precision", new FieldParser<PushSeriesResponseReader>() {
                        @Override
                        public void parse(PushSeriesResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readPrecision(jsonParser);
                        }
                    })
                    .put("counts", new FieldParser<PushSeriesResponseReader>() {
                        @Override
                        public void parse(PushSeriesResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readCounts(jsonParser);
                        }
                    })
                    .build()
            );

    private final StandardObjectDeserializer<PushSeriesResponse, ?> deserializer;

    public PushSeriesResponseDeserializer() {
        deserializer = new StandardObjectDeserializer<PushSeriesResponse, PushSeriesResponseReader>(
                FIELD_PARSERS,
                new Supplier<PushSeriesResponseReader>() {
                    @Override
                    public PushSeriesResponseReader get() {
                        return new PushSeriesResponseReader();
                    }
                }
        );
    }

    @Override
    public PushSeriesResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
