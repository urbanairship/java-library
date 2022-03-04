package com.urbanairship.api.channel.parse.subscriptionlist;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.channel.model.subscriptionlist.SubscriptionListResponse;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;

import java.io.IOException;

public class SubscriptionListResponseDeserializer extends JsonDeserializer<SubscriptionListResponse> {
    private static final FieldParserRegistry<SubscriptionListResponse, SubscriptionListResponseReader> FIELD_PARSERS = new MapFieldParserRegistry<SubscriptionListResponse, SubscriptionListResponseReader>(
            ImmutableMap.<String, FieldParser<SubscriptionListResponseReader>>builder()
            .put("ok", new FieldParser<SubscriptionListResponseReader>() {
                @Override
                public void parse(SubscriptionListResponseReader reader, JsonParser jsonParser, DeserializationContext context) throws IOException {
                    reader.readOk(jsonParser);
                }
            })
            .put("error", new FieldParser<SubscriptionListResponseReader>() {
                @Override
                public void parse(SubscriptionListResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readError(jsonParser);
                }
            })
            .put("details", new FieldParser<SubscriptionListResponseReader>() {
                @Override
                public void parse(SubscriptionListResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readErrorDetails(jsonParser);
                }
            })
            .build()
    );

    private final StandardObjectDeserializer<SubscriptionListResponse, ?> deserializer;

    public SubscriptionListResponseDeserializer() {
        deserializer = new StandardObjectDeserializer<SubscriptionListResponse, SubscriptionListResponseReader>(
                FIELD_PARSERS,
                new Supplier<SubscriptionListResponseReader>() {
                    @Override
                    public SubscriptionListResponseReader get() {
                        return new SubscriptionListResponseReader();
                    }
                }
        );
    }

    @Override
    public SubscriptionListResponse deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        return deserializer.deserialize(jsonParser, context);
    }
}
