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
import com.urbanairship.api.reports.model.DevicesReport;

import java.io.IOException;

public class DevicesReportDeserializer extends JsonDeserializer<DevicesReport> {
    private static final FieldParserRegistry<DevicesReport, DevicesReportReader> FIELD_PARSERS =
            new MapFieldParserRegistry<DevicesReport, DevicesReportReader>(
                    ImmutableMap.<String, FieldParser<DevicesReportReader>>builder()
                            .put("date_closed", new FieldParser<DevicesReportReader>() {
                                @Override
                                public void parse(DevicesReportReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readDateClosed(jsonParser);
                                }
                            })
                            .put("date_computed", new FieldParser<DevicesReportReader>() {
                                @Override
                                public void parse(DevicesReportReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readDateComputed(jsonParser);
                                }
                            })
                            .put("total_unique_devices", new FieldParser<DevicesReportReader>() {
                                @Override
                                public void parse(DevicesReportReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readTotalUniqueDevices(jsonParser);
                                }
                            })
                            .put("counts", new FieldParser<DevicesReportReader>() {
                                @Override
                                public void parse(DevicesReportReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readResponseObjects(jsonParser);
                                }
                            })
                            .build()
            );

    private final StandardObjectDeserializer<DevicesReport, ?> deserializer;

    public DevicesReportDeserializer() {
        deserializer = new StandardObjectDeserializer<DevicesReport, DevicesReportReader>(
                FIELD_PARSERS,
                new Supplier<DevicesReportReader>() {
                    @Override
                    public DevicesReportReader get() {
                        return new DevicesReportReader();
                    }
                }
        );
    }

    @Override
    public DevicesReport deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
