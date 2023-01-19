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
import com.urbanairship.api.reports.model.DevicesReportResponse;

import java.io.IOException;

public class DevicesReportResponseDeserializer extends JsonDeserializer<DevicesReportResponse> {
    private static final FieldParserRegistry<DevicesReportResponse, DevicesReportResponseReader> FIELD_PARSERS =
            new MapFieldParserRegistry<DevicesReportResponse, DevicesReportResponseReader>(
                    ImmutableMap.<String, FieldParser<DevicesReportResponseReader>>builder()
                            .put("android", (reader, jsonParser, deserializationContext) -> reader.readDeviceStats(jsonParser, "android"))
                            .put("amazon", (reader, jsonParser, deserializationContext) -> reader.readDeviceStats(jsonParser, "amazon"))
                            .put("ios", (reader, jsonParser, deserializationContext) -> reader.readDeviceStats(jsonParser, "ios"))
                            .put("sms", (reader, jsonParser, deserializationContext) -> reader.readDeviceStats(jsonParser, "sms"))
                            .put("email", (reader, jsonParser, deserializationContext) -> reader.readDeviceStats(jsonParser, "email"))
                            .build()
            );

    private final StandardObjectDeserializer<DevicesReportResponse, ?> deserializer;

    public DevicesReportResponseDeserializer() {
        deserializer = new StandardObjectDeserializer<DevicesReportResponse, DevicesReportResponseReader>(
                FIELD_PARSERS,
                () -> new DevicesReportResponseReader()
        );
    }

    public DevicesReportResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
