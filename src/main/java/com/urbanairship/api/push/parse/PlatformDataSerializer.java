/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMultimap;
import com.urbanairship.api.push.model.Platform;
import com.urbanairship.api.push.model.PlatformData;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class PlatformDataSerializer extends JsonSerializer<PlatformData> {

    @Override
    public void serialize(PlatformData payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (payload.isAll()) {
            jgen.writeString("all");
        } else {
            jgen.writeObject(payload.getPlatforms().get());
        }
    }
}
