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
import com.urbanairship.api.schedule.model.ScheduleResponse;

import java.io.IOException;

/*
Deserializers create a mapping between Jackson and an object. This abstracts all
the boilerplate necessary for Jackson stream parsing, which is essentially what
 we're doing. This will be a lot cleaner when lambda's come down.
 If you're using Intellij, be sure and toggle open the code that's
 been collapsed.
 */
public final class ScheduleResponseDeserializer extends JsonDeserializer<ScheduleResponse> {

    private static final FieldParserRegistry<ScheduleResponse, ScheduleResponseReader> FIELD_PARSER =
            new MapFieldParserRegistry<ScheduleResponse, ScheduleResponseReader>(
                    ImmutableMap.<String, FieldParser<ScheduleResponseReader>>builder()
                            .put("ok", (reader, jsonParser, deserializationContext) -> reader.readOk(jsonParser))
                            .put("operation_id", (reader, jsonParser, deserializationContext) -> reader.readOperationId(jsonParser))
                            .put("schedule_urls", (reader, jsonParser, deserializationContext) -> reader.readScheduleUrls(jsonParser))
                            .put("schedule_ids", (reader, jsonParser, deserializationContext) -> reader.readScheduleIds(jsonParser))
                            .put("schedules", (reader, jsonParser, deserializationContext) -> reader.readSchedulePayloads(jsonParser))
                            .put("error", (reader, jsonParser, deserializationContext) -> reader.readError(jsonParser))
                            .put("details", (reader, jsonParser, deserializationContext) -> reader.readErrorDetails(jsonParser))
                            .build()
            );

    private final StandardObjectDeserializer<ScheduleResponse, ?> deserializer;

    public ScheduleResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<ScheduleResponse, ScheduleResponseReader>(
                FIELD_PARSER,
                () -> new ScheduleResponseReader()
        );
    }

    @Override
    public ScheduleResponse deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
