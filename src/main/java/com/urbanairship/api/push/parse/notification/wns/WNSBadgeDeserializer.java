/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.urbanairship.api.push.model.notification.wns.WNSBadgeData;
import com.urbanairship.api.push.parse.*;
import com.urbanairship.api.common.parse.*;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class WNSBadgeDeserializer extends JsonDeserializer<WNSBadgeData> {

    private static final FieldParserRegistry<WNSBadgeData, WNSBadgeReader> FIELD_PARSERS = new MapFieldParserRegistry<WNSBadgeData, WNSBadgeReader>(
            ImmutableMap.<String, FieldParser<WNSBadgeReader>>builder()
            .put("value", new FieldParser<WNSBadgeReader>() {
                    public void parse(WNSBadgeReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readValue(json);
                    }
                })
            .put("glyph", new FieldParser<WNSBadgeReader>() {
                    public void parse(WNSBadgeReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readGlyph(json, context);
                    }
                })
            .put("duration", new FieldParser<WNSBadgeReader>() {
                    public void parse(WNSBadgeReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readGlyph(json, context);
                    }
                })
            .build()
            );

    private final StandardObjectDeserializer<WNSBadgeData, ?> deserializer;

    public WNSBadgeDeserializer() {
        deserializer = new StandardObjectDeserializer<WNSBadgeData, WNSBadgeReader>(
            FIELD_PARSERS,
            new Supplier<WNSBadgeReader>() {
                @Override
                public WNSBadgeReader get() {
                    return new WNSBadgeReader();
                }
            }
        );
    }

    @Override
    public WNSBadgeData deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
