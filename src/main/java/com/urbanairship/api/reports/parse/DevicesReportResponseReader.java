package com.urbanairship.api.reports.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.reports.model.DeviceTypeStats;
import com.urbanairship.api.reports.model.DevicesReportResponse;

import java.io.IOException;

public class DevicesReportResponseReader implements JsonObjectReader<DevicesReportResponse> {

    private final DevicesReportResponse.Builder builder;

    public DevicesReportResponseReader() { this.builder = DevicesReportResponse.newBuilder(); }

    public void readDeviceStats(JsonParser jsonParser, String value ) throws IOException {
        builder.addDeviceTypeStatsMapping(value, jsonParser.readValueAs(DeviceTypeStats.class));
    }

    @Override
    public DevicesReportResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        }
        catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
