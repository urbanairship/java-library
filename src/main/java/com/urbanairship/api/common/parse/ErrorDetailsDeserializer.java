package com.urbanairship.api.common.parse;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.model.ErrorDetails;

import java.io.IOException;

public class ErrorDetailsDeserializer extends JsonDeserializer<ErrorDetails> {

    private static final FieldParserRegistry<ErrorDetails, ErrorDetailsReader> FIELD_PARSERS =
            new MapFieldParserRegistry<>(
            ImmutableMap.<String, FieldParser<ErrorDetailsReader>>builder()
                    .put("error", new FieldParser<ErrorDetailsReader>() {
                        @Override
                        public void parse(ErrorDetailsReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                            reader.readError(parser);
                        }
                    })
                    .put("path", new FieldParser<ErrorDetailsReader>() {
                        @Override
                        public void parse(ErrorDetailsReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                            reader.readPath(parser);
                        }
                    })
                    .build()
    );

    private final StandardObjectDeserializer<ErrorDetails, ?> deserializer;

    public ErrorDetailsDeserializer() {
        this.deserializer = new StandardObjectDeserializer<ErrorDetails, ErrorDetailsReader>(
                FIELD_PARSERS,
                new Supplier<ErrorDetailsReader>() {
                    @Override
                    public ErrorDetailsReader get() {
                        return new ErrorDetailsReader();
                    }
                }
        );
    }

    @Override
    public ErrorDetails deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
