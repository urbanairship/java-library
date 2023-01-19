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
import com.urbanairship.api.templates.model.TemplateListingResponse;

import java.io.IOException;

public class TemplateListingResponseDeserializer extends JsonDeserializer<TemplateListingResponse> {
    private static final FieldParserRegistry<TemplateListingResponse, TemplateListingResponseReader> FIELD_PARSER =
            new MapFieldParserRegistry<TemplateListingResponse, TemplateListingResponseReader>(
                    ImmutableMap.<String, FieldParser<TemplateListingResponseReader>>builder()
                            .put("ok", (reader, jsonParser, deserializationContext) -> reader.readOk(jsonParser))
                            .put("template", (reader, jsonParser, deserializationContext) -> reader.readTemplate(jsonParser))
                            .put("templates", (reader, jsonParser, deserializationContext) -> reader.readAllTemplates(jsonParser))
                            .put("count", (reader, jsonParser, deserializationContext) -> reader.readCount(jsonParser))
                            .put("total_count", (reader, jsonParser, deserializationContext) -> reader.readTotalCount(jsonParser))
                            .put("next_page", (reader, jsonParser, deserializationContext) -> reader.readNextPage(jsonParser))
                            .put("prev_page", (reader, jsonParser, deserializationContext) -> reader.readPrevPage(jsonParser))
                            .put("error", (reader, jsonParser, deserializationContext) -> reader.readError(jsonParser))
                            .put("details", (reader, jsonParser, deserializationContext) -> reader.readErrorDetails(jsonParser))
                            .build()
            );

    private final StandardObjectDeserializer<TemplateListingResponse, ?> deserializer;

    public TemplateListingResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<TemplateListingResponse, TemplateListingResponseReader>(
                FIELD_PARSER,
                () -> new TemplateListingResponseReader()
        );
    }

    @Override
    public TemplateListingResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
