/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.urbanairship.api.push.model.notification.wns.WNSAudioData;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class WNSSoundSerializer extends JsonSerializer<WNSAudioData.Sound> {
    @Override
    public void serialize(WNSAudioData.Sound sound, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeString(sound.getIdentifier());
    }
}
