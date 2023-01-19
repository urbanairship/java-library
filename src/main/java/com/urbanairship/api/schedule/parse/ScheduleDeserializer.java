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
import com.urbanairship.api.schedule.model.Schedule;

import java.io.IOException;

public final class ScheduleDeserializer extends JsonDeserializer<Schedule> {

    private static final FieldParserRegistry<Schedule, ScheduleReader> FIELD_PARSERS = new MapFieldParserRegistry<Schedule, ScheduleReader>(
        ImmutableMap.<String, FieldParser<ScheduleReader>>builder()
            .put("scheduled_time", (reader, jsonParser, deserializationContext) -> reader.readScheduledTime(jsonParser))
            .put("local_scheduled_time", (reader, jsonParser, deserializationContext) -> reader.readScheduledTime(jsonParser))
            .put("best_time", (reader, jsonParser, deserializationContext) -> reader.readBestTime(jsonParser))
            .build()
    );

    public static final ScheduleDeserializer INSTANCE = new ScheduleDeserializer();

    private final StandardObjectDeserializer<Schedule, ?> deserializer;

    public ScheduleDeserializer() {
        deserializer = new StandardObjectDeserializer<Schedule, ScheduleReader>(
            FIELD_PARSERS,
                () -> new ScheduleReader()
        );
    }

    @Override
    public Schedule deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
