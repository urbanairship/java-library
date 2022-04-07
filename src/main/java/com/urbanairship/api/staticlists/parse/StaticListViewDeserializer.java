/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.staticlists.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.staticlists.model.StaticListView;

import java.io.IOException;

public class StaticListViewDeserializer extends JsonDeserializer<StaticListView> {
    private static final FieldParserRegistry<StaticListView, StaticListViewReader> FIELD_PARSERS =
            new MapFieldParserRegistry<StaticListView, StaticListViewReader>(
                    ImmutableMap.<String, FieldParser<StaticListViewReader>>builder()
                            .put("ok", (reader, jsonParser, deserializationContext) -> reader.readOk(jsonParser))
                            .put("name", (reader, jsonParser, deserializationContext) -> reader.readName(jsonParser))
                            .put("description", (reader, jsonParser, deserializationContext) -> reader.readDescription(jsonParser))
                            .put("extra", (reader, jsonParser, deserializationContext) -> reader.readExtras(jsonParser))
                            .put("created", (reader, jsonParser, deserializationContext) -> reader.readCreated(jsonParser))
                            .put("last_updated", (reader, jsonParser, deserializationContext) -> reader.readLastUpdated(jsonParser))
                            .put("channel_count", (reader, jsonParser, deserializationContext) -> reader.readChannelCount(jsonParser))
                            .put("status", (reader, jsonParser, deserializationContext) -> reader.readStatus(jsonParser))
                            .put("error", (reader, jsonParser, deserializationContext) -> reader.readError(jsonParser))
                            .put("details", (reader, jsonParser, deserializationContext) -> reader.readErrorDetails(jsonParser))
                            .build()
            );

    private final StandardObjectDeserializer<StaticListView, ?> deserializer;

    public StaticListViewDeserializer() {
        deserializer = new StandardObjectDeserializer<>(
                FIELD_PARSERS,
                () -> new StaticListViewReader()
        );
    }

    @Override
    public StaticListView deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }


}
