/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.reports.model.PushListingResponse;

import java.io.IOException;

public final class PushListingResponseDeserializer extends JsonDeserializer<PushListingResponse> {

    private static final FieldParserRegistry<PushListingResponse, PushListingResponseReader> FIELD_PARSER =
            new MapFieldParserRegistry<PushListingResponse, PushListingResponseReader>(
                    ImmutableMap.<String, FieldParser<PushListingResponseReader>>builder()
                            .put("next_page", (reader, jsonParser, deserializationContext) -> reader.readNextPage(jsonParser))
                            .put("pushes", (reader, jsonParser, deserializationContext) -> reader.readPushInfoObjects(jsonParser))
                            .put("ok", (reader, jsonParser, deserializationContext) -> reader.readOk(jsonParser))
                            .put("error", (reader, jsonParser, deserializationContext) -> reader.readError(jsonParser))
                            .put("details", (reader, jsonParser, deserializationContext) -> reader.readErrorDetails(jsonParser))
                            .build()
            );

    private final StandardObjectDeserializer<PushListingResponse, ?> deserializer;

    public PushListingResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<PushListingResponse, PushListingResponseReader>(
                FIELD_PARSER,
                () -> new PushListingResponseReader()
        );
    }

    @Override
    public PushListingResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }

}
