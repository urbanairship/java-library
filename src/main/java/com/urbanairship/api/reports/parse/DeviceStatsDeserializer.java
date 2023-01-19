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
import com.urbanairship.api.reports.model.DeviceStats;

import java.io.IOException;

public final class DeviceStatsDeserializer extends JsonDeserializer<DeviceStats> {

   private static final FieldParserRegistry<DeviceStats, DeviceStatsReader> FIELD_PARSERS = new MapFieldParserRegistry<DeviceStats, DeviceStatsReader>(ImmutableMap.<String, FieldParser<DeviceStatsReader>>builder()
      .put("direct", (reader, jsonParser, deserializationContext) -> reader.readDirect(jsonParser))
       .put("influenced", (reader, jsonParser, deserializationContext) -> reader.readInfluenced(jsonParser))
       .build()
   );

    private final StandardObjectDeserializer<DeviceStats, ?> deserializer;

    public DeviceStatsDeserializer() {
        deserializer = new StandardObjectDeserializer<DeviceStats, DeviceStatsReader>(
                FIELD_PARSERS,
                () -> new DeviceStatsReader()
        );
    }

    @Override
    public DeviceStats deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
