/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.notification.wns.WNSAudioData;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class WNSSoundDeserializer extends JsonDeserializer<WNSAudioData.Sound> {
    public static final WNSSoundDeserializer INSTANCE = new WNSSoundDeserializer();
    @Override
    public WNSAudioData.Sound deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String value = jp.getText();
        WNSAudioData.Sound sound = WNSAudioData.Sound.get(value);
        if (sound == null) {
            APIParsingException.raise("Unrecognized WNS toast sound " + value, jp);
        }
        return sound;
    }
}
