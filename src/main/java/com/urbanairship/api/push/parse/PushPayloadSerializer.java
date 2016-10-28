/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.urbanairship.api.push.model.PushPayload;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class PushPayloadSerializer extends JsonSerializer<PushPayload> {

    @Override
    public void serialize(PushPayload payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeObjectField("audience", payload.getAudience());
        if (payload.getDeviceTypes().isAll()) {
            jgen.writeStringField("device_types", "all");
        } else {
            jgen.writeObjectField("device_types", payload.getDeviceTypes().getDeviceTypes().get());
        }
        if (payload.getNotification().isPresent()) {
            jgen.writeObjectField("notification", payload.getNotification().get());
        }
        if (payload.getMessage().isPresent()) {
            jgen.writeObjectField("message", payload.getMessage().get());
        }
        if (payload.getPushOptions().isPresent()) {
            jgen.writeObjectField("options", payload.getPushOptions().get());
        }
        if (payload.getInApp().isPresent()) {
            jgen.writeObjectField("in_app", payload.getInApp().get());
        }

        jgen.writeEndObject();
    }
}
