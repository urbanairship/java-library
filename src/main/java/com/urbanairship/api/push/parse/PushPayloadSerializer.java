/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.localization.Localization;

import java.io.IOException;
import java.util.List;

public class PushPayloadSerializer extends JsonSerializer<PushPayload> {

    @Override
    public void serialize(PushPayload payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeObjectField("audience", payload.getAudience());

        jgen.writeObjectField("device_types", payload.getDeviceTypes().getDeviceTypes().get());

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
        if (payload.getCampaigns().isPresent()) {
            jgen.writeObjectField("campaigns", payload.getCampaigns().get());
        }
        if (payload.getLocalizations().isPresent()) {
            List<Localization> localizations = payload.getLocalizations().get();

            jgen.writeArrayFieldStart("localizations");

            for (Localization localization : localizations) {
                jgen.writeObject(localization);
            }

            jgen.writeEndArray();
        }
        if (!payload.getGlobalAttributes().isEmpty()) {
            jgen.writeObjectField("global_attributes", payload.getGlobalAttributes());
        }
        jgen.writeEndObject();
    }
}
