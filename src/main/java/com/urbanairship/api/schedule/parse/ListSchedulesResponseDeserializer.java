/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.schedule.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.schedule.model.ListAllSchedulesResponse;

import java.io.IOException;

public final class ListSchedulesResponseDeserializer extends JsonDeserializer<ListAllSchedulesResponse> {

    private static final FieldParserRegistry<ListAllSchedulesResponse, ListSchedulesResponseReader> FIELD_PARSER =
            new MapFieldParserRegistry<ListAllSchedulesResponse, ListSchedulesResponseReader>(
                    ImmutableMap.<String, FieldParser<ListSchedulesResponseReader>>builder()
                            .put("ok", new FieldParser<ListSchedulesResponseReader>() {
                                @Override
                                public void parse(ListSchedulesResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readOk(jsonParser);
                                }
                            })
                            .put("count", new FieldParser<ListSchedulesResponseReader>() {
                                @Override
                                public void parse(ListSchedulesResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readCount(jsonParser);
                                }
                            })
                            .put("total_count", new FieldParser<ListSchedulesResponseReader>() {
                                @Override
                                public void parse(ListSchedulesResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readTotalCount(jsonParser);
                                }
                            })
                            .put("next_page", new FieldParser<ListSchedulesResponseReader>() {
                                @Override
                                public void parse(ListSchedulesResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readNextPage(jsonParser);
                                }
                            })
                            .put("schedules", new FieldParser<ListSchedulesResponseReader>() {
                                @Override
                                public void parse(ListSchedulesResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readListScheduleResponse(jsonParser);
                                }
                            })
                            .put("error", new FieldParser<ListSchedulesResponseReader>() {
                                @Override
                                public void parse(ListSchedulesResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readError(jsonParser);
                                }
                            })
                            .put("details", new FieldParser<ListSchedulesResponseReader>() {
                                @Override
                                public void parse(ListSchedulesResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readErrorDetails(jsonParser);
                                }
                            })
                            .build()
            );

    private final StandardObjectDeserializer<ListAllSchedulesResponse, ?> deserializer;

    public ListSchedulesResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<ListAllSchedulesResponse, ListSchedulesResponseReader>(
                FIELD_PARSER,
                new Supplier<ListSchedulesResponseReader>() {
                    @Override
                    public ListSchedulesResponseReader get() {
                        return new ListSchedulesResponseReader();
                    }
                }
        );
    }

    @Override
    public ListAllSchedulesResponse deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
