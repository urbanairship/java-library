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
import com.urbanairship.api.reports.model.ResponseReport;

import java.io.IOException;

public class ResponseReportDeserializer extends JsonDeserializer<ResponseReport> {
    private static final FieldParserRegistry<ResponseReport, ResponseReportReader> FIELD_PARSERS =
            new MapFieldParserRegistry<ResponseReport, ResponseReportReader>(
                    ImmutableMap.<String, FieldParser<ResponseReportReader>>builder()
                    .put("next_page", (reader, jsonParser, deserializationContext) -> reader.readNextPage(jsonParser))
                    .put("responses", (reader, jsonParser, deserializationContext) -> reader.readResponseObjects(jsonParser))
                    .put("ok", (reader, jsonParser, deserializationContext) -> reader.readOk(jsonParser))
                    .put("error", (reader, jsonParser, deserializationContext) -> reader.readError(jsonParser))
                    .put("details", (reader, jsonParser, deserializationContext) -> reader.readErrorDetails(jsonParser))
                    .build()
            );

    private final StandardObjectDeserializer<ResponseReport, ?> deserializer;

    public ResponseReportDeserializer() {
        deserializer = new StandardObjectDeserializer<ResponseReport, ResponseReportReader>(
                FIELD_PARSERS,
                () -> new ResponseReportReader()
        );
    }

    @Override
    public ResponseReport deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
