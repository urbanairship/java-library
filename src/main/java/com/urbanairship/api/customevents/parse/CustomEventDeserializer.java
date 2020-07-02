package com.urbanairship.api.customevents.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.customevents.model.CustomEventPayload;

import java.io.IOException;

public class CustomEventDeserializer extends JsonDeserializer<CustomEventPayload> {

    private static final FieldParserRegistry<CustomEventPayload, CustomEventReader> FIELD_PARSERS = new MapFieldParserRegistry<CustomEventPayload, CustomEventReader>(
            ImmutableMap.<String, FieldParser<CustomEventReader>>builder()
            .put("occurred", new FieldParser<CustomEventReader>() {
                @Override
                public void parse(CustomEventReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readOccurred(json);
                }
            })
            .put("user", new FieldParser<CustomEventReader>() {
                @Override
                public void parse(CustomEventReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readUser(json);
                }
            })
            .put("body", new FieldParser<CustomEventReader>() {
                @Override
                public void parse(CustomEventReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readBody(json);
                }
            })
            .build()
    );

    private final StandardObjectDeserializer<CustomEventPayload, ?> deserializer;

    public CustomEventDeserializer() {
        deserializer = new StandardObjectDeserializer<CustomEventPayload, CustomEventReader>(
                FIELD_PARSERS,
                new Supplier<CustomEventReader>() {
                    @Override
                    public CustomEventReader get() {
                        return new CustomEventReader();
                    }
                }
        );
    }

    @Override
    public CustomEventPayload deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
