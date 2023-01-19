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
import com.urbanairship.api.push.model.notification.android.AndroidDevicePayload;

import java.io.IOException;

public class AndroidDevicePayloadDeserializer extends JsonDeserializer<AndroidDevicePayload> {

    private static final FieldParserRegistry<AndroidDevicePayload, AndroidDevicePayloadReader> FIELD_PARSERS = new MapFieldParserRegistry<AndroidDevicePayload, AndroidDevicePayloadReader>(
            ImmutableMap.<String, FieldParser<AndroidDevicePayloadReader>>builder()
                    .put("alert", (reader, json, context) -> reader.readAlert(json))
                    .put("collapse_key", (reader, json, context) -> reader.readCollapseKey(json))
                    .put("notification_channel", (reader, json, context) -> reader.readNotificationChannel(json))
                    .put("notification_tag", (reader, json, context) -> reader.readNotificationTag(json))
                    .put("time_to_live", (reader, json, context) -> reader.readTimeToLive(json))
                    .put("delivery_priority", (reader, json, context) -> reader.readDeliveryPriority(json))
                    .put("delay_while_idle", (reader, json, context) -> reader.readDelayWhileIdle(json))
                    .put("extra", (reader, json, context) -> reader.readExtra(json))
                    .put("interactive", (reader, json, context) -> reader.readInteractive(json))
                    .put("title", (reader, json, context) -> reader.readTitle(json))
                    .put("local_only", (reader, json, context) -> reader.readLocalOnly(json))
                    .put("wearable", (reader, json, context) -> reader.readWearable(json))
                    .put("summary", (reader, json, context) -> reader.readSummary(json))
                    .put("style", (reader, json, context) -> reader.readStyle(json))
                    .put("sound", (reader, json, context) -> reader.readSound(json))
                    .put("icon", (reader, json, context) -> reader.readIcon(json))
                    .put("icon_color", (reader, json, context) -> reader.readIconColor(json))
                    .put("priority", (reader, json, context) -> reader.readPriority(json))
                    .put("category", (reader, json, context) -> reader.readCategory(json))
                    .put("visibility", (reader, json, context) -> reader.readVisibility(json))
                    .put("public_notification", (reader, json, context) -> reader.readPublicNotification(json))
                    .put("actions", (reader, json, context) -> reader.readActions(json))
                    .put("template", (reader, json, context) -> reader.readTemplate(json))
                    .build()
    );

    private final StandardObjectDeserializer<AndroidDevicePayload, ?> deserializer;

    public AndroidDevicePayloadDeserializer() {
        deserializer = new StandardObjectDeserializer<AndroidDevicePayload, AndroidDevicePayloadReader>(
            FIELD_PARSERS,
                () -> new AndroidDevicePayloadReader()
        );
    }

    @Override
    public AndroidDevicePayload deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
