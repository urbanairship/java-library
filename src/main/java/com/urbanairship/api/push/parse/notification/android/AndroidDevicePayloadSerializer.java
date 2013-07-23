package com.urbanairship.api.push.parse.notification.android;

import com.urbanairship.api.push.model.notification.android.AndroidDevicePayload;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class AndroidDevicePayloadSerializer extends JsonSerializer<AndroidDevicePayload> {
    @Override
    public void serialize(AndroidDevicePayload payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (payload.getAlert().isPresent()) {
            jgen.writeStringField("alert", payload.getAlert().get());
        }

        if (payload.getCollapseKey().isPresent()) {
            jgen.writeStringField("collapse_key", payload.getCollapseKey().get());
        }

        if (payload.getTimeToLive().isPresent()) {
            jgen.writeNumberField("time_to_live", payload.getTimeToLive().get());
        }

        if (payload.getDelayWhileIdle().isPresent()) {
            jgen.writeBooleanField("delay_while_idle", payload.getDelayWhileIdle().get());
        }

        if (payload.getExtra().isPresent()) {
            jgen.writeObjectField("extra", payload.getExtra().get());
        }

        jgen.writeEndObject();
    }
}
