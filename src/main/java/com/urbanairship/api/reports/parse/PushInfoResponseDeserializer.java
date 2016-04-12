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
import com.urbanairship.api.reports.model.PushInfoResponse;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public final class PushInfoResponseDeserializer extends JsonDeserializer<PushInfoResponse> {

    public static final FieldParserRegistry<PushInfoResponse, PushInfoResponseReader> FIELD_PARSER =
            new MapFieldParserRegistry<PushInfoResponse, PushInfoResponseReader>(
                    ImmutableMap.<String, FieldParser<PushInfoResponseReader>>builder()
                            .put("push_uuid", new FieldParser<PushInfoResponseReader>() {
                                @Override
                                public void parse(PushInfoResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readPushUUID(jsonParser);
                                }
                            })
                            .put("direct_responses", new FieldParser<PushInfoResponseReader>() {
                                @Override
                                public void parse(PushInfoResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readDirectResponses(jsonParser);
                                }
                            })
                            .put("sends", new FieldParser<PushInfoResponseReader>() {
                                @Override
                                public void parse(PushInfoResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readSends(jsonParser);
                                }
                            })
                            .put("push_type", new FieldParser<PushInfoResponseReader>() {
                                @Override
                                public void parse(PushInfoResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readPushType(jsonParser);
                                }
                            })
                            .put("push_time", new FieldParser<PushInfoResponseReader>() {
                                @Override
                                public void parse(PushInfoResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readPushTime(jsonParser);
                                }
                            })
                            .put("group_id", new FieldParser<PushInfoResponseReader>() {
                                @Override
                                public void parse(PushInfoResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readGroupID(jsonParser);
                                }
                            })
                            .build()
            );

    private final StandardObjectDeserializer<PushInfoResponse, ?> deserializer;

    public PushInfoResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<PushInfoResponse, PushInfoResponseReader>(
                FIELD_PARSER,
                new Supplier<PushInfoResponseReader>() {
                    @Override
                    public PushInfoResponseReader get() {
                        return new PushInfoResponseReader();
                    }
                }
        );
    }

    @Override
    public PushInfoResponse deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }

}
