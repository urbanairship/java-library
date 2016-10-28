package com.urbanairship.api.reports.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.reports.model.PlatformStatsResponse;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class PlatformStatsResponseDeserializer extends JsonDeserializer<PlatformStatsResponse> {
    private static final FieldParserRegistry<PlatformStatsResponse, PlatformStatsResponseReader> FIELD_PARSERS =
            new MapFieldParserRegistry<PlatformStatsResponse, PlatformStatsResponseReader>(
                    ImmutableMap.<String, FieldParser<PlatformStatsResponseReader>>builder()
                            .put("next_page", new FieldParser<PlatformStatsResponseReader>() {
                                @Override
                                public void parse(PlatformStatsResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readNextPage(jsonParser);
                                }
                            })
                            .put("opens", new FieldParser<PlatformStatsResponseReader>() {
                                @Override
                                public void parse(PlatformStatsResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readPlatformStats(jsonParser);
                                }
                            })
                            .put("timeinapp", new FieldParser<PlatformStatsResponseReader>() {
                                @Override
                                public void parse(PlatformStatsResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readPlatformStats(jsonParser);
                                }
                            })
                            .put("optins", new FieldParser<PlatformStatsResponseReader>() {
                                @Override
                                public void parse(PlatformStatsResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readPlatformStats(jsonParser);
                                }
                            })
                            .put("optouts", new FieldParser<PlatformStatsResponseReader>() {
                                @Override
                                public void parse(PlatformStatsResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readPlatformStats(jsonParser);
                                }
                            })
                            .put("sends", new FieldParser<PlatformStatsResponseReader>() {
                                @Override
                                public void parse(PlatformStatsResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readPlatformStats(jsonParser);
                                }
                            })
                            .build()
            );

    private final StandardObjectDeserializer<PlatformStatsResponse, ?> deserializer;

    public PlatformStatsResponseDeserializer() {
        deserializer = new StandardObjectDeserializer<PlatformStatsResponse, PlatformStatsResponseReader>(
                FIELD_PARSERS,
                new Supplier<PlatformStatsResponseReader>() {
                    @Override
                    public PlatformStatsResponseReader get() {
                        return new PlatformStatsResponseReader();
                    }
                }
        );
    }

    @Override
    public PlatformStatsResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }

}
