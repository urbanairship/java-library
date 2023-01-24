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
import com.urbanairship.api.templates.model.PartialPushPayload;

import java.io.IOException;

public class PartialPushPayloadDeserializer extends JsonDeserializer<PartialPushPayload> {
    private static final FieldParserRegistry<PartialPushPayload, PartialPushPayloadReader> FIELD_PARSER =
            new MapFieldParserRegistry<PartialPushPayload, PartialPushPayloadReader>(
                    ImmutableMap.<String, FieldParser<PartialPushPayloadReader>>builder()
                            .put("notification", (reader, jsonParser, deserializationContext) -> reader.readNotification(jsonParser))
                            .put("options", (reader, jsonParser, deserializationContext) -> reader.readPushOptions(jsonParser))
                            .put("message", (reader, jsonParser, deserializationContext) -> reader.readRichPushMessage(jsonParser))
                            .put("in_app", (reader, jsonParser, deserializationContext) -> reader.readInApp(jsonParser))
                            .build()
            );

    private final StandardObjectDeserializer<PartialPushPayload, ?> deserializer;

    public PartialPushPayloadDeserializer() {
        this.deserializer = new StandardObjectDeserializer<PartialPushPayload, PartialPushPayloadReader>(
                FIELD_PARSER,
                () -> new PartialPushPayloadReader()
        );
    }

    @Override
    public PartialPushPayload deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }

}
