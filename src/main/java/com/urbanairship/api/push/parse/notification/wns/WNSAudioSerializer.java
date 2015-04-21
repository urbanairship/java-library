/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.urbanairship.api.push.model.notification.wns.WNSAudioData;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

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
