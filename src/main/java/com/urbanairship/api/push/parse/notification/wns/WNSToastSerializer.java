/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.urbanairship.api.push.model.notification.wns.WNSToastData;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

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
