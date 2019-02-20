package com.urbanairship.api.reports.parse;

import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.reports.model.DeviceStats;
import com.urbanairship.api.reports.model.Response;
import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.APIParsingException;

import java.io.IOException;

public class ResponseReader implements JsonObjectReader<Response> {

    private final Response.Builder builder;

    public ResponseReader() { this.builder = Response.newBuilder(); }

    public void readDate(JsonParser jsonParser) throws IOException {
        String date = jsonParser.readValueAs(String.class);
        builder.setDate(DateFormats.DATE_PARSER.parseDateTime(date));
    }

    public void readDeviceStats(JsonParser jsonParser, String value ) throws IOException {
        builder.addDeviceStatsMapping(value, jsonParser.readValueAs(DeviceStats.class));
    }

   @Override
   public Response validateAndBuild() throws IOException {
        try {
            return builder.build();
        }
        catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
   }
}
