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
import com.urbanairship.api.push.model.notification.web.WebIcon;

import java.io.IOException;

public class WebIconDeserializer extends JsonDeserializer<WebIcon> {

    private static final FieldParserRegistry<WebIcon, WebIconReader> FIELD_PARSERS = new MapFieldParserRegistry<WebIcon, WebIconReader>(
            ImmutableMap.<String, FieldParser<WebIconReader>>builder()
                .put("url", new FieldParser<WebIconReader>() {
                    @Override
                    public void parse(WebIconReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readUrl(json);
                    }
                })
                .build()
    );

    private final StandardObjectDeserializer<WebIcon, ?> deserializer;

    public WebIconDeserializer() {
        deserializer = new StandardObjectDeserializer<WebIcon, WebIconReader>(
                FIELD_PARSERS,
                new Supplier<WebIconReader>() {
                    @Override
                    public WebIconReader get() {
                        return new WebIconReader();
                    }
                }
        );
    }

    @Override
    public WebIcon deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
