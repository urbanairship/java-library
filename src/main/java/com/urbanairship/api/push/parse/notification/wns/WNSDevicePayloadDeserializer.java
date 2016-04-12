/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.wns.WNSDevicePayload;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class WNSDevicePayloadDeserializer extends JsonDeserializer<WNSDevicePayload> {

    private static final FieldParserRegistry<WNSDevicePayload, WNSDevicePayloadReader> FIELD_PARSERS = new MapFieldParserRegistry<WNSDevicePayload, WNSDevicePayloadReader>(
            ImmutableMap.<String, FieldParser<WNSDevicePayloadReader>>builder()
            .put("alert", new FieldParser<WNSDevicePayloadReader>() {
                    public void parse(WNSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readAlert(json, context);
                    }
                })
            .put("toast", new FieldParser<WNSDevicePayloadReader>() {
                    public void parse(WNSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readToast(json, context);
                    }
                })
            .put("tile", new FieldParser<WNSDevicePayloadReader>() {
                    public void parse(WNSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readTile(json, context);
                    }
                })
            .put("badge", new FieldParser<WNSDevicePayloadReader>() {
                    public void parse(WNSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readBadge(json, context);
                    }
                })
            .put("cache_policy", new FieldParser<WNSDevicePayloadReader>() {
                    public void parse(WNSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readCachePolicy(json, context);
                    }
                })
            .build()
            );

    private final StandardObjectDeserializer<WNSDevicePayload, ?> deserializer;

    public WNSDevicePayloadDeserializer(final WNSToastDeserializer toastDS, final WNSTileDeserializer tileDS, final WNSBadgeDeserializer badgeDS) {
        deserializer = new StandardObjectDeserializer<WNSDevicePayload, WNSDevicePayloadReader>(
            FIELD_PARSERS,
            new Supplier<WNSDevicePayloadReader>() {
                @Override
                public WNSDevicePayloadReader get() {
                    return new WNSDevicePayloadReader(toastDS, tileDS, badgeDS);
                }
            }
        );
    }

    @Override
    public WNSDevicePayload deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
