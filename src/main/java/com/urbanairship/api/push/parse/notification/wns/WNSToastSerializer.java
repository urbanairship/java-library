/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.wns.WNSToastData;

import java.io.IOException;

public class WNSToastSerializer extends JsonSerializer<WNSToastData> {
    @Override
    public void serialize(WNSToastData toast, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeObjectField("binding", toast.getBinding());

        if (toast.getDuration().isPresent()) {
            jgen.writeStringField("duration", toast.getDuration().get().getIdentifier());
        }

        if (toast.getAudio().isPresent()) {
            jgen.writeObjectField("audio", toast.getAudio().get());
        }

        jgen.writeEndObject();
    }
}
