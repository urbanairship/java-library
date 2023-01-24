/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

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
import com.urbanairship.api.nameduser.model.NamedUserListingResponse;

import java.io.IOException;

public class NamedUserlListingResponseDeserializer extends JsonDeserializer<NamedUserListingResponse> {

    private static final FieldParserRegistry<NamedUserListingResponse, NamedUserListingResponseReader> FIELD_PARSER =
        new MapFieldParserRegistry<NamedUserListingResponse, NamedUserListingResponseReader>(
            ImmutableMap.<String, FieldParser<NamedUserListingResponseReader>>builder()
                .put("ok", (reader, jsonParser, deserializationContext) -> reader.readOk(jsonParser))
                .put("next_page", (reader, jsonParser, deserializationContext) -> reader.readNextPage(jsonParser))
                .put("named_user", (reader, jsonParser, deserializationContext) -> reader.readNamedUser(jsonParser))
                .put("named_users", (reader, jsonParser, deserializationContext) -> reader.readNamedUsers(jsonParser))
                .put("error", (reader, jsonParser, deserializationContext) -> reader.readError(jsonParser))
                .put("details", (reader, jsonParser, deserializationContext) -> reader.readErrorDetails(jsonParser))
                .build()
        );

    private final StandardObjectDeserializer<NamedUserListingResponse, ?> deserializer;

    public NamedUserlListingResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<NamedUserListingResponse, NamedUserListingResponseReader>(
            FIELD_PARSER,
                () -> new NamedUserListingResponseReader()
        );
    }

    @Override
    public NamedUserListingResponse deserialize(JsonParser jsonParser, DeserializationContext
        deserializationContext)
        throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
