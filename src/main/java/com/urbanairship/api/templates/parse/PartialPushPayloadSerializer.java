/*
 * Copyright (c) 2013-2016. Urban Airship and Contributors
 */

package com.urbanairship.api.templates.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.templates.model.PartialPushPayload;

import java.io.IOException;

public final class PartialPushPayloadSerializer extends JsonSerializer<PartialPushPayload> {

    @Override
    public void serialize(PartialPushPayload value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (value.getNotification().isPresent()) {
            jgen.writeObjectField("notification", value.getNotification().get());
        }
        if (value.getPushOptions().isPresent()) {
            jgen.writeObjectField("options", value.getPushOptions().get());
        }
        if (value.getRichPushMessage().isPresent()) {
            jgen.writeObjectField("message", value.getRichPushMessage().get());
        }
        if (value.getInApp().isPresent()) {
            jgen.writeObjectField("in_app", value.getInApp().get());
        }

        jgen.writeEndObject();
    }
}
