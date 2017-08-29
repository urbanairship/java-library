/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.adm;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.adm.ADMDevicePayload;

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
            jgen.writeObjectField("expires_after", payload.getExpiresAfter().get());
        }

        if (payload.getExtra().isPresent()) {
            jgen.writeObjectField("extra", payload.getExtra().get());
        }

        if (payload.getInteractive().isPresent()) {
            jgen.writeObjectField("interactive", payload.getInteractive().get());
        }

        jgen.writeEndObject();
    }
}
