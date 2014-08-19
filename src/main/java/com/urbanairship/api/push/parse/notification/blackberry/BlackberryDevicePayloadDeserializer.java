package com.urbanairship.api.push.parse.notification.blackberry;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.notification.blackberry.BlackberryDevicePayload;
import com.urbanairship.api.push.parse.*;
import com.urbanairship.api.common.parse.*;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class BlackberryDevicePayloadDeserializer extends JsonDeserializer<BlackberryDevicePayload> {

    private static final FieldParserRegistry<BlackberryDevicePayload, BlackberryDevicePayloadReader> FIELD_PARSERS = new MapFieldParserRegistry<BlackberryDevicePayload, BlackberryDevicePayloadReader>(
            ImmutableMap.<String, FieldParser<BlackberryDevicePayloadReader>>builder()
            .put("alert", new FieldParser<BlackberryDevicePayloadReader>() {
                    public void parse(BlackberryDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readAlert(json, context);
                    }
                })
            .put("body", new FieldParser<BlackberryDevicePayloadReader>() {
                    public void parse(BlackberryDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readBody(json, context);
                    }
                })
            .put("content-type", new FieldParser<BlackberryDevicePayloadReader>() {
                    public void parse(BlackberryDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readContentType(json, context);
                    }
                })
            .put("content_type", new FieldParser<BlackberryDevicePayloadReader>() {
                    public void parse(BlackberryDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readContentType(json, context);
                    }
                })
            .build()
            );

    private final StandardObjectDeserializer<BlackberryDevicePayload, ?> deserializer;

    public BlackberryDevicePayloadDeserializer() {
        deserializer = new StandardObjectDeserializer<BlackberryDevicePayload, BlackberryDevicePayloadReader>(
            FIELD_PARSERS,
            new Supplier<BlackberryDevicePayloadReader>() {
                @Override
                public BlackberryDevicePayloadReader get() {
                    return new BlackberryDevicePayloadReader();
                }
            }
        );
    }

    @Override
    public BlackberryDevicePayload deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
