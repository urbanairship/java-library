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

public class WebDeserializer extends JsonDeserializer<WebSettings> {
    private static final FieldParserRegistry<WebSettings, WebReader> FIELD_PARSERS = new MapFieldParserRegistry<WebSettings, WebReader>(
            ImmutableMap.<String, FieldParser<WebReader>>builder()
                    .put(Constants.SUBSCRIPTION, new FieldParser<WebReader>() {
                        @Override
                        public void parse(WebReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readSubscription(jsonParser);
                        }
                    })
                    .build()
    );

    private final StandardObjectDeserializer<WebSettings, ?> deserializer;

    public WebDeserializer() {
        deserializer = new StandardObjectDeserializer<WebSettings, WebReader>(
                FIELD_PARSERS,
                new Supplier<WebReader>() {
                    @Override
                    public WebReader get() {
                        return new WebReader();
                    }
                }
        );
    }

    @Override
    public WebSettings deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
