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
import com.urbanairship.api.reports.model.PushInfoResponse;

import java.io.IOException;

public final class PushInfoResponseDeserializer extends JsonDeserializer<PushInfoResponse> {

    public static final FieldParserRegistry<PushInfoResponse, PushInfoResponseReader> FIELD_PARSER =
            new MapFieldParserRegistry<PushInfoResponse, PushInfoResponseReader>(
                    ImmutableMap.<String, FieldParser<PushInfoResponseReader>>builder()
                            .put("push_uuid", (reader, jsonParser, deserializationContext) -> reader.readPushUUID(jsonParser))
                            .put("direct_responses", (reader, jsonParser, deserializationContext) -> reader.readDirectResponses(jsonParser))
                            .put("sends", (reader, jsonParser, deserializationContext) -> reader.readSends(jsonParser))
                            .put("push_type", (reader, jsonParser, deserializationContext) -> reader.readPushType(jsonParser))
                            .put("push_time", (reader, jsonParser, deserializationContext) -> reader.readPushTime(jsonParser))
                            .put("group_id", (reader, jsonParser, deserializationContext) -> reader.readGroupID(jsonParser))
                            .put("ok", (reader, jsonParser, deserializationContext) -> reader.readOk(jsonParser))
                            .put("error", (reader, jsonParser, deserializationContext) -> reader.readError(jsonParser))
                            .put("details", (reader, jsonParser, deserializationContext) -> reader.readErrorDetails(jsonParser))
                            .build()
            );

    private final StandardObjectDeserializer<PushInfoResponse, ?> deserializer;

    public PushInfoResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<PushInfoResponse, PushInfoResponseReader>(
                FIELD_PARSER,
                () -> new PushInfoResponseReader()
        );
    }

    @Override
    public PushInfoResponse deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }

}
