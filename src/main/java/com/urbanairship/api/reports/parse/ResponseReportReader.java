package com.urbanairship.api.reports.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.urbanairship.api.common.model.ErrorDetails;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.reports.model.ResponseReport;
import com.urbanairship.api.reports.model.ResponseReportResponse;

import java.io.IOException;
import java.util.List;

public class ResponseReportReader implements JsonObjectReader<ResponseReport> {
    private final ResponseReport.Builder builder;

    public ResponseReportReader() { this.builder = ResponseReport.newBuilder(); }

    public void readNextPage(JsonParser jsonParser) throws IOException {
        builder.setNextPage(jsonParser.readValueAs(String.class));
    }

    public void readResponseObjects(JsonParser jsonParser) throws IOException {
        builder.addResponseObject((List<ResponseReportResponse>) jsonParser.readValueAs(new TypeReference<List<ResponseReportResponse>>() {}));
    }

    public void readOk(JsonParser jsonParser) throws IOException {
        builder.setOk(jsonParser.readValueAs(Boolean.class));
    }

    public void readError(JsonParser jsonParser) throws IOException {
        builder.setError(jsonParser.readValueAs(String.class));
    }

    public void readErrorDetails(JsonParser jsonParser) throws IOException {
        builder.setErrorDetails(jsonParser.readValueAs(ErrorDetails.class));
    }

    @Override
    public ResponseReport validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}