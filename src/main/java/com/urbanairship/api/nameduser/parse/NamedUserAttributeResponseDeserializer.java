package com.urbanairship.api.nameduser.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.nameduser.model.NamedUserAttributeResponse;

import java.io.IOException;

public class NamedUserAttributeResponseDeserializer extends JsonDeserializer<NamedUserAttributeResponse> {
    private static final FieldParserRegistry<NamedUserAttributeResponse, NamedUserAttributeResponseReader> FIELD_PARSERS = new MapFieldParserRegistry<NamedUserAttributeResponse, NamedUserAttributeResponseReader>(
            ImmutableMap.<String, FieldParser<NamedUserAttributeResponseReader>>builder()
                    .put("ok", new FieldParser<NamedUserAttributeResponseReader>() {
                        @Override
                        public void parse(NamedUserAttributeResponseReader reader, JsonParser jsonParser, DeserializationContext context) throws IOException {
                            reader.readOk(jsonParser);
                        }
                    })
                    .put("error", new FieldParser<NamedUserAttributeResponseReader>() {
                        @Override
                        public void parse(NamedUserAttributeResponseReader reader, JsonParser jsonParser, DeserializationContext context) throws IOException {
                            reader.readError(jsonParser);
                        }
                    })
                    .put("details", new FieldParser<NamedUserAttributeResponseReader>() {
                        @Override
                        public void parse(NamedUserAttributeResponseReader reader, JsonParser jsonParser, DeserializationContext context) throws IOException {
                            reader.readErrorDetails(jsonParser);
                        }
                    })
                    .build()
    );

    private final StandardObjectDeserializer<NamedUserAttributeResponse, ?> deserializer;

    public NamedUserAttributeResponseDeserializer() {
        deserializer = new StandardObjectDeserializer<NamedUserAttributeResponse, NamedUserAttributeResponseReader>(
                FIELD_PARSERS,
                new Supplier<NamedUserAttributeResponseReader>() {
                    @Override
                    public NamedUserAttributeResponseReader get() {
                        return new NamedUserAttributeResponseReader();
                    }
                }
        );
    }

    @Override
    public NamedUserAttributeResponse deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return deserializer.deserialize(parser, context);
    }
}
