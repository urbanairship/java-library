/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.templates.parse;

import com.urbanairship.api.templates.model.TemplatePushPayload;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

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
