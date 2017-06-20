/*
 * Copyright (c) 2013-2017.  Urban Airship and Contributors
 */

package com.urbanairship.api.experiments.parse;

import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.experiments.model.ExperimentResponse;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;
import com.google.common.base.Supplier;

public class ExperimentResponseDeserializer extends JsonDeserializer<ExperimentResponse> {

    private static final FieldParserRegistry<ExperimentResponse, ExperimentResponseReader> FIELD_PARSERS =
            new MapFieldParserRegistry<ExperimentResponse, ExperimentResponseReader>(
                    ImmutableMap.<String, FieldParser<ExperimentResponseReader>>builder()
                            .put("operation_id", new FieldParser<ExperimentResponseReader>() {
                                @Override
                                public void parse(ExperimentResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readOperationId(jsonParser);
                                }
                            })
                            .put("push_ids", new FieldParser<ExperimentResponseReader>() {
                                @Override
                                public void parse(ExperimentResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readPushIds(jsonParser);
                                }
                            })
                            .put("ok", new FieldParser<ExperimentResponseReader>() {
                                @Override
                                public void parse(ExperimentResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readOk(jsonParser);
                                }
                            })
                            .put("experiment_id", new FieldParser<ExperimentResponseReader>() {
                                @Override
                                public void parse(ExperimentResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readExperimentId(jsonParser);
                                }
                            })
                            .build()
            );

    private final StandardObjectDeserializer<ExperimentResponse, ?> deserializer;

    // See Google Guava for Supplier details
    public ExperimentResponseDeserializer() {
        deserializer = new StandardObjectDeserializer<ExperimentResponse, ExperimentResponseReader>(
                FIELD_PARSERS,
                new Supplier<ExperimentResponseReader>() {
                    @Override
                    public ExperimentResponseReader get() {
                        return new ExperimentResponseReader();
                    }
                }

        );
    }

    @Override
    public ExperimentResponse deserialize(JsonParser parser, DeserializationContext deserializationContext)
            throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
