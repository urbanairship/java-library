/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.mpns;

import com.urbanairship.api.push.model.notification.mpns.MPNSDevicePayload;
import com.urbanairship.api.push.model.notification.mpns.MPNSPush;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import java.io.IOException;

public class MPNSDevicePayloadSerializer extends JsonSerializer<MPNSDevicePayload> {
    @Override
    public void serialize(MPNSDevicePayload payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (payload.getAlert().isPresent()) {
            jgen.writeObjectField("alert", payload.getAlert().get());
        }
        if (payload.getBody().isPresent()) {
            MPNSPush body = payload.getBody().get();

            if (body.getTile().isPresent()) {
                jgen.writeObjectField("tile", body.getTile().get());
            }
            if (body.getToast().isPresent()) {
                jgen.writeObjectField("toast", body.getToast().get());
            }
            jgen.writeStringField("batching_interval", body.getBatchingInterval().name().toLowerCase());
        }

        jgen.writeEndObject();
    }
}
