package com.urbanairship.api.push.parse.notification.web;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.web.WebDevicePayload;

import java.io.IOException;

public class WebDevicePayloadSerializer extends JsonSerializer<WebDevicePayload> {
    @Override
    public void serialize(WebDevicePayload payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (payload.getAlert().isPresent()) {
            jgen.writeStringField("alert", payload.getAlert().get());
        }

        if (payload.getExtra().isPresent()) {
            jgen.writeObjectField("extra", payload.getExtra().get());
        }

        if (payload.getWebIcon().isPresent()) {
            jgen.writeObjectField("icon", payload.getWebIcon().get());
        }

        if (payload.getTitle().isPresent()) {
            jgen.writeStringField("title", payload.getTitle().get());
        }

        if (payload.getRequireInteraction().isPresent()) {
            jgen.writeObjectField("require_interaction", payload.getRequireInteraction().get());
        }

        jgen.writeEndObject();
    }
}
