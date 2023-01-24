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
                    .put("ok", (reader, jsonParser, context) -> reader.readOk(jsonParser))
                    .put("error", (reader, jsonParser, context) -> reader.readError(jsonParser))
                    .put("attribute_warnings", (reader, jsonParser, context) -> reader.readAttributeWarnings(jsonParser))
                    .put("tag_warnings", (reader, jsonParser, context) -> reader.readTagWarnings(jsonParser))
                    .put("details", (reader, jsonParser, context) -> reader.readErrorDetails(jsonParser))
                    .build()
    );

    private final StandardObjectDeserializer<NamedUserUpdateResponse, ?> deserializer;

    public NamedUserUpdateResponseDeserializer() {
        deserializer = new StandardObjectDeserializer<NamedUserUpdateResponse, NamedUserUpdateResponseReader>(
                FIELD_PARSERS,
                () -> new NamedUserUpdateResponseReader()
        );
    }

    @Override
    public NamedUserUpdateResponse deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return deserializer.deserialize(parser, context);
    }
}
