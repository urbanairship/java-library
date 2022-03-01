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
                    .put("ok", new FieldParser<SmsRegistrationResponseReader>() {
                        @Override
                        public void parse(SmsRegistrationResponseReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                            reader.readOk(parser);
                        }
                    })
                    .put("channel_id", new FieldParser<SmsRegistrationResponseReader>() {
                        @Override
                        public void parse(SmsRegistrationResponseReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                            reader.readChannelId(parser);
                        }
                    })
                    .put("status", new FieldParser<SmsRegistrationResponseReader>() {
                        @Override
                        public void parse(SmsRegistrationResponseReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                            reader.readStatus(parser);
                        }
                    })
                    .put("error", new FieldParser<SmsRegistrationResponseReader>() {
                        @Override
                        public void parse(SmsRegistrationResponseReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                            reader.readError(parser);
                        }
                    })
                    .put("details", new FieldParser<SmsRegistrationResponseReader>() {
                        @Override
                        public void parse(SmsRegistrationResponseReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                            reader.readErrorDetails(parser);
                        }
                    })
                    .build()
            );

    private final StandardObjectDeserializer<SmsRegistrationResponse, ?> deserializer;

    public SmsRegistrationResponseDeserializer() {
        deserializer = new StandardObjectDeserializer<SmsRegistrationResponse, SmsRegistrationResponseReader>(
                FIELD_PARSERS,
                new Supplier<SmsRegistrationResponseReader>() {
                    @Override
                    public SmsRegistrationResponseReader get() {
                        return new SmsRegistrationResponseReader();
                    }
                }
        );
    }

    @Override
    public SmsRegistrationResponse deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        return deserializer.deserialize(parser, context);
    }
}
