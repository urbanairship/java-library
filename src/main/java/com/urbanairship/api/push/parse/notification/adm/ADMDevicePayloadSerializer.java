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

        if (payload.getActions().isPresent()) {
            jgen.writeObjectField("actions", payload.getActions().get());
        }

        if (payload.getIcon().isPresent()) {
            jgen.writeStringField("icon", payload.getIcon().get());
        }

        if (payload.getIconColor().isPresent()) {
            jgen.writeStringField("icon_color", payload.getIconColor().get());
        }

        if (payload.getNotificationChannel().isPresent()) {
            jgen.writeStringField("notification_channel", payload.getNotificationChannel().get());
        }

        if (payload.getNotificationTag().isPresent()) {
            jgen.writeStringField("notification_tag", payload.getNotificationTag().get());
        }

        if (payload.getSound().isPresent()) {
            jgen.writeStringField("sound", payload.getSound().get());
        }

        if (payload.getSummary().isPresent()) {
            jgen.writeStringField("summary", payload.getSummary().get());
        }

        if (payload.getTitle().isPresent()) {
            jgen.writeStringField("title", payload.getTitle().get());
        }

        if (payload.getStyle().isPresent()) {
            jgen.writeObjectField("style", payload.getStyle().get());
        }

        if (payload.getTemplate().isPresent()) {
            jgen.writeObjectField("template", payload.getTemplate().get());
        }

        jgen.writeEndObject();
    }
}
