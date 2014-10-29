package com.urbanairship.api.reports.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.*;
import com.urbanairship.api.reports.model.AppStats;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class AppStatsDeserializer extends JsonDeserializer<AppStats> {

    private static final FieldParserRegistry<AppStats, AppStatsReader> FIELD_PARSERS =
            new MapFieldParserRegistry<AppStats, AppStatsReader>(ImmutableMap.<String, FieldParser<AppStatsReader>>builder()
            .put("start", new FieldParser<AppStatsReader>() {
                @Override
                public void parse(AppStatsReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readStartTime(jsonParser);
                }
            })
            .put("messages", new FieldParser<AppStatsReader>() {
                @Override
                public void parse(AppStatsReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readiOSCount(jsonParser);
                }
            })
            .put("bb_messages", new FieldParser<AppStatsReader>() {
                @Override
                public void parse(AppStatsReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readBlackBerryCount(jsonParser);
                }
            })
            .put("c2dm_messages", new FieldParser<AppStatsReader>() {
                @Override
                public void parse(AppStatsReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readC2DMCount(jsonParser);
                }
            })
            .put("gcm_messages", new FieldParser<AppStatsReader>() {
                @Override
                public void parse(AppStatsReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readGCMCount(jsonParser);
                }
            })
            .put("wns_messages", new FieldParser<AppStatsReader>() {
                @Override
                public void parse(AppStatsReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readWindows8Count(jsonParser);
                }
            })
            .put("mpns_messages", new FieldParser<AppStatsReader>() {
                @Override
                public void parse(AppStatsReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readWindowsPhone8Count(jsonParser);
                }
            })
            .build()
    );

    private final StandardObjectDeserializer<AppStats, ?> deserializer;

    public AppStatsDeserializer() {
        deserializer = new StandardObjectDeserializer<AppStats, AppStatsReader>(
            FIELD_PARSERS,
            new Supplier<AppStatsReader>() {
                @Override
                public AppStatsReader get() {
                    return new AppStatsReader();
                }
            }
        );
    }

    @Override
    public AppStats deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
