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
import com.urbanairship.api.reports.model.ResponseReportResponse;

import java.io.IOException;

public class ResponseReportResponseDeserializer extends JsonDeserializer<ResponseReportResponse> {
    private static final FieldParserRegistry<ResponseReportResponse, ResponseReportResponseReader> FIELD_PARSERS =
            new MapFieldParserRegistry<ResponseReportResponse, ResponseReportResponseReader>(
                    ImmutableMap.<String, FieldParser<ResponseReportResponseReader>>builder()
                            .put("android", (reader, jsonParser, deserializationContext) -> reader.readDeviceStats(jsonParser, "android"))
                            .put("date", (reader, jsonParser, deserializationContext) -> reader.readDate(jsonParser))
                            .put("ios", (reader, jsonParser, deserializationContext) -> reader.readDeviceStats(jsonParser, "ios"))
                            .build()
            );

    private final StandardObjectDeserializer<ResponseReportResponse, ?> deserializer;

    public ResponseReportResponseDeserializer() {
        deserializer = new StandardObjectDeserializer<ResponseReportResponse, ResponseReportResponseReader>(
                FIELD_PARSERS,
                () -> new ResponseReportResponseReader()
        );
    }

    public ResponseReportResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
