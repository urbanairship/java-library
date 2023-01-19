/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.richpush;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.richpush.RichPushMessage;

import java.io.IOException;

public class RichPushMessageDeserializer extends JsonDeserializer<RichPushMessage> {

    private static final FieldParserRegistry<RichPushMessage, RichPushMessageReader> FIELD_PARSERS = new MapFieldParserRegistry<RichPushMessage, RichPushMessageReader>(
            ImmutableMap.<String, FieldParser<RichPushMessageReader>>builder()
            .put("title", (reader, json, context) -> reader.readTitle(json))
            .put("body", (reader, json, context) -> reader.readBody(json))
            .put("content-type", (reader, json, context) -> reader.readContentType(json))
            .put("content_type", (reader, json, context) -> reader.readContentType(json))
            .put("content-encoding", (reader, json, context) -> reader.readContentEncoding(json))
            .put("content_encoding", (reader, json, context) -> reader.readContentEncoding(json))
            .put("extra", (reader, json, context) -> reader.readExtra(json))
            .put("expiry", (reader, json, context) -> reader.readExpiry(json)).put("icons", (reader, json, context) -> reader.readIcons(json))
            .build()
            );

    private final StandardObjectDeserializer<RichPushMessage, ?> deserializer;

    public RichPushMessageDeserializer() {
        deserializer = new StandardObjectDeserializer<RichPushMessage, RichPushMessageReader>(
            FIELD_PARSERS,
                () -> new RichPushMessageReader()
        );
    }

    @Override
    public RichPushMessage deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
