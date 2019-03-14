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
import com.urbanairship.api.reports.model.DeviceTypeStats;

import java.io.IOException;

public final class DeviceTypeStatsDeserializer extends JsonDeserializer<DeviceTypeStats> {

    private static final FieldParserRegistry<DeviceTypeStats, DeviceTypeStatsReader> FIELD_PARSERS = new MapFieldParserRegistry<DeviceTypeStats, DeviceTypeStatsReader>(ImmutableMap.<String, FieldParser<DeviceTypeStatsReader>>builder()
            .put("opted_in", new FieldParser<DeviceTypeStatsReader>() {
                @Override
                public void parse(DeviceTypeStatsReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readOptedIn(jsonParser);
                }
            })
            .put("opted_out", new FieldParser<DeviceTypeStatsReader>() {
                @Override
                public void parse(DeviceTypeStatsReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readOptedOut(jsonParser);
                }
            })
            .put("uninstalled", new FieldParser<DeviceTypeStatsReader>() {
                @Override
                public void parse(DeviceTypeStatsReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readUninstalled(jsonParser);
                }
            })
            .put("unique_devices", new FieldParser<DeviceTypeStatsReader>() {
                @Override
                public void parse(DeviceTypeStatsReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readUniqueDevices(jsonParser);
                }
            })
            .build()
    );

    private final StandardObjectDeserializer<DeviceTypeStats, ?> deserializer;

    public DeviceTypeStatsDeserializer() {
        deserializer = new StandardObjectDeserializer<DeviceTypeStats, DeviceTypeStatsReader>(
                FIELD_PARSERS,
                new Supplier<DeviceTypeStatsReader>() {
                    @Override
                    public DeviceTypeStatsReader get() {
                        return new DeviceTypeStatsReader();
                    }
                }
        );
    }

    @Override
    public DeviceTypeStats deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
