package com.urbanairship.api.channel.parse.web;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.channel.Constants;
import com.urbanairship.api.channel.model.web.WebSettings;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class WebSettingsDeserializer extends JsonDeserializer<WebSettings> {
    private static final FieldParserRegistry<WebSettings, WebSettingsReader> FIELD_PARSERS = new MapFieldParserRegistry<WebSettings, WebSettingsReader>(
            ImmutableMap.<String, FieldParser<WebSettingsReader>>builder()
                    .put(Constants.SUBSCRIPTION, new FieldParser<WebSettingsReader>() {
                        @Override
                        public void parse(WebSettingsReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readSubscription(jsonParser);
                        }
                    })
                    .build()
    );

    private final StandardObjectDeserializer<WebSettings, ?> deserializer;

    public WebSettingsDeserializer() {
        deserializer = new StandardObjectDeserializer<WebSettings, WebSettingsReader>(
                FIELD_PARSERS,
                new Supplier<WebSettingsReader>() {
                    @Override
                    public WebSettingsReader get() {
                        return new WebSettingsReader();
                    }
                }
        );
    }

    @Override
    public WebSettings deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
