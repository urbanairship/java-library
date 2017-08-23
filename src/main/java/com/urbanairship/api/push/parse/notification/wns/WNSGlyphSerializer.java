/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.wns.WNSBadgeData;

import java.io.IOException;

public class WNSGlyphSerializer extends JsonSerializer<WNSBadgeData.Glyph> {
    @Override
    public void serialize(WNSBadgeData.Glyph glyph, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeString(glyph.getIdentifier());
    }
}
