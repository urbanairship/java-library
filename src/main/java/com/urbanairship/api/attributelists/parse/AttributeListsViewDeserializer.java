/*
 * Copyright (c) 2013-2022. Airship and Contributors
 */

package com.urbanairship.api.attributelists.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.attributelists.model.AttributeListsView;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;

import java.io.IOException;

public class AttributeListsViewDeserializer extends JsonDeserializer<AttributeListsView> {
    private static final FieldParserRegistry<AttributeListsView, AttributeListsViewReader> FIELD_PARSERS =
            new MapFieldParserRegistry<AttributeListsView, AttributeListsViewReader>(
                    ImmutableMap.<String, FieldParser<AttributeListsViewReader>>builder()
                            .put("ok", (reader, jsonParser, deserializationContext) -> reader.readOk(jsonParser))
                            .put("name", (reader, jsonParser, deserializationContext) -> reader.readName(jsonParser))
                            .put("description", (reader, jsonParser, deserializationContext) -> reader.readDescription(jsonParser))
                            .put("extra", (reader, jsonParser, deserializationContext) -> reader.readExtras(jsonParser))
                            .put("created", (reader, jsonParser, deserializationContext) -> reader.readCreated(jsonParser))
                            .put("last_updated", (reader, jsonParser, deserializationContext) -> reader.readLastUpdated(jsonParser))
                            .put("channel_count", (reader, jsonParser, deserializationContext) -> reader.readChannelCount(jsonParser))
                            .put("error_path", (reader, jsonParser, deserializationContext) -> reader.readErrorPath(jsonParser))
                            .put("status", (reader, jsonParser, deserializationContext) -> reader.readStatus(jsonParser))
                            .build()
            );

    private final StandardObjectDeserializer<AttributeListsView, ?> deserializer;

    public AttributeListsViewDeserializer() {
        deserializer = new StandardObjectDeserializer<AttributeListsView, AttributeListsViewReader>(
                FIELD_PARSERS,
                () -> new AttributeListsViewReader()
        );
    }

    @Override
    public AttributeListsView deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }


}
