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
import com.urbanairship.api.nameduser.model.NamedUserUpdateResponse;

import java.io.IOException;

public class NamedUserUpdateResponseDeserializer extends JsonDeserializer<NamedUserUpdateResponse> {
    private static final FieldParserRegistry<NamedUserUpdateResponse, NamedUserUpdateResponseReader> FIELD_PARSERS = new MapFieldParserRegistry<NamedUserUpdateResponse, NamedUserUpdateResponseReader>(
            ImmutableMap.<String, FieldParser<NamedUserUpdateResponseReader>>builder()
                    .put("ok", new FieldParser<NamedUserUpdateResponseReader>() {
                        @Override
                        public void parse(NamedUserUpdateResponseReader reader, JsonParser jsonParser, DeserializationContext context) throws IOException {
                            reader.readOk(jsonParser);
                        }
                    })
                    .put("error", new FieldParser<NamedUserUpdateResponseReader>() {
                        @Override
                        public void parse(NamedUserUpdateResponseReader reader, JsonParser jsonParser, DeserializationContext context) throws IOException {
                            reader.readError(jsonParser);
                        }
                    })
                    .put("attribute_warnings", new FieldParser<NamedUserUpdateResponseReader>() {
                        @Override
                        public void parse(NamedUserUpdateResponseReader reader, JsonParser jsonParser, DeserializationContext context) throws IOException {
                            reader.readAttributeWarnings(jsonParser);
                        }
                    })
                    .put("tag_warnings", new FieldParser<NamedUserUpdateResponseReader>() {
                        @Override
                        public void parse(NamedUserUpdateResponseReader reader, JsonParser jsonParser, DeserializationContext context) throws IOException {
                            reader.readTagWarnings(jsonParser);
                        }
                    })
                    .put("details", new FieldParser<NamedUserUpdateResponseReader>() {
                        @Override
                        public void parse(NamedUserUpdateResponseReader reader, JsonParser jsonParser, DeserializationContext context) throws IOException {
                            reader.readErrorDetails(jsonParser);
                        }
                    })
                    .build()
    );

    private final StandardObjectDeserializer<NamedUserUpdateResponse, ?> deserializer;

    public NamedUserUpdateResponseDeserializer() {
        deserializer = new StandardObjectDeserializer<NamedUserUpdateResponse, NamedUserUpdateResponseReader>(
                FIELD_PARSERS,
                new Supplier<NamedUserUpdateResponseReader>() {
                    @Override
                    public NamedUserUpdateResponseReader get() {
                        return new NamedUserUpdateResponseReader();
                    }
                }
        );
    }

    @Override
    public NamedUserUpdateResponse deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return deserializer.deserialize(parser, context);
    }
}
