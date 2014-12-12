/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.schedule.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.schedule.model.Schedule;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public final class ScheduleDeserializer extends JsonDeserializer<Schedule> {

    private static final FieldParserRegistry<Schedule, ScheduleReader> FIELD_PARSERS = new MapFieldParserRegistry<Schedule, ScheduleReader>(
        ImmutableMap.<String, FieldParser<ScheduleReader>>builder()
            .put("scheduled_time", new FieldParser<ScheduleReader>() {
                @Override
                public void parse(ScheduleReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readScheduledTime(jsonParser);
                }
            })
            .build()
    );

    public static final ScheduleDeserializer INSTANCE = new ScheduleDeserializer();

    private final StandardObjectDeserializer<Schedule, ?> deserializer;

    public ScheduleDeserializer() {
        deserializer = new StandardObjectDeserializer<Schedule, ScheduleReader>(
            FIELD_PARSERS,
            new Supplier<ScheduleReader>() {
                @Override
                public ScheduleReader get() {
                    return new ScheduleReader();
                }
            }
        );
    }

    @Override
    public Schedule deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
