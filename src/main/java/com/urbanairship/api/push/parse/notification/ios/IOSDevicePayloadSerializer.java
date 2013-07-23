package com.urbanairship.api.push.parse.notification.ios;

import com.urbanairship.api.push.model.notification.ios.IOSDevicePayload;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import java.util.Map;
import java.io.IOException;

public class IOSDevicePayloadSerializer extends JsonSerializer<IOSDevicePayload> {
    @Override
    public void serialize(IOSDevicePayload payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (payload.getAlert().isPresent()) {
            jgen.writeStringField("alert", payload.getAlert().get());
        }

        if (payload.getSound().isPresent()) {
            jgen.writeStringField("sound", payload.getSound().get());
        }

        if (payload.getBadge().isPresent()) {
            jgen.writeObjectField("badge", payload.getBadge().get());
        }

        if (payload.getContentAvailable().isPresent()) {
            jgen.writeBooleanField("content_available", payload.getContentAvailable().get());
        }

        if (payload.getExtra().isPresent()) {
            jgen.writeObjectField("extra", payload.getExtra().get());
        }

        jgen.writeEndObject();
    }
}
