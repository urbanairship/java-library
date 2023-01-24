/*
 * Copyright (c) 2013-2022. Airship and Contributors
 */

package com.urbanairship.api.attributelists.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.attributelists.model.AttributeListsListingResponse;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;

import java.io.IOException;

public class AttributeListsListingResponseDeserializer extends JsonDeserializer<AttributeListsListingResponse> {
    private static final FieldParserRegistry<AttributeListsListingResponse, AttributeListsResponseReader> FIELD_PARSERS =
            new MapFieldParserRegistry<AttributeListsListingResponse, AttributeListsResponseReader>(
                    ImmutableMap.<String, FieldParser<AttributeListsResponseReader>>builder()
                            .put("ok", (reader, jsonParser, deserializationContext) -> reader.readOk(jsonParser))
                            .put("lists", (reader, jsonParser, deserializationContext) -> reader.readStaticListObjects(jsonParser))
                            .build()
            );

    private final StandardObjectDeserializer<AttributeListsListingResponse, ?> deserializer;

    public AttributeListsListingResponseDeserializer() {
        deserializer = new StandardObjectDeserializer<AttributeListsListingResponse, AttributeListsResponseReader>(
                FIELD_PARSERS,
                () -> new AttributeListsResponseReader()
        );
    }

    @Override
    public AttributeListsListingResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }


}
