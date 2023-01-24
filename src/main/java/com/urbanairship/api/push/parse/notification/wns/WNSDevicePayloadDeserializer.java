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
import com.urbanairship.api.push.model.notification.wns.WNSDevicePayload;

import java.io.IOException;

public class WNSDevicePayloadDeserializer extends JsonDeserializer<WNSDevicePayload> {

    private static final FieldParserRegistry<WNSDevicePayload, WNSDevicePayloadReader> FIELD_PARSERS = new MapFieldParserRegistry<WNSDevicePayload, WNSDevicePayloadReader>(
            ImmutableMap.<String, FieldParser<WNSDevicePayloadReader>>builder()
            .put("alert", (reader, json, context) -> reader.readAlert(json, context))
            .put("toast", (reader, json, context) -> reader.readToast(json, context))
            .put("tile", (reader, json, context) -> reader.readTile(json, context))
            .put("badge", (reader, json, context) -> reader.readBadge(json, context))
            .put("cache_policy", (reader, json, context) -> reader.readCachePolicy(json, context))
            .build()
            );

    private final StandardObjectDeserializer<WNSDevicePayload, ?> deserializer;

    public WNSDevicePayloadDeserializer(final WNSToastDeserializer toastDS, final WNSTileDeserializer tileDS, final WNSBadgeDeserializer badgeDS) {
        deserializer = new StandardObjectDeserializer<WNSDevicePayload, WNSDevicePayloadReader>(
            FIELD_PARSERS,
                () -> new WNSDevicePayloadReader(toastDS, tileDS, badgeDS)
        );
    }

    @Override
    public WNSDevicePayload deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
