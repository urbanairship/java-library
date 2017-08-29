/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.wns.WNSBinding;
import com.urbanairship.api.push.model.notification.wns.WNSTileData;

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
