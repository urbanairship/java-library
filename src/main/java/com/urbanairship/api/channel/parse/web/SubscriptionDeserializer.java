package com.urbanairship.api.channel.parse.web;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.channel.Constants;
import com.urbanairship.api.channel.model.web.Subscription;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;

import java.io.IOException;

public class SubscriptionDeserializer extends JsonDeserializer<Subscription> {

    private static final FieldParserRegistry<Subscription, SubscriptionReader> FIELD_PARSERS = new MapFieldParserRegistry<Subscription, SubscriptionReader>(
            ImmutableMap.<String, FieldParser<SubscriptionReader>>builder()
                    .put(Constants.AUTH, (reader, jsonParser, deserializationContext) -> reader.readAuth(jsonParser))
                    .put(Constants.P256DH, (reader, jsonParser, deserializationContext) -> reader.readP256dh(jsonParser))
                    .build()
    );

    private final StandardObjectDeserializer<Subscription, ?> deserializer;

    public SubscriptionDeserializer() {
        deserializer = new StandardObjectDeserializer<Subscription, SubscriptionReader>(
                FIELD_PARSERS,
                () -> new SubscriptionReader()
        );
    }

    @Override
    public Subscription deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
