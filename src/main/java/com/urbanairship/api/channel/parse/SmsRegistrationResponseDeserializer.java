package com.urbanairship.api.channel.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.channel.model.SmsRegistrationResponse;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;

import java.io.IOException;

public class SmsRegistrationResponseDeserializer extends JsonDeserializer<SmsRegistrationResponse> {
    private static final FieldParserRegistry<SmsRegistrationResponse, SmsRegistrationResponseReader> FIELD_PARSERS =
            new MapFieldParserRegistry<SmsRegistrationResponse, SmsRegistrationResponseReader>(
                    ImmutableMap.<String, FieldParser<SmsRegistrationResponseReader>>builder()
                    .put("ok", (reader, parser, context) -> reader.readOk(parser))
                    .put("channel_id", (reader, parser, context) -> reader.readChannelId(parser))
                    .put("status", (reader, parser, context) -> reader.readStatus(parser))
                    .put("error", (reader, parser, context) -> reader.readError(parser))
                    .put("details", (reader, parser, context) -> reader.readErrorDetails(parser))
                    .build()
            );

    private final StandardObjectDeserializer<SmsRegistrationResponse, ?> deserializer;

    public SmsRegistrationResponseDeserializer() {
        deserializer = new StandardObjectDeserializer<SmsRegistrationResponse, SmsRegistrationResponseReader>(
                FIELD_PARSERS,
                () -> new SmsRegistrationResponseReader()
        );
    }

    @Override
    public SmsRegistrationResponse deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        return deserializer.deserialize(parser, context);
    }
}
