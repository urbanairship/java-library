/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.urbanairship.api.push.model.notification.wns.WNSBadgeData;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class WNSGlyphSerializer extends JsonSerializer<WNSBadgeData.Glyph> {
    @Override
    public void serialize(WNSBadgeData.Glyph glyph, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeString(glyph.getIdentifier());
    }
}
