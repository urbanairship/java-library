/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.ios;

import com.urbanairship.api.push.model.notification.ios.IOSDevicePayload;
import com.urbanairship.api.push.parse.*;
import com.urbanairship.api.common.parse.*;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
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
                        reader.readSound(json, context);
                    }
                })
            .put("badge", new FieldParser<IOSDevicePayloadReader>() {
                    public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readBadge(json, context);
                    }
                })
            .put("content-available", new FieldParser<IOSDevicePayloadReader>() {
                    public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readContentAvailable(json, context);
                    }
                })
            .put("content_available", new FieldParser<IOSDevicePayloadReader>() {
                    public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readContentAvailable(json, context);
                    }
                })
            .put("extra", new FieldParser<IOSDevicePayloadReader>() {
                    public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readExtra(json, context);
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
