/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.android;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.android.InboxStyle;

import java.io.IOException;

public class InboxStyleDeserializer extends JsonDeserializer<InboxStyle> {
    private static final FieldParserRegistry<InboxStyle, InboxStyleReader> FIELD_PARSERS = new MapFieldParserRegistry<InboxStyle, InboxStyleReader>(
            ImmutableMap.<String, FieldParser<InboxStyleReader>>builder()
                    .put("title", (reader, json, context) -> reader.readTitle(json))
                    .put("summary", (reader, json, context) -> reader.readSummary(json))
                    .put("lines", (reader, json, context) -> reader.readContent(json))
                    .build()
    );

    private final StandardObjectDeserializer<InboxStyle, ?> deserializer;

    public InboxStyleDeserializer() {
        deserializer = new StandardObjectDeserializer<InboxStyle, InboxStyleReader>(
                FIELD_PARSERS,
                () -> new InboxStyleReader()
        );
    }

    @Override
    public InboxStyle deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
