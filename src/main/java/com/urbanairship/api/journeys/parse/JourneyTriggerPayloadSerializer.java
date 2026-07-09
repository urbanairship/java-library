package com.urbanairship.api.journeys.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.journeys.model.JourneyTriggerPayload;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JourneyTriggerPayloadSerializer extends JsonSerializer<JourneyTriggerPayload> {

    @Override
    public void serialize(JourneyTriggerPayload payload, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {
        jgen.writeStartObject();

        jgen.writeObjectField("audience", payload.getAudience());

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

        if (payload.getGlobalAttributes().isPresent()) {
            jgen.writeFieldName("global_attributes");
            jgen.writeStartObject();
            for (Map.Entry<String, Object> entry : payload.getGlobalAttributes().get().entrySet()) {
                jgen.writeObjectField(entry.getKey(), entry.getValue());
            }
            jgen.writeEndObject();
        }

        jgen.writeEndObject();
    }
}
