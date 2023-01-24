package com.urbanairship.api.channel.parse.web;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.channel.Constants;
import com.urbanairship.api.channel.model.web.WebSettings;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;

import java.io.IOException;

public class WebSettingsDeserializer extends JsonDeserializer<WebSettings> {
    private static final FieldParserRegistry<WebSettings, WebSettingsReader> FIELD_PARSERS = new MapFieldParserRegistry<WebSettings, WebSettingsReader>(
            ImmutableMap.<String, FieldParser<WebSettingsReader>>builder()
                    .put(Constants.SUBSCRIPTION, (reader, jsonParser, deserializationContext) -> reader.readSubscription(jsonParser))
                    .build()
    );

    private final StandardObjectDeserializer<WebSettings, ?> deserializer;

    public WebSettingsDeserializer() {
        deserializer = new StandardObjectDeserializer<WebSettings, WebSettingsReader>(
                FIELD_PARSERS,
                () -> new WebSettingsReader()
        );
    }

    @Override
    public WebSettings deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
