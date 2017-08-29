/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.wns.WNSBadgeData;

import java.io.IOException;

public class WNSBadgeSerializer extends JsonSerializer<WNSBadgeData> {
    @Override
    public void serialize(WNSBadgeData badge, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (badge.getValue().isPresent()) {
            jgen.writeNumberField("value", badge.getValue().get());
        }

        if (badge.getGlyph().isPresent()) {
            jgen.writeStringField("glyph", badge.getGlyph().get().getIdentifier());
        }

        jgen.writeEndObject();
    }
}
