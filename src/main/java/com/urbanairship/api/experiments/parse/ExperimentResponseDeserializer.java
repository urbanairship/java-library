/*
 * Copyright (c) 2013-2017.  Urban Airship and Contributors
 */

package com.urbanairship.api.experiments.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.experiments.model.ExperimentResponse;

import java.io.IOException;

public class ExperimentResponseDeserializer extends JsonDeserializer<ExperimentResponse> {

    private static final FieldParserRegistry<ExperimentResponse, ExperimentResponseReader> FIELD_PARSERS =
            new MapFieldParserRegistry<ExperimentResponse, ExperimentResponseReader>(
                    ImmutableMap.<String, FieldParser<ExperimentResponseReader>>builder()
                            .put("operation_id", (reader, jsonParser, deserializationContext) -> reader.readOperationId(jsonParser))
                            .put("push_id", (reader, jsonParser, deserializationContext) -> reader.readPushId(jsonParser))
                            .put("ok", (reader, jsonParser, deserializationContext) -> reader.readOk(jsonParser))
                            .put("experiment_id", (reader, jsonParser, deserializationContext) -> reader.readExperimentId(jsonParser))
                            .put("error", (reader, jsonParser, deserializationContext) -> reader.readError(jsonParser))
                            .put("details", (reader, jsonParser, deserializationContext) -> reader.readErrorDetails(jsonParser))
                            .build()
            );

    private final StandardObjectDeserializer<ExperimentResponse, ?> deserializer;

    // See Google Guava for Supplier details
    public ExperimentResponseDeserializer() {
        deserializer = new StandardObjectDeserializer<ExperimentResponse, ExperimentResponseReader>(
                FIELD_PARSERS,
                () -> new ExperimentResponseReader()

        );
    }

    @Override
    public ExperimentResponse deserialize(JsonParser parser, DeserializationContext deserializationContext)
            throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
