package com.urbanairship.api.journeys.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.journeys.model.JourneyExitPayload;

import java.io.IOException;
import java.util.List;

public class JourneyExitPayloadSerializer extends JsonSerializer<JourneyExitPayload> {

    @Override
    public void serialize(JourneyExitPayload payload, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {
        jgen.writeStartObject();

        List<String> ids = payload.getTriggeringIds();
        if (ids.size() == 1) {
            jgen.writeStringField("triggering_id", ids.get(0));
        } else {
            jgen.writeArrayFieldStart("triggering_id");
            for (String id : ids) {
                jgen.writeString(id);
            }
            jgen.writeEndArray();
        }

        if (payload.getEntranceId().isPresent()) {
            jgen.writeStringField("entrance_id", payload.getEntranceId().get());
        }

        jgen.writeEndObject();
    }
}
