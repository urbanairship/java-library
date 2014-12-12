/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.adm;

import com.urbanairship.api.push.model.notification.adm.ADMDevicePayload;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class ADMDevicePayloadSerializer extends JsonSerializer<ADMDevicePayload> {
    @Override
    public void serialize(ADMDevicePayload payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (payload.getAlert().isPresent()) {
            jgen.writeStringField("alert", payload.getAlert().get());
        }

        if (payload.getConsolidationKey().isPresent()) {
            jgen.writeStringField("consolidation_key", payload.getConsolidationKey().get());
        }

        if (payload.getExpiresAfter().isPresent()) {
            jgen.writeNumberField("expires_after", payload.getExpiresAfter().get());
        }

        if (payload.getExtra().isPresent()) {
            jgen.writeObjectField("extra", payload.getExtra().get());
        }

        jgen.writeEndObject();
    }
}
