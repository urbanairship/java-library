/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.schedule.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.schedule.model.ScheduleDetails;

import java.io.IOException;

public final class ScheduleDetailsSerializer extends JsonSerializer<ScheduleDetails> {

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
