/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.notification.wns.WNSBadgeData;

import java.io.IOException;

public class WNSGlyphDeserializer extends JsonDeserializer<WNSBadgeData.Glyph> {
    public static final WNSGlyphDeserializer INSTANCE = new WNSGlyphDeserializer();
    @Override
    public WNSBadgeData.Glyph deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String value = jp.getText();
        WNSBadgeData.Glyph glyph = WNSBadgeData.Glyph.get(value);
        if (glyph == null) {
            APIParsingException.raise("Unrecognized WNS badge glyph " + value, jp);
        }
        return glyph;
    }
}
