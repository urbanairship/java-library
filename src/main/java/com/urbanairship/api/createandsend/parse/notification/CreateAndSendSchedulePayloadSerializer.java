package com.urbanairship.api.createandsend.parse.notification;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.createandsend.model.notification.CreateAndSendSchedulePayload;

import java.io.IOException;

public class CreateAndSendSchedulePayloadSerializer extends JsonSerializer<CreateAndSendSchedulePayload> {
    @Override
    public void serialize(CreateAndSendSchedulePayload payload, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
        jgen.writeStartObject();

        jgen.writeObjectFieldStart("schedule");
        jgen.writePOJOField("scheduled_time", payload.getScheduleTime());
        jgen.writeEndObject();

        if (payload.getName().isPresent()) {
            jgen.writeStringField("name", payload.getName().get());
        }        
        
        jgen.writeObjectField("push", payload.getPayload());

        jgen.writeEndObject();
    }
}
