/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.mpns;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.mpns.MPNSDevicePayload;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class MPNSDevicePayloadDeserializer extends JsonDeserializer<MPNSDevicePayload> {

    private static final FieldParserRegistry<MPNSDevicePayload, MPNSDevicePayloadReader> FIELD_PARSERS = new MapFieldParserRegistry<MPNSDevicePayload, MPNSDevicePayloadReader>(
            ImmutableMap.<String, FieldParser<MPNSDevicePayloadReader>>builder()

            .put("alert", new FieldParser<MPNSDevicePayloadReader>() {
                    public void parse(MPNSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readAlert(json);
                    }
                })
            .put("toast", new FieldParser<MPNSDevicePayloadReader>() {
                    public void parse(MPNSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readToast(json, context);
                    }
                })
            .put("tile", new FieldParser<MPNSDevicePayloadReader>() {
                    public void parse(MPNSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readTile(json, context);
                    }
                })
            .put("batching_interval", new FieldParser<MPNSDevicePayloadReader>() {
                public void parse(MPNSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readBatchingInterval(json, context);
                }
            })
            .build()
            );

    private final StandardObjectDeserializer<MPNSDevicePayload, ?> deserializer;

    public MPNSDevicePayloadDeserializer(final MPNSToastDeserializer toastDS, final MPNSTileDeserializer tileDS) {
        deserializer = new StandardObjectDeserializer<MPNSDevicePayload, MPNSDevicePayloadReader>(
            FIELD_PARSERS,
            new Supplier<MPNSDevicePayloadReader>() {
                @Override
                public MPNSDevicePayloadReader get() {
                    return new MPNSDevicePayloadReader(toastDS, tileDS);
                }
            }
        );
    }

    @Override
    public MPNSDevicePayload deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
