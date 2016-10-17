/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.android;

import com.urbanairship.api.push.model.notification.android.Wearable;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class WearableSerializer extends JsonSerializer<Wearable> {
    @Override
    public void serialize(Wearable payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        if (payload.getBackgroundImage().isPresent()) {
            jgen.writeStringField("background_image", payload.getBackgroundImage().get());
        }
        if (payload.getInteractive().isPresent()) {
            jgen.writeObjectField("interactive", payload.getInteractive().get());
        }
        if (payload.getExtraPages().isPresent()) {
            jgen.writeObjectField("extra_pages", payload.getExtraPages().get());
        }
        jgen.writeEndObject();
    }
}
