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
import com.urbanairship.api.push.model.notification.web.Button;

import java.io.IOException;

public class ButtonDeserializer extends JsonDeserializer<Button> {

    private static final FieldParserRegistry<Button, ButtonReader> FIELD_PARSERS = new MapFieldParserRegistry<Button, ButtonReader>(
            ImmutableMap.<String, FieldParser<ButtonReader>>builder()
            .put("id", (reader, parser, context) -> reader.readId(parser))
            .put("label", (reader, parser, context) -> reader.readLabel(parser))
            .put("actions", (reader, parser, context) -> reader.readActions(parser))
            .build()
    );

    private final StandardObjectDeserializer<Button, ?> deserializer;

    public ButtonDeserializer() {
        deserializer = new StandardObjectDeserializer<>(
                FIELD_PARSERS,
                () -> new ButtonReader()
        );
    }

    @Override
    public Button deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return deserializer.deserialize(parser, context);
    }
}
