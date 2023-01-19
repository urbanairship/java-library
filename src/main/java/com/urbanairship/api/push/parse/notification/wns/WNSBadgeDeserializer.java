/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.wns.WNSBadgeData;

import java.io.IOException;

public class WNSBadgeDeserializer extends JsonDeserializer<WNSBadgeData> {

    private static final FieldParserRegistry<WNSBadgeData, WNSBadgeReader> FIELD_PARSERS = new MapFieldParserRegistry<WNSBadgeData, WNSBadgeReader>(
            ImmutableMap.<String, FieldParser<WNSBadgeReader>>builder()
            .put("value", (reader, json, context) -> reader.readValue(json))
            .put("glyph", (reader, json, context) -> reader.readGlyph(json, context))
            .put("duration", (reader, json, context) -> reader.readGlyph(json, context))
            .build()
            );

    private final StandardObjectDeserializer<WNSBadgeData, ?> deserializer;

    public WNSBadgeDeserializer() {
        deserializer = new StandardObjectDeserializer<WNSBadgeData, WNSBadgeReader>(
            FIELD_PARSERS,
                () -> new WNSBadgeReader()
        );
    }

    @Override
    public WNSBadgeData deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
