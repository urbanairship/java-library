package com.urbanairship.api.reports.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.reports.model.DeviceStats;
import com.urbanairship.api.reports.model.ResponseReportResponse;

import java.io.IOException;

public class ResponseReportResponseReader implements JsonObjectReader<ResponseReportResponse> {

    private final ResponseReportResponse.Builder builder;

    public ResponseReportResponseReader() { this.builder = ResponseReportResponse.newBuilder(); }

    public void readDate(JsonParser jsonParser) throws IOException {
        String date = jsonParser.readValueAs(String.class);
        builder.setDate(DateFormats.DATE_PARSER.parseDateTime(date));
    }

    public void readDeviceStats(JsonParser jsonParser, String value ) throws IOException {
        builder.addDeviceStatsMapping(value, jsonParser.readValueAs(DeviceStats.class));
    }

   @Override
   public ResponseReportResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        }
        catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
   }
}
