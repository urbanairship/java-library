/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.ios;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.ios.IOSDevicePayload;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class IOSDevicePayloadDeserializer extends JsonDeserializer<IOSDevicePayload> {

    private static final FieldParserRegistry<IOSDevicePayload, IOSDevicePayloadReader> FIELD_PARSERS = new MapFieldParserRegistry<IOSDevicePayload, IOSDevicePayloadReader>(
            ImmutableMap.<String, FieldParser<IOSDevicePayloadReader>>builder()
            .put("alert", new FieldParser<IOSDevicePayloadReader>() {
                    public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readAlert(json, context);
                    }
                })
            .put("sound", new FieldParser<IOSDevicePayloadReader>() {
                    public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readSound(json);
                    }
                })
            .put("badge", new FieldParser<IOSDevicePayloadReader>() {
                    public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readBadge(json);
                    }
                })
            .put("content-available", new FieldParser<IOSDevicePayloadReader>() {
                    public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readContentAvailable(json);
                    }
                })
            .put("content_available", new FieldParser<IOSDevicePayloadReader>() {
                    public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readContentAvailable(json);
                    }
                })
            .put("extra", new FieldParser<IOSDevicePayloadReader>() {
                    public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readExtra(json);
                    }
                })
            .put("category", new FieldParser<IOSDevicePayloadReader>() {
                    public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readCategory(json);
                    }
                })
            .put("interactive", new FieldParser<IOSDevicePayloadReader>() {
                    public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readInteractive(json);
                    }
                })
            .put("expiry", new FieldParser<IOSDevicePayloadReader>() {
                    public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readExpiry(json);
                    }
            })
            .put("priority", new FieldParser<IOSDevicePayloadReader>() {
                    public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readPriority(json);
                    }
            })
            .build()
            );

    private final StandardObjectDeserializer<IOSDevicePayload, ?> deserializer;

    public IOSDevicePayloadDeserializer() {
        deserializer = new StandardObjectDeserializer<IOSDevicePayload, IOSDevicePayloadReader>(
            FIELD_PARSERS,
            new Supplier<IOSDevicePayloadReader>() {
                @Override
                public IOSDevicePayloadReader get() {
                    return new IOSDevicePayloadReader();
                }
            }
        );
    }

    @Override
    public IOSDevicePayload deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
