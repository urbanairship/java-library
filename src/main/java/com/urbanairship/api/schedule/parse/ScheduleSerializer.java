/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.schedule.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.schedule.model.Schedule;

import java.io.IOException;

public class ScheduleSerializer extends JsonSerializer<Schedule> {

    public static final ScheduleSerializer INSTANCE = new ScheduleSerializer();

    @Override
    public void serialize(Schedule value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (value.getBestTime().isPresent()) {
            jgen.writeObjectField("best_time", value.getBestTime().get());
        } else if (!value.getLocalTimePresent()){
                jgen.writeStringField("scheduled_time", DateFormats.DATE_FORMATTER.print(value.getScheduledTimestamp()));
            }
            else {
                jgen.writeStringField("local_scheduled_time", DateFormats.DATE_FORMATTER.print(value.getScheduledTimestamp()));
            }
        jgen.writeEndObject();
    }
}
