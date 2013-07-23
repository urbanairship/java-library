/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.schedule.parse;

import com.urbanairship.api.schedule.model.ScheduleDetails;
import com.urbanairship.api.schedule.model.SchedulePayload;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class ScheduleDetailsSerializer extends JsonSerializer<ScheduleDetails> {

    public static final ScheduleDetailsSerializer INSTANCE = new ScheduleDetailsSerializer();

    private ScheduleDetailsSerializer() { }

    @Override
    public void serialize(ScheduleDetails value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeObjectField("href", value.getJobId());

        jgen.writeObjectField("schedule", value.getSchedulePayload().getSchedule());

        if(value.getSchedulePayload().getName().isPresent()) {
            jgen.writeStringField("name",value.getSchedulePayload().getName().get());
        }

        jgen.writeObjectField("push", value.getSchedulePayload().getPushPayload());

        jgen.writeEndObject();
    }
}
