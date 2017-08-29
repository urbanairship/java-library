/*
 * Copyright (c) 2013-2017. Urban Airship and Contributors
 */

package com.urbanairship.api.experiments.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.experiments.model.VariantPushPayload;

import java.io.IOException;

public class VariantPushPayloadSerializer extends JsonSerializer<VariantPushPayload> {

    @Override
    public void serialize(VariantPushPayload value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (value.getNotification().isPresent()) {
            jgen.writeObjectField("notification", value.getNotification().get());
        }
        if (value.getPushOptions().isPresent()) {
            jgen.writeObjectField("options", value.getPushOptions().get());
        }
        if (value.getInApp().isPresent()) {
            jgen.writeObjectField("in_app", value.getInApp().get());
        }

        jgen.writeEndObject();
    }
}
