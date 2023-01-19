/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
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
import com.urbanairship.api.templates.model.TemplateResponse;

import java.io.IOException;

public class TemplateResponseDeserializer extends JsonDeserializer<TemplateResponse> {
    private static final FieldParserRegistry<TemplateResponse, TemplateResponseReader> FIELD_PARSER =
            new MapFieldParserRegistry<TemplateResponse, TemplateResponseReader>(
                    ImmutableMap.<String, FieldParser<TemplateResponseReader>>builder()
                            .put("ok", (reader, jsonParser, deserializationContext) -> reader.readOk(jsonParser))
                            .put("operation_id", (reader, jsonParser, deserializationContext) -> reader.readOperationId(jsonParser))
                            .put("template_id", (reader, jsonParser, deserializationContext) -> reader.readTemplateId(jsonParser))
                            .put("push_ids", (reader, jsonParser, deserializationContext) -> reader.addAllPushIds(jsonParser))
                            .put("error", (reader, jsonParser, deserializationContext) -> reader.readError(jsonParser))
                            .put("details", (reader, jsonParser, deserializationContext) -> reader.readErrorDetails(jsonParser))
                            .build()
            );

    private final StandardObjectDeserializer<TemplateResponse, ?> deserializer;

    public TemplateResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<TemplateResponse, TemplateResponseReader>(
                FIELD_PARSER,
                () -> new TemplateResponseReader()
        );
    }

    @Override
    public TemplateResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }


}
