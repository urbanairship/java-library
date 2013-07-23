package com.urbanairship.api.schedule.parse;

import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.schedule.model.Schedule;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class ScheduleSerializer extends JsonSerializer<Schedule> {

    public static final ScheduleSerializer INSTANCE = new ScheduleSerializer();

    private ScheduleSerializer() { }

    @Override
    public void serialize(Schedule value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeStringField("scheduled_time", DateFormats.DATE_FORMATTER.print(value.getScheduledTimestamp()));

        jgen.writeEndObject();
    }
}
