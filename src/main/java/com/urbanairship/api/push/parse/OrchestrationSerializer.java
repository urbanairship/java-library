package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.Orchestration;

import java.io.IOException;

public class OrchestrationSerializer extends JsonSerializer<Orchestration> {
    @Override
    public void serialize(Orchestration orchestration, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeStringField("type", orchestration.getOrchestrationType().getOrchestrationType());

        if (!orchestration.getOrchestrationChannelPriority().isEmpty()) {
            jgen.writeObjectField("channel_priority", orchestration.getOrchestrationChannelPriority());
        }

        jgen.writeEndObject();
    }
}
