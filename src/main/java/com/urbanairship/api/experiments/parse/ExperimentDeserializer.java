package com.urbanairship.api.experiments.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import com.urbanairship.api.experiments.model.Experiment;

import java.io.IOException;

public class ExperimentDeserializer extends JsonDeserializer<Experiment> {

    private static final FieldParserRegistry<Experiment, ExperimentReader> FIELD_PARSERS = new MapFieldParserRegistry<Experiment, ExperimentReader>(

            ImmutableMap.<String, FieldParser<ExperimentReader>>builder()
                    .put("name", new FieldParser<ExperimentReader>() {
                        @Override
                        public void parse(ExperimentReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readName(jsonParser);
                        }
                    })
                    .put("description", new FieldParser<ExperimentReader>() {
                        @Override
                        public void parse(ExperimentReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readDescription(jsonParser);
                        }
                    })
                    .put("control", new FieldParser<ExperimentReader>() {
                        @Override
                        public void parse(ExperimentReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readControl(jsonParser);
                        }
                    })
                    .put("audience", new FieldParser<ExperimentReader>() {
                        @Override
                        public void parse(ExperimentReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readAudience(jsonParser);
                        }
                    })
                    .put("device_types", new FieldParser<ExperimentReader>() {
                        @Override
                        public void parse(ExperimentReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readDeviceTypes(jsonParser);
                        }
                    })
                    .put("variants", new FieldParser<ExperimentReader>() {
                        @Override
                        public void parse(ExperimentReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readVariants(jsonParser);
                        }
                    })
                    .build()
    );

    public static final ExperimentDeserializer INSTANCE = new ExperimentDeserializer();

    private final StandardObjectDeserializer<Experiment, ?> deserializer;

    public ExperimentDeserializer() {
        deserializer = new StandardObjectDeserializer<Experiment, ExperimentReader>(
                FIELD_PARSERS,
                new Supplier<ExperimentReader>() {
                    @Override
                    public ExperimentReader get() {
                        return new ExperimentReader();
                    }
                }
        );
    }

    @Override
    public Experiment deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
