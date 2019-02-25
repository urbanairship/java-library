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

public class ReportDeserializer extends JsonDeserializer<ResponseReport> {
    private static final FieldParserRegistry<ResponseReport, ReportReader> FIELD_PARSERS =
            new MapFieldParserRegistry<ResponseReport, ReportReader>(
                    ImmutableMap.<String, FieldParser<ReportReader>>builder()
                    .put("next_page", new FieldParser<ReportReader>() {
                        @Override
                        public void parse(ReportReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readNextPage(jsonParser);
                        }
                    })
                    .put("responses", new FieldParser<ReportReader>() {
                        @Override
                        public void parse(ReportReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                           reader.readResponseObjects(jsonParser);
                        }
                    })
                    .build()
            );

    private final StandardObjectDeserializer<ResponseReport, ?> deserializer;

    public ReportDeserializer() {
        deserializer = new StandardObjectDeserializer<ResponseReport, ReportReader>(
                FIELD_PARSERS,
                new Supplier<ReportReader>() {
                    @Override
                    public ReportReader get() {
                        return new ReportReader();
                    }
                }
        );
    }

    @Override
    public ResponseReport deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
