/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.ios;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.ios.IOSDevicePayload;
import com.urbanairship.api.push.model.notification.ios.IOSSoundData;

import java.io.IOException;

public class IOSDevicePayloadSerializer extends JsonSerializer<IOSDevicePayload> {
    @Override
    public void serialize(IOSDevicePayload payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (payload.getAlertData().isPresent()) {
            jgen.writeObjectField("alert", payload.getAlertData().get());
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

        if (payload.getExpiry().isPresent()) {
            jgen.writeObjectField("expiry", payload.getExpiry().get());
        }

        if (payload.getPriority().isPresent()) {
            jgen.writeNumberField("priority", payload.getPriority().get());
        }

        if (payload.getCategory().isPresent()) {
            jgen.writeStringField("category", payload.getCategory().get());
        }

        if (payload.getInteractive().isPresent()) {
            jgen.writeObjectField("interactive", payload.getInteractive().get());
        }

        if (payload.getTitle().isPresent()) {
            jgen.writeStringField("title", payload.getTitle().get());
        }

        if (payload.getSubtitle().isPresent()) {
            jgen.writeStringField("subtitle", payload.getSubtitle().get());
        }

        if (payload.getMutableContent().isPresent()) {
            jgen.writeBooleanField("mutable_content", payload.getMutableContent().get());
        }

        if (payload.getSound().isPresent()) {
            jgen.writeObjectField("sound", payload.getSound().get());
        }

        if (payload.getMediaAttachment().isPresent()) {
            jgen.writeObjectField("media_attachment", payload.getMediaAttachment().get());
        }

        if (payload.getCollapseId().isPresent()) {
            jgen.writeStringField("collapse_id", payload.getCollapseId().get());
        }

        if (payload.getThreadId().isPresent()) {
            jgen.writeStringField("thread_id", payload.getThreadId().get());
        }

        if (payload.getActions().isPresent()) {
            jgen.writeObjectField("actions", payload.getActions().get());
        }

        if (payload.getTargetContentId().isPresent()) {
            jgen.writeStringField("target_content_id", payload.getTargetContentId().get());
        }

        if (payload.getIosTemplate().isPresent()) {
            jgen.writeObjectField("template", payload.getIosTemplate().get());
        }

        if (payload.getIosInterruptionLevel().isPresent()) {
            jgen.writeStringField("interruption_level", payload.getIosInterruptionLevel().get().getInterruptionLevel());
        }

        if (payload.getRelevanceScore().isPresent()) {
            jgen.writeNumberField("relevance_score", payload.getRelevanceScore().get());
        }

        jgen.writeEndObject();
    }
}
