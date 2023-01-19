/*
 * Copyright (c) 2013-2016. Urban Airship and Contributors
 */

package com.urbanairship.api.templates.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.templates.model.TemplateView;

import java.io.IOException;

public final class TemplateViewDeserializer extends JsonDeserializer<TemplateView> {
    private static final FieldParserRegistry<TemplateView, TemplateViewReader> FIELD_PARSER =
            new MapFieldParserRegistry<TemplateView, TemplateViewReader>(
                    ImmutableMap.<String, FieldParser<TemplateViewReader>>builder()
                    .put("id", (reader, jsonParser, deserializationContext) -> reader.readId(jsonParser))
                    .put("created_at", (reader, jsonParser, deserializationContext) -> reader.readCreatedAt(jsonParser))
                    .put("modified_at", (reader, jsonParser, deserializationContext) -> reader.readModifiedAt(jsonParser))
                    .put("last_used", (reader, jsonParser, deserializationContext) -> reader.readLastUsed(jsonParser))
                    .put("name", (reader, jsonParser, deserializationContext) -> reader.readName(jsonParser))
                    .put("description", (reader, jsonParser, deserializationContext) -> reader.readDescription(jsonParser))
                    .put("variables", (reader, jsonParser, deserializationContext) -> reader.readVariables(jsonParser))
                    .put("push", (reader, jsonParser, deserializationContext) -> reader.readPartialPush(jsonParser))
                    .build()
            );

    private final StandardObjectDeserializer<TemplateView, ?> deserializer;

    public TemplateViewDeserializer() {
        this.deserializer = new StandardObjectDeserializer<TemplateView, TemplateViewReader>(
                FIELD_PARSER,
                () -> new TemplateViewReader()
        );
    }

    @Override
    public TemplateView deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
