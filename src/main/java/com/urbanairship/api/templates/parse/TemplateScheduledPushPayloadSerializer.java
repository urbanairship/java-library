package com.urbanairship.api.templates.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.templates.model.TemplateScheduledPushPayload;

import java.io.IOException;

public class TemplateScheduledPushPayloadSerializer extends JsonSerializer<TemplateScheduledPushPayload> {
    @Override
    public void serialize(TemplateScheduledPushPayload value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeObjectField("audience", value.getAudience());
        jgen.writeObjectField("device_types", value.getDeviceTypes());
        jgen.writeObjectField("merge_data", value.getMergeData());
        jgen.writeObjectField("schedule", value.getSchedule());

        if (value.getName().isPresent()) {
            jgen.writeObjectField("name", value.getName().get());
        }

        if (value.getCampaigns().isPresent()) {
            jgen.writeObjectField("campaigns", value.getCampaigns().get());
        }

        jgen.writeEndObject();
    }
}
