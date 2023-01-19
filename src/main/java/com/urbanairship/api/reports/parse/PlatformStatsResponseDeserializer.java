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
import com.urbanairship.api.reports.model.PlatformStatsResponse;

import java.io.IOException;

public class PlatformStatsResponseDeserializer extends JsonDeserializer<PlatformStatsResponse> {
    private static final FieldParserRegistry<PlatformStatsResponse, PlatformStatsResponseReader> FIELD_PARSERS =
            new MapFieldParserRegistry<PlatformStatsResponse, PlatformStatsResponseReader>(
                    ImmutableMap.<String, FieldParser<PlatformStatsResponseReader>>builder()
                            .put("next_page", (reader, jsonParser, deserializationContext) -> reader.readNextPage(jsonParser))
                            .put("opens", (reader, jsonParser, deserializationContext) -> reader.readPlatformStats(jsonParser))
                            .put("timeinapp", (reader, jsonParser, deserializationContext) -> reader.readPlatformStats(jsonParser))
                            .put("optins", (reader, jsonParser, deserializationContext) -> reader.readPlatformStats(jsonParser))
                            .put("optouts", (reader, jsonParser, deserializationContext) -> reader.readPlatformStats(jsonParser))
                            .put("sends", (reader, jsonParser, deserializationContext) -> reader.readPlatformStats(jsonParser))
                            .put("ok", (reader, jsonParser, deserializationContext) -> reader.readOk(jsonParser))
                            .put("error", (reader, jsonParser, deserializationContext) -> reader.readError(jsonParser))
                            .put("details", (reader, jsonParser, deserializationContext) -> reader.readErrorDetails(jsonParser))
                            .build()
            );

    private final StandardObjectDeserializer<PlatformStatsResponse, ?> deserializer;

    public PlatformStatsResponseDeserializer() {
        deserializer = new StandardObjectDeserializer<PlatformStatsResponse, PlatformStatsResponseReader>(
                FIELD_PARSERS,
                () -> new PlatformStatsResponseReader()
        );
    }

    @Override
    public PlatformStatsResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }

}
