package com.urbanairship.api.channel.parse.web;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.channel.Constants;
import com.urbanairship.api.channel.model.web.Subscription;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class SubscriptionDeserializer extends JsonDeserializer<Subscription> {

    private static final FieldParserRegistry<Subscription, SubscriptionReader> FIELD_PARSERS = new MapFieldParserRegistry<Subscription, SubscriptionReader>(
            ImmutableMap.<String, FieldParser<SubscriptionReader>>builder()
                    .put(Constants.AUTH, new FieldParser<SubscriptionReader>() {
                        @Override
                        public void parse(SubscriptionReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readAuth(jsonParser);
                        }
                    })
                    .put(Constants.P256DH, new FieldParser<SubscriptionReader>() {
                        @Override
                        public void parse(SubscriptionReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readP256dh(jsonParser);
                        }
                    })
                    .build()
    );

    private final StandardObjectDeserializer<Subscription, ?> deserializer;

    public SubscriptionDeserializer() {
        deserializer = new StandardObjectDeserializer<Subscription, SubscriptionReader>(
                FIELD_PARSERS,
                new Supplier<SubscriptionReader>() {
                    @Override
                    public SubscriptionReader get() {
                        return new SubscriptionReader();
                    }
                }
        );
    }

    @Override
    public Subscription deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
