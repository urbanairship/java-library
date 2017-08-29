/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.wns.WNSAudioData;

import java.io.IOException;

public class WNSAudioSerializer extends JsonSerializer<WNSAudioData> {
    @Override
    public void serialize(WNSAudioData audio, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("sound", audio.getSound().getIdentifier());

        if (audio.getLoop().isPresent()) {
            jgen.writeBooleanField("loop", audio.getLoop().get());
        }

        jgen.writeEndObject();
    }
}
