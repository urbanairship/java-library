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
import com.urbanairship.api.nameduser.model.NamedUserView;

import java.io.IOException;

public class NamedUserViewDeserializer extends JsonDeserializer<NamedUserView> {

    private static final FieldParserRegistry<NamedUserView, NamedUserViewReader> FIELD_PARSERS = new MapFieldParserRegistry<NamedUserView, NamedUserViewReader>(
        ImmutableMap.<String, FieldParser<NamedUserViewReader>>builder()
            .put("named_user_id", (reader, jsonParser, deserializationContext) -> reader.readNamedUserId(jsonParser))
            .put("tags", (reader, jsonParser, deserializationContext) -> reader.readNamedUserTags(jsonParser))
            .put("channels", (reader, jsonParser, deserializationContext) -> reader.readChannelView(jsonParser))
            .put("attributes", (reader, jsonParser, deserializationContext) -> reader.readAttributes(jsonParser))
            .put("user_attributes", (reader, jsonParser, deserializationContext) -> reader.readUserAttributes(jsonParser))
            .build());

    private final StandardObjectDeserializer<NamedUserView, ?> deserializer;

    public NamedUserViewDeserializer() {
        deserializer = new StandardObjectDeserializer<NamedUserView, NamedUserViewReader>(
            FIELD_PARSERS,
                () -> new NamedUserViewReader()
        );
    }

    @Override
    public NamedUserView deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
