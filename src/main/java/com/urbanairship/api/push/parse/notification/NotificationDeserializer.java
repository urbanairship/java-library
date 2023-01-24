/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.notification.DevicePayloadOverride;
import com.urbanairship.api.push.model.notification.Notification;

import java.io.IOException;
import java.util.Map;

public class NotificationDeserializer extends JsonDeserializer<Notification> {

    private static final FieldParserRegistry<Notification, NotificationReader> FIELD_PARSERS =
        new MapFieldParserRegistry<Notification, NotificationReader>(
            ImmutableMap.<String, FieldParser<NotificationReader>>builder()
            .put("alert", (reader, jsonParser, deserializationContext) -> reader.readAlert(jsonParser))
            .put("wns", (reader, jsonParser, deserializationContext) -> reader.readPlatformDevicePayloadOverride(DeviceType.WNS, jsonParser, deserializationContext))
            .put("ios", (reader, jsonParser, deserializationContext) -> reader.readPlatformDevicePayloadOverride(DeviceType.IOS, jsonParser, deserializationContext))
            .put("android", (reader, jsonParser, deserializationContext) -> reader.readPlatformDevicePayloadOverride(DeviceType.ANDROID, jsonParser, deserializationContext))
            .put("amazon", (reader, jsonParser, deserializationContext) -> reader.readPlatformDevicePayloadOverride(DeviceType.AMAZON, jsonParser, deserializationContext))
            .put("sms", (reader, jsonParser, deserializationContext) -> reader.readPlatformDevicePayloadOverride(DeviceType.SMS, jsonParser, deserializationContext))
            .put("web", (reader, jsonParser, deserializationContext) -> reader.readPlatformDevicePayloadOverride(DeviceType.WEB, jsonParser, deserializationContext))
            .put("email", (reader, jsonParser, deserializationContext) -> reader.readPlatformDevicePayloadOverride(DeviceType.EMAIL, jsonParser, deserializationContext))
            .put("actions", (reader, jsonParser, deserializationContext) -> reader.readActions(jsonParser))
            .put("interactive", (reader, jsonParser, deserializationContext) -> reader.readInteractive(jsonParser))
            .build());

    private final StandardObjectDeserializer<Notification, ?> deserializer;

    public NotificationDeserializer(final Map<DeviceType, JsonDeserializer<? extends DevicePayloadOverride>> payloadOverridesDeserializersRegistry) {
        deserializer = new StandardObjectDeserializer<Notification, NotificationReader>(
            FIELD_PARSERS,
                () -> new NotificationReader(payloadOverridesDeserializersRegistry)
        );
    }

    @Override
    public Notification deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
