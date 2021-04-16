package com.urbanairship.api.push.parse.notification.web;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.web.Button;
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

        if (payload.getActions().isPresent()) {
            jgen.writeObjectField("actions", payload.getActions().get());
        }

        if (payload.getWebImage().isPresent()) {
            jgen.writeObjectField("image", payload.getWebImage().get());
        }

        if (payload.getExpiry().isPresent()) {
            jgen.writeObjectField("time_to_live", payload.getExpiry().get());
        }

        if (payload.getButtons().isPresent()) {
            jgen.writeArrayFieldStart("buttons");
            for (Button button : payload.getButtons().get()) {
                jgen.writeObject(button);
            }
            jgen.writeEndArray();
        }

        if (payload.getTemplate().isPresent()) {
            jgen.writeObjectField("template", payload.getTemplate().get());
        }

        jgen.writeEndObject();
    }
}
