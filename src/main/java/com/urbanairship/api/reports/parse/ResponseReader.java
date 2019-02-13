package com.urbanairship.api.reports.parse;

import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.reports.model.DeviceStats;
import com.urbanairship.api.reports.model.Response;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.urbanairship.api.common.parse.APIParsingException;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.Map;

public class ResponseReader implements JsonObjectReader<Response> {

    private final Response.Builder builder;

    public ResponseReader() { this.builder = Response.newBuilder(); }

    public void readDate(JsonParser jsonParser) throws IOException {
        builder.setDate(jsonParser.readValueAs(DateTime.class));
    }

    public void readDeviceStats(JsonParser jsonParser) throws IOException {
        builder.addDeviceStatsMapping( (Map<String, DeviceStats>) jsonParser.readValueAs(new TypeReference<Map<String, DeviceStats>>(){}));
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
