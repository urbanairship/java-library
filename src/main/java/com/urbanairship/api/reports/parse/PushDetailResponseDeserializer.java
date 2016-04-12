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
import com.urbanairship.api.reports.model.PushDetailResponse;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class PushDetailResponseDeserializer extends JsonDeserializer<PushDetailResponse> {

    private static final FieldParserRegistry<PushDetailResponse, PushDetailResponseReader> FIELD_PARSERS =
            new MapFieldParserRegistry<PushDetailResponse, PushDetailResponseReader>(ImmutableMap.<String, FieldParser<PushDetailResponseReader>>builder()
                    .put("app_key", new FieldParser<PushDetailResponseReader>() {
                        @Override
                        public void parse(PushDetailResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readAppKey(jsonParser);
                        }
                    })
                    .put("push_id", new FieldParser<PushDetailResponseReader>() {
                        @Override
                        public void parse(PushDetailResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readPushID(jsonParser);
                        }
                    })
                    .put("created", new FieldParser<PushDetailResponseReader>() {
                        @Override
                        public void parse(PushDetailResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readCreated(jsonParser);
                        }
                    })
                    .put("push_body", new FieldParser<PushDetailResponseReader>() {
                        @Override
                        public void parse(PushDetailResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readPushBody(jsonParser);
                        }
                    })
                    .put("rich_deletions", new FieldParser<PushDetailResponseReader>() {
                        @Override
                        public void parse(PushDetailResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readRichDeletions(jsonParser);
                        }
                    })
                    .put("rich_responses", new FieldParser<PushDetailResponseReader>() {
                        @Override
                        public void parse(PushDetailResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readRichResponses(jsonParser);
                        }
                    })
                    .put("rich_sends", new FieldParser<PushDetailResponseReader>() {
                        @Override
                        public void parse(PushDetailResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readRichSends(jsonParser);
                        }
                    })
                    .put("sends", new FieldParser<PushDetailResponseReader>() {
                        @Override
                        public void parse(PushDetailResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readSends(jsonParser);
                        }
                    })
                    .put("direct_responses", new FieldParser<PushDetailResponseReader>() {
                        @Override
                        public void parse(PushDetailResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readDirectResponses(jsonParser);
                        }
                    })
                    .put("influenced_responses", new FieldParser<PushDetailResponseReader>() {
                        @Override
                        public void parse(PushDetailResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readInfluencedResponses(jsonParser);
                        }
                    })
                    .put("platforms", new FieldParser<PushDetailResponseReader>() {
                        @Override
                        public void parse(PushDetailResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readPlatforms(jsonParser);
                        }
                    })
                    .build()
            );

    private final StandardObjectDeserializer<PushDetailResponse, ?> deserializer;

    public PushDetailResponseDeserializer() {
        deserializer = new StandardObjectDeserializer<PushDetailResponse, PushDetailResponseReader>(
                FIELD_PARSERS,
                new Supplier<PushDetailResponseReader>() {
                    @Override
                    public PushDetailResponseReader get() {
                        return new PushDetailResponseReader();
                    }
                }
        );
    }

    @Override
    public PushDetailResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
