/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.templates.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.templates.model.TemplatePushPayload;

import java.io.IOException;

public class TemplatePushPayloadSerializer extends JsonSerializer<TemplatePushPayload> {

    @Override
    public void serialize(TemplatePushPayload value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeObjectField("audience", value.getAudience());
        jgen.writeObjectField("device_types", value.getDeviceTypes());
        jgen.writeObjectField("merge_data", value.getMergeData());

        jgen.writeEndObject();
    }
}
