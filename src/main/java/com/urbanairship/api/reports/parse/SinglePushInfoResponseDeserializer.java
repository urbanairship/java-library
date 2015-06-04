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
import com.urbanairship.api.reports.model.SinglePushInfoResponse;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public final class SinglePushInfoResponseDeserializer extends JsonDeserializer<SinglePushInfoResponse> {

    public static final FieldParserRegistry<SinglePushInfoResponse, SinglePushInfoResponseReader> FIELD_PARSER =
            new MapFieldParserRegistry<SinglePushInfoResponse, SinglePushInfoResponseReader>(
                    ImmutableMap.<String, FieldParser<SinglePushInfoResponseReader>>builder()
                            .put("push_uuid", new FieldParser<SinglePushInfoResponseReader>() {
                                @Override
                                public void parse(SinglePushInfoResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readPushUUID(jsonParser);
                                }
                            })
                            .put("direct_responses", new FieldParser<SinglePushInfoResponseReader>() {
                                @Override
                                public void parse(SinglePushInfoResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readDirectResponses(jsonParser);
                                }
                            })
                            .put("sends", new FieldParser<SinglePushInfoResponseReader>() {
                                @Override
                                public void parse(SinglePushInfoResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readSends(jsonParser);
                                }
                            })
                            .put("push_type", new FieldParser<SinglePushInfoResponseReader>() {
                                @Override
                                public void parse(SinglePushInfoResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readPushType(jsonParser);
                                }
                            })
                            .put("push_time", new FieldParser<SinglePushInfoResponseReader>() {
                                @Override
                                public void parse(SinglePushInfoResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readPushTime(jsonParser);
                                }
                            })
                            .put("group_id", new FieldParser<SinglePushInfoResponseReader>() {
                                @Override
                                public void parse(SinglePushInfoResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readGroupID(jsonParser);
                                }
                            })
                            .build()
            );

    private final StandardObjectDeserializer<SinglePushInfoResponse, ?> deserializer;

    public SinglePushInfoResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<SinglePushInfoResponse, SinglePushInfoResponseReader>(
                FIELD_PARSER,
                new Supplier<SinglePushInfoResponseReader>() {
                    @Override
                    public SinglePushInfoResponseReader get() {
                        return new SinglePushInfoResponseReader();
                    }
                }
        );
    }

    @Override
    public SinglePushInfoResponse deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }

}
