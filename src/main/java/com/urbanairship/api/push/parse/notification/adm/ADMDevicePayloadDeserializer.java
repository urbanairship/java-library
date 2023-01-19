/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.adm;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.adm.ADMDevicePayload;

import java.io.IOException;

public class ADMDevicePayloadDeserializer extends JsonDeserializer<ADMDevicePayload> {

    private static final FieldParserRegistry<ADMDevicePayload, ADMDevicePayloadReader> FIELD_PARSERS = new MapFieldParserRegistry<ADMDevicePayload, ADMDevicePayloadReader>(
            ImmutableMap.<String, FieldParser<ADMDevicePayloadReader>>builder()
            .put("alert", (reader, json, context) -> reader.readAlert(json))
            .put("consolidation_key", (reader, json, context) -> reader.readConsolidationKey(json))
            .put("expires_after", (reader, json, context) -> reader.readExpiresAfter(json))
            .put("extra", (reader, json, context) -> reader.readExtra(json))
            .put("interactive", (reader, json, context) -> reader.readInteractive(json))
            .put("actions", (reader, json, context) -> reader.readActions(json))
            .put("icon", (reader, json, context) -> reader.readIcon(json))
            .put("icon_color", (reader, json, context) -> reader.readIconColor(json))
            .put("notification_channel", (reader, json, context) -> reader.readNotificationChannel(json))
            .put("notification_tag", (reader, json, context) -> reader.readNotificationTag(json))
            .put("sound", (reader, json, context) -> reader.readSound(json))
            .put("summary", (reader, json, context) -> reader.readSummary(json))
            .put("title", (reader, json, context) -> reader.readTitle(json))
            .put("style", (reader, json, context) -> reader.readStyle(json))
            .put("template", (reader, json, context) -> reader.readTemplate(json))
            .build()
            );

    private final StandardObjectDeserializer<ADMDevicePayload, ?> deserializer;

    public ADMDevicePayloadDeserializer() {
        deserializer = new StandardObjectDeserializer<ADMDevicePayload, ADMDevicePayloadReader>(
            FIELD_PARSERS,
                () -> new ADMDevicePayloadReader()
        );
    }

    @Override
    public ADMDevicePayload deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
