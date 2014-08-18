/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.blackberry;

import com.urbanairship.api.push.model.notification.blackberry.BlackberryDevicePayload;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class BlackberryDevicePayloadSerializer extends JsonSerializer<BlackberryDevicePayload> {
    @Override
    public void serialize(BlackberryDevicePayload payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (payload.getAlert().isPresent()) {
            jgen.writeStringField("alert", payload.getAlert().get());
        }
        if (payload.getBody().isPresent()) {
            jgen.writeStringField("body", payload.getBody().get());
        }
        if (payload.getContentType().isPresent()) {
            jgen.writeObjectField("content_type", payload.getContentType().get());
        }

        jgen.writeEndObject();
    }
}
