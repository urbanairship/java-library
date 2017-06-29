/*
 * Copyright (c) 2013-2017. Urban Airship and Contributors
 */

package com.urbanairship.api.experiments.parse;

import com.urbanairship.api.experiments.model.VariantPushPayload;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public final class VariantPushPayloadSerializer extends JsonSerializer<VariantPushPayload> {

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
