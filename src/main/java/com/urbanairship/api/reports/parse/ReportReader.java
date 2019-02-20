package com.urbanairship.api.reports.parse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.reports.model.Report;
import com.urbanairship.api.reports.model.Response;
import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.APIParsingException;

import java.io.IOException;
import java.util.List;

public class ReportReader implements JsonObjectReader<Report> {
    private final Report.Builder builder;

    public ReportReader() { this.builder = Report.newBuilder(); }

    public void readNextPage(JsonParser jsonParser) throws IOException {
        builder.setNextPage(jsonParser.readValueAs(String.class));
    }

    public void readResponseObjects(JsonParser jsonParser) throws IOException {
        builder.addResponseObject((List<Response>) jsonParser.readValueAs(new TypeReference<List<Response>>() {}));
    }

    @Override
    public Report validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}