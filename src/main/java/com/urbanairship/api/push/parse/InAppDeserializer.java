package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.InApp;

import java.io.IOException;

public class InAppDeserializer extends JsonDeserializer<InApp> {

    private static final FieldParserRegistry<InApp, InAppReader> FIELD_PARSERS =
            new MapFieldParserRegistry<InApp, InAppReader>(
                    ImmutableMap.<String, FieldParser<InAppReader>>builder()
                            .put("alert", (reader, jsonParser, deserializationContext) -> reader.readAlert(jsonParser))
                            .put("expiry", (reader, jsonParser, deserializationContext) -> reader.readExpiry(jsonParser))
                            .put("display", (reader, jsonParser, deserializationContext) -> reader.readDisplay(jsonParser))
                            .put("actions", (reader, jsonParser, deserializationContext) -> reader.readActions(jsonParser))
                            .put("interactive", (reader, jsonParser, deserializationContext) -> reader.readInteractive(jsonParser))
                            .put("extra", (reader, jsonParser, deserializationContext) -> reader.readAllExtras(jsonParser))
                            .build());

    private final StandardObjectDeserializer<InApp, ?> deserializer;

    public InAppDeserializer() {
        deserializer = new StandardObjectDeserializer<InApp, InAppReader>(
                FIELD_PARSERS,
                () -> new InAppReader()
        );
    }

    @Override
    public InApp deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }

}
