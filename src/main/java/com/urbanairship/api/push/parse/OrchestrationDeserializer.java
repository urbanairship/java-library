/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.Orchestration;

import java.io.IOException;

public class OrchestrationDeserializer extends JsonDeserializer<Orchestration> {

    private static final FieldParserRegistry<Orchestration, OrchestrationReader> FIELD_PARSERS = new MapFieldParserRegistry<>(ImmutableMap.<String, FieldParser<OrchestrationReader>>builder()
            .put("type", (reader, parser, context) -> reader.readOrchestrationType(parser))
            .put("channel_priority", (reader, parser, context) -> reader.readOrchestrationChannelPriority(parser))
            .build()
    );

    private final StandardObjectDeserializer<Orchestration, ?> deserializer;

    public OrchestrationDeserializer() {
        deserializer = new StandardObjectDeserializer<>(
                FIELD_PARSERS,
                () -> new OrchestrationReader()
        );
    }

    @Override
    public Orchestration deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
