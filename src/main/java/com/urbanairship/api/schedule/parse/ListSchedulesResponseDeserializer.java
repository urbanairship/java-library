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
                            .put("ok", (reader, jsonParser, deserializationContext) -> reader.readOk(jsonParser))
                            .put("count", (reader, jsonParser, deserializationContext) -> reader.readCount(jsonParser))
                            .put("total_count", (reader, jsonParser, deserializationContext) -> reader.readTotalCount(jsonParser))
                            .put("next_page", (reader, jsonParser, deserializationContext) -> reader.readNextPage(jsonParser))
                            .put("schedules", (reader, jsonParser, deserializationContext) -> reader.readListScheduleResponse(jsonParser))
                            .put("error", (reader, jsonParser, deserializationContext) -> reader.readError(jsonParser))
                            .put("details", (reader, jsonParser, deserializationContext) -> reader.readErrorDetails(jsonParser))
                            .build()
            );

    private final StandardObjectDeserializer<ListAllSchedulesResponse, ?> deserializer;

    public ListSchedulesResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<ListAllSchedulesResponse, ListSchedulesResponseReader>(
                FIELD_PARSER,
                () -> new ListSchedulesResponseReader()
        );
    }

    @Override
    public ListAllSchedulesResponse deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
