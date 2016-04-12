/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.android;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.android.AndroidDevicePayload;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class AndroidDevicePayloadDeserializer extends JsonDeserializer<AndroidDevicePayload> {

    private static final FieldParserRegistry<AndroidDevicePayload, AndroidDevicePayloadReader> FIELD_PARSERS = new MapFieldParserRegistry<AndroidDevicePayload, AndroidDevicePayloadReader>(
            ImmutableMap.<String, FieldParser<AndroidDevicePayloadReader>>builder()
            .put("alert", new FieldParser<AndroidDevicePayloadReader>() {
                public void parse(AndroidDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readAlert(json);
                }
            })
            .put("collapse_key", new FieldParser<AndroidDevicePayloadReader>() {
                    public void parse(AndroidDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readCollapseKey(json);
                    }
                })
            .put("time_to_live", new FieldParser<AndroidDevicePayloadReader>() {
                    public void parse(AndroidDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readTimeToLive(json);
                    }
                })
            .put("delay_while_idle", new FieldParser<AndroidDevicePayloadReader>() {
                    public void parse(AndroidDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readDelayWhileIdle(json);
                    }
                })
            .put("extra", new FieldParser<AndroidDevicePayloadReader>() {
                    public void parse(AndroidDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readExtra(json);
                    }
                })
            .put("interactive", new FieldParser<AndroidDevicePayloadReader>() {
                    public void parse(AndroidDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readInteractive(json);
                    }
                })
            .put("title", new FieldParser<AndroidDevicePayloadReader>() {
                    public void parse(AndroidDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readTitle(json);
                    }
                })
            .build()
            );

    private final StandardObjectDeserializer<AndroidDevicePayload, ?> deserializer;

    public AndroidDevicePayloadDeserializer() {
        deserializer = new StandardObjectDeserializer<AndroidDevicePayload, AndroidDevicePayloadReader>(
            FIELD_PARSERS,
            new Supplier<AndroidDevicePayloadReader>() {
                @Override
                public AndroidDevicePayloadReader get() {
                    return new AndroidDevicePayloadReader();
                }
            }
        );
    }

    @Override
    public AndroidDevicePayload deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
