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
import com.urbanairship.api.reports.model.PerPushDetailResponse;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class PerPushDetailResponseDeserializer extends JsonDeserializer<PerPushDetailResponse> {

    private static final FieldParserRegistry<PerPushDetailResponse, PerPushDetailResponseReader> FIELD_PARSERS =
            new MapFieldParserRegistry<PerPushDetailResponse, PerPushDetailResponseReader>(ImmutableMap.<String, FieldParser<PerPushDetailResponseReader>>builder()
                    .put("app_key", new FieldParser<PerPushDetailResponseReader>() {
                        @Override
                        public void parse(PerPushDetailResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readAppKey(jsonParser);
                        }
                    })
                    .put("push_id", new FieldParser<PerPushDetailResponseReader>() {
                        @Override
                        public void parse(PerPushDetailResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readPushID(jsonParser);
                        }
                    })
                    .put("created", new FieldParser<PerPushDetailResponseReader>() {
                        @Override
                        public void parse(PerPushDetailResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readCreated(jsonParser);
                        }
                    })
                    .put("push_body", new FieldParser<PerPushDetailResponseReader>() {
                        @Override
                        public void parse(PerPushDetailResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readPushBody(jsonParser);
                        }
                    })
                    .put("rich_deletions", new FieldParser<PerPushDetailResponseReader>() {
                        @Override
                        public void parse(PerPushDetailResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readRichDeletions(jsonParser);
                        }
                    })
                    .put("rich_responses", new FieldParser<PerPushDetailResponseReader>() {
                        @Override
                        public void parse(PerPushDetailResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readRichResponses(jsonParser);
                        }
                    })
                    .put("rich_sends", new FieldParser<PerPushDetailResponseReader>() {
                        @Override
                        public void parse(PerPushDetailResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readRichSends(jsonParser);
                        }
                    })
                    .put("sends", new FieldParser<PerPushDetailResponseReader>() {
                        @Override
                        public void parse(PerPushDetailResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readSends(jsonParser);
                        }
                    })
                    .put("direct_responses", new FieldParser<PerPushDetailResponseReader>() {
                        @Override
                        public void parse(PerPushDetailResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readDirectResponses(jsonParser);
                        }
                    })
                    .put("influenced_responses", new FieldParser<PerPushDetailResponseReader>() {
                        @Override
                        public void parse(PerPushDetailResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readInfluencedResponses(jsonParser);
                        }
                    })
                    .put("platforms", new FieldParser<PerPushDetailResponseReader>() {
                        @Override
                        public void parse(PerPushDetailResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readPlatforms(jsonParser);
                        }
                    })
                    .build()
            );

    private final StandardObjectDeserializer<PerPushDetailResponse, ?> deserializer;

    public PerPushDetailResponseDeserializer() {
        deserializer = new StandardObjectDeserializer<PerPushDetailResponse, PerPushDetailResponseReader>(
                FIELD_PARSERS,
                new Supplier<PerPushDetailResponseReader>() {
                    @Override
                    public PerPushDetailResponseReader get() {
                        return new PerPushDetailResponseReader();
                    }
                }
        );
    }

    @Override
    public PerPushDetailResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
