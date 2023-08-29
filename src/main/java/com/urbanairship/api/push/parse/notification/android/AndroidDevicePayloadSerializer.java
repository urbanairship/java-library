/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.android;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.android.AndroidDevicePayload;

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

        if (payload.getNotificationChannel().isPresent()) {
            jgen.writeStringField("notification_channel", payload.getNotificationChannel().get());
        }

        if (payload.getNotificationTag().isPresent()) {
            jgen.writeStringField("notification_tag", payload.getNotificationTag().get());
        }

        if (payload.getTimeToLive().isPresent()) {
            jgen.writeObjectField("time_to_live", payload.getTimeToLive().get());
        }

        if (payload.getDeliveryPriority().isPresent()) {
            jgen.writeStringField("delivery_priority", payload.getDeliveryPriority().get());
        }

        if (payload.getDelayWhileIdle().isPresent()) {
            jgen.writeBooleanField("delay_while_idle", payload.getDelayWhileIdle().get());
        }

        if (payload.getExtra().isPresent()) {
            jgen.writeObjectField("extra", payload.getExtra().get());
        }

        if (payload.getInteractive().isPresent()) {
            jgen.writeObjectField("interactive", payload.getInteractive().get());
        }

        if (payload.getTitle().isPresent()) {
            jgen.writeStringField("title", payload.getTitle().get());
        }

        if (payload.getLocalOnly().isPresent()) {
            jgen.writeBooleanField("local_only", payload.getLocalOnly().get());
        }

        if (payload.getWearable().isPresent()) {
            jgen.writeObjectField("wearable", payload.getWearable().get());
        }

        if (payload.getSummary().isPresent()) {
            jgen.writeStringField("summary", payload.getSummary().get());
        }

        if (payload.getSound().isPresent()) {
            jgen.writeStringField("sound", payload.getSound().get());
        }

        if (payload.getIcon().isPresent()) {
            jgen.writeStringField("icon", payload.getIcon().get());
        }

        if (payload.getIconColor().isPresent()) {
            jgen.writeStringField("icon_color", payload.getIconColor().get());
        }

        if (payload.getCategory().isPresent()) {
            jgen.writeStringField("category", payload.getCategory().get().getCategory());
        }

        if (payload.getPriority().isPresent()) {
            jgen.writeNumberField("priority", payload.getPriority().get());
        }

        if (payload.getStyle().isPresent()) {
            jgen.writeObjectField("style", payload.getStyle().get());
        }

        if (payload.getTimeToLive().isPresent()) {
            jgen.writeObjectField("time_to_live", payload.getTimeToLive().get());
        }

        if (payload.getVisibility().isPresent()) {
            jgen.writeNumberField("visibility", payload.getVisibility().get());
        }

        if (payload.getPublicNotification().isPresent()) {
            jgen.writeObjectField("public_notification", payload.getPublicNotification().get());
        }

        if (payload.getActions().isPresent()) {
            jgen.writeObjectField("actions", payload.getActions().get());
        }

        if (payload.getTemplate().isPresent()) {
            jgen.writeObjectField("template", payload.getTemplate().get());
        }

        if (payload.getAndroidLiveUpdate().isPresent()) {
            jgen.writeObjectField("live_update", payload.getAndroidLiveUpdate().get());
        }

        jgen.writeEndObject();
    }
}
