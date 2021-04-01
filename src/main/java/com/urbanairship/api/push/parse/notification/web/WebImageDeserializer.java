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
import com.urbanairship.api.push.model.notification.web.WebImage;

import java.io.IOException;

public class WebImageDeserializer extends JsonDeserializer<WebImage> {

    private static final FieldParserRegistry<WebImage, WebImageReader> FIELD_PARSERS = new MapFieldParserRegistry<WebImage, WebImageReader>(
            ImmutableMap.<String, FieldParser<WebImageReader>>builder()
            .put("url", new FieldParser<WebImageReader>() {
                @Override
                public void parse(WebImageReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readUrl(json);
                }
            })
            .build()
    );

    private final StandardObjectDeserializer<WebImage, ?> deserializer;

    public WebImageDeserializer() {
        deserializer = new StandardObjectDeserializer<WebImage, WebImageReader>(
                FIELD_PARSERS,
                new Supplier<WebImageReader>() {
                    @Override
                    public WebImageReader get() {
                        return new WebImageReader();
                    }
                }
        );
    }

    @Override
    public WebImage deserialize(JsonParser json, DeserializationContext context) throws IOException {
        return deserializer.deserialize(json, context);
    }
}
