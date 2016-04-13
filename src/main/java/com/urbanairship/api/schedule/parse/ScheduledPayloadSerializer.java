/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.schedule.parse;

import com.urbanairship.api.schedule.model.SchedulePayload;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public final class ScheduledPayloadSerializer extends JsonSerializer<SchedulePayload> {

    public static final ScheduledPayloadSerializer INSTANCE = new ScheduledPayloadSerializer();

    private ScheduledPayloadSerializer() { }

    @Override
    public void serialize(SchedulePayload value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeObjectField("schedule", value.getSchedule());

        if(value.getName().isPresent()) {
            jgen.writeStringField("name", value.getName().get());
        }

        jgen.writeObjectField("push", value.getPushPayload());

        jgen.writeEndObject();
    }
}
