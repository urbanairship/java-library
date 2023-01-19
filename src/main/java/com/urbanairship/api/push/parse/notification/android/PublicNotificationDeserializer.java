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
import com.urbanairship.api.push.model.notification.android.PublicNotification;

import java.io.IOException;


public class PublicNotificationDeserializer extends JsonDeserializer<PublicNotification> {
    private static final FieldParserRegistry<PublicNotification, PublicNotificationReader> FIELD_PARSERS = new MapFieldParserRegistry<PublicNotification, PublicNotificationReader>(
            ImmutableMap.<String, FieldParser<PublicNotificationReader>>builder()
                    .put("title", (reader, json, context) -> reader.readTitle(json))
                    .put("summary", (reader, json, context) -> reader.readSummary(json))
                    .put("alert", (reader, json, context) -> reader.readAlert(json))
                    .build()
    );

    private final StandardObjectDeserializer<PublicNotification, ?> deserializer;

    public PublicNotificationDeserializer() {
        deserializer = new StandardObjectDeserializer<PublicNotification, PublicNotificationReader>(
                FIELD_PARSERS,
                () -> new PublicNotificationReader()
        );
    }

    @Override
    public PublicNotification deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }

}
