package com.urbanairship.api.push.parse.notification.sms;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.sms.SmsPayload;

import java.io.IOException;

public class SmsPayloadDeserializer extends JsonDeserializer<SmsPayload> {
    private static final FieldParserRegistry<SmsPayload, SmsPayloadReader> FIELD_PARSERS =
            new MapFieldParserRegistry<SmsPayload, SmsPayloadReader>(
                    ImmutableMap.<String, FieldParser<SmsPayloadReader>>builder()
                    .put("alert", (reader, parser, context) -> reader.readAlert(parser))
                    .put("expiry", (reader, parser, context) -> reader.readPushExpiry(parser))
                    .build());

    private final StandardObjectDeserializer<SmsPayload, ?> deserializer;

    public SmsPayloadDeserializer() {
        deserializer = new StandardObjectDeserializer<>(
                FIELD_PARSERS,
                () -> new SmsPayloadReader()
        );
    }

    @Override
    public SmsPayload deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return deserializer.deserialize(parser, context);
    }
}
