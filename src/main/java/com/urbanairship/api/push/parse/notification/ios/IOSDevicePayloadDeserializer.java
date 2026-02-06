/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.ios;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.ios.IOSDevicePayload;

import java.io.IOException;

public class IOSDevicePayloadDeserializer extends JsonDeserializer<IOSDevicePayload> {

    private static final FieldParserRegistry<IOSDevicePayload, IOSDevicePayloadReader> FIELD_PARSERS = new MapFieldParserRegistry<IOSDevicePayload, IOSDevicePayloadReader>(
            ImmutableMap.<String, FieldParser<IOSDevicePayloadReader>>builder()
            .put("alert", (reader, json, context) -> reader.readAlert(json, context))
            .put("badge", (reader, json, context) -> reader.readBadge(json))
            .put("content-available", (reader, json, context) -> reader.readContentAvailable(json))
            .put("content_available", (reader, json, context) -> reader.readContentAvailable(json))
            .put("extra", (reader, json, context) -> reader.readExtra(json))
            .put("category", (reader, json, context) -> reader.readCategory(json))
            .put("interactive", (reader, json, context) -> reader.readInteractive(json))
            .put("expiry", (reader, json, context) -> reader.readExpiry(json))
            .put("priority", (reader, json, context) -> reader.readPriority(json))
            .put("title", (reader, json, context) -> reader.readTitle(json))
            .put("subtitle", (reader, json, context) -> reader.readSubtitle(json))
            .put("mutable_content", (reader, json, context) -> reader.readMutableContent(json))
            .put("sound", (reader, json, context) -> reader.readSoundData(json, context))
            .put("media_attachment", (reader, json, context) -> reader.readMediaAttachment(json, context))
            .put("collapse_id", (reader, json, context) -> reader.readCollapseId(json))
            .put("thread_id", (reader, json, context) -> reader.readThreadId(json))
            .put("actions", (reader, json, context) -> reader.readActions(json))
            .put("target_content_id", (reader, json, context) -> reader.readTargetContentId(json))
            .put("template", (reader, json, context) -> reader.readIosTemplate(json))
            .put("interruption_level", (reader, json, context) -> reader.readIosInterruptionLevel(json))
            .put("relevance_score", (reader, json, context) -> reader.readRelevanceScore(json))
            .put("live_activity", (reader, json, context) -> reader.readLiveActivity(json, context))
            .build()
            );

    private final StandardObjectDeserializer<IOSDevicePayload, ?> deserializer;

    public IOSDevicePayloadDeserializer() {
        deserializer = new StandardObjectDeserializer<IOSDevicePayload, IOSDevicePayloadReader>(
            FIELD_PARSERS,
                () -> new IOSDevicePayloadReader()
        );
    }

    @Override
    public IOSDevicePayload deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
