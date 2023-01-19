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
                    .put("alert", (reader, json, context) -> reader.readAlert(json))
                    .put("title", (reader, json, context) -> reader.readTitle(json))
                    .put("extra", (reader, json, context) -> reader.readExtra(json))
                    .put("icon", (reader, json, context) -> reader.readWebIcon(json))
                    .put("require_interaction", (reader, json, context) -> reader.readRequireInteraction(json))
                    .put("actions", (reader, json, context) -> reader.readActions(json))
                    .put("image", (reader, json, context) -> reader.readWebImage(json))
                    .put("time_to_live", (reader, json, context) -> reader.readExpiry(json))
                    .put("buttons", (reader, json, context) -> reader.readButtons(json))
                    .put("template", (reader, json, context) -> reader.readTemplate(json))
                    .build()
    );

    private final StandardObjectDeserializer<WebDevicePayload, ?> deserializer;

    public WebDevicePayloadDeserializer() {
        deserializer = new StandardObjectDeserializer<WebDevicePayload, WebDevicePayloadReader>(
                FIELD_PARSER,
                () -> new WebDevicePayloadReader()
        );
    }

    @Override
    public WebDevicePayload deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
