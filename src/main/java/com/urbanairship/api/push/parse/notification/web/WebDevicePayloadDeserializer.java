package com.urbanairship.api.push.parse.notification.web;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.web.WebDevicePayload;

import java.io.IOException;

public class WebDevicePayloadDeserializer extends JsonDeserializer<WebDevicePayload> {

    private static final FieldParserRegistry<WebDevicePayload, WebDevicePayloadReader> FIELD_PARSER = new MapFieldParserRegistry<WebDevicePayload, WebDevicePayloadReader>(
            ImmutableMap.<String, FieldParser<WebDevicePayloadReader>>builder()
                    .put("alert", new FieldParser<WebDevicePayloadReader>() {
                        public void parse(WebDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readAlert(json);
                        }
                    })
                    .put("title", new FieldParser<WebDevicePayloadReader>() {
                        public void parse(WebDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readTitle(json);
                        }
                    })
                    .put("extra", new FieldParser<WebDevicePayloadReader>() {
                        public void parse(WebDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readExtra(json);
                        }
                    })
                    .put("icon", new FieldParser<WebDevicePayloadReader>() {
                        public void parse(WebDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readWebIcon(json);
                        }
                    })
                    .put("require_interaction", new FieldParser<WebDevicePayloadReader>() {
                        public void parse(WebDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readRequireInteraction(json);
                        }
                    })
                    .put("actions", new FieldParser<WebDevicePayloadReader>() {
                        @Override
                        public void parse(WebDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readActions(json);
                        }
                    })
                    .put("image", new FieldParser<WebDevicePayloadReader>() {
                        @Override
                        public void parse(WebDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readWebImage(json);
                        }
                    })
                    .put("time_to_live", new FieldParser<WebDevicePayloadReader>() {
                        @Override
                        public void parse(WebDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readExpiry(json);
                        }
                    })
                    .put("buttons", new FieldParser<WebDevicePayloadReader>() {
                        @Override
                        public void parse(WebDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readButtons(json);
                        }
                    })
                    .build()
    );

    private final StandardObjectDeserializer<WebDevicePayload, ?> deserializer;

    public WebDevicePayloadDeserializer() {
        deserializer = new StandardObjectDeserializer<WebDevicePayload, WebDevicePayloadReader>(
                FIELD_PARSER,
                new Supplier<WebDevicePayloadReader>() {
                    @Override
                    public WebDevicePayloadReader get() {
                        return new WebDevicePayloadReader();
                    }
                }
        );
    }

    @Override
    public WebDevicePayload deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
