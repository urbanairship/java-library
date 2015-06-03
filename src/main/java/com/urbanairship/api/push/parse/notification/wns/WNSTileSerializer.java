/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.urbanairship.api.push.model.notification.wns.WNSBinding;
import com.urbanairship.api.push.model.notification.wns.WNSTileData;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class WNSTileSerializer extends JsonSerializer<WNSTileData> {
    @Override
    public void serialize(WNSTileData tile, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (tile.getBindingCount() > 0) {
            jgen.writeArrayFieldStart("binding");
            for (WNSBinding binding : tile.getBindings()) {
                jgen.writeObject(binding);
            }
            jgen.writeEndArray();
        }

        jgen.writeEndObject();
    }
}
