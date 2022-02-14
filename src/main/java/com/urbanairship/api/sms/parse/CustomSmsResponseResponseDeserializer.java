package com.urbanairship.api.sms.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.sms.model.CustomSmsResponseResponse;

import java.io.IOException;

public class CustomSmsResponseResponseDeserializer extends JsonDeserializer<CustomSmsResponseResponse> {

    private static final FieldParserRegistry<CustomSmsResponseResponse, CustomSmsResponseResponseReader> FIELD_PARSERS =
            new MapFieldParserRegistry<>(
            ImmutableMap.<String, FieldParser<CustomSmsResponseResponseReader>>builder()
                    .put("ok", new FieldParser<CustomSmsResponseResponseReader>() {
                        @Override
                        public void parse(CustomSmsResponseResponseReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                            reader.readOk(parser);
                        }
                    })
                    .put("push_id", new FieldParser<CustomSmsResponseResponseReader>() {
                        @Override
                        public void parse(CustomSmsResponseResponseReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                            reader.readPushId(parser);
                        }
                    })
                    .put("operation_id", new FieldParser<CustomSmsResponseResponseReader>() {
                        @Override
                        public void parse(CustomSmsResponseResponseReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                            reader.readOperationId(parser);
                        }
                    })
                    .build()
    );

    private final StandardObjectDeserializer<CustomSmsResponseResponse, ?> deserializer;

    public CustomSmsResponseResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<CustomSmsResponseResponse, CustomSmsResponseResponseReader>(
                FIELD_PARSERS,
                new Supplier<CustomSmsResponseResponseReader>() {
                    @Override
                    public CustomSmsResponseResponseReader get() {
                        return new CustomSmsResponseResponseReader();
                    }
                }
        );
    }

    @Override
    public CustomSmsResponseResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
