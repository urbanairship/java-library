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
import com.urbanairship.api.experiments.model.Experiment;

import java.io.IOException;

public class ExperimentDeserializer extends JsonDeserializer<Experiment> {

    private static final FieldParserRegistry<Experiment, ExperimentReader> FIELD_PARSERS = new MapFieldParserRegistry<Experiment, ExperimentReader>(
            ImmutableMap.<String, FieldParser<ExperimentReader>>builder()
                    .put("name", (reader, jsonParser, deserializationContext) -> reader.readName(jsonParser))
                    .put("description", (reader, jsonParser, deserializationContext) -> reader.readDescription(jsonParser))
                    .put("control", (reader, jsonParser, deserializationContext) -> reader.readControl(jsonParser))
                    .put("audience", (reader, jsonParser, deserializationContext) -> reader.readAudience(jsonParser))
                    .put("device_types", (reader, jsonParser, deserializationContext) -> reader.readDeviceTypes(jsonParser))
                    .put("variants", (reader, jsonParser, deserializationContext) -> reader.readVariants(jsonParser))
                    .build()
    );

    private final StandardObjectDeserializer<Experiment, ?> deserializer;

    public ExperimentDeserializer() {
        deserializer = new StandardObjectDeserializer<Experiment, ExperimentReader>(
                FIELD_PARSERS,
                () -> new ExperimentReader()
        );
    }

    @Override
    public Experiment deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
