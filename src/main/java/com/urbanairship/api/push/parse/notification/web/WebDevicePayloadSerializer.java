package com.urbanairship.api.push.parse.notification.web;

import com.urbanairship.api.push.model.notification.web.WebDevicePayload;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

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

        jgen.writeEndObject();
    }
}
