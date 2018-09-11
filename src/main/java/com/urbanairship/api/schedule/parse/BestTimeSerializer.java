package com.urbanairship.api.schedule.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.schedule.model.BestTime;

import java.io.IOException;

public class BestTimeSerializer extends JsonSerializer<BestTime> {

    public static final ScheduleSerializer INSTANCE = new ScheduleSerializer();

    @Override
    public void serialize(BestTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeStringField("send_date", DateFormats.DAYS_FORMAT.print(value.getSendDate()));

        jgen.writeEndObject();
    }
}
