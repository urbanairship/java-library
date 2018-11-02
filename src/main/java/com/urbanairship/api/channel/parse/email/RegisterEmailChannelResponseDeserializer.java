package com.urbanairship.api.channel.parse.email;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.channel.model.email.RegisterEmailChannelResponse;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;

import java.io.IOException;

public class RegisterEmailChannelResponseDeserializer extends JsonDeserializer<RegisterEmailChannelResponse> {

    private static final FieldParserRegistry<RegisterEmailChannelResponse, RegisterEmailChannelResponseReader> FIELD_PARSERS =
            new MapFieldParserRegistry<>(
            ImmutableMap.<String, FieldParser<RegisterEmailChannelResponseReader>>builder()
                    .put("ok", new FieldParser<RegisterEmailChannelResponseReader>() {
                        @Override
                        public void parse(RegisterEmailChannelResponseReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                            reader.readOk(parser);
                        }
                    })
                    .put("channel_id", new FieldParser<RegisterEmailChannelResponseReader>() {
                        @Override
                        public void parse(RegisterEmailChannelResponseReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                            reader.readChannelId(parser);
                        }
                    })
                    .build()
    );

    private final StandardObjectDeserializer<RegisterEmailChannelResponse, ?> deserializer;

    public RegisterEmailChannelResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<RegisterEmailChannelResponse, RegisterEmailChannelResponseReader>(
                FIELD_PARSERS,
                new Supplier<RegisterEmailChannelResponseReader>() {
                    @Override
                    public RegisterEmailChannelResponseReader get() {
                        return new RegisterEmailChannelResponseReader();
                    }
                }
        );
    }

    @Override
    public RegisterEmailChannelResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
