package com.urbanairship.api.reports.parse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.reports.model.DevicesReport;
import com.urbanairship.api.reports.model.DevicesReportResponse;
import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.APIParsingException;

import java.io.IOException;

public class DevicesReportReader implements JsonObjectReader<DevicesReport> {
    private final DevicesReport.Builder builder;

    public DevicesReportReader() { this.builder = DevicesReport.newBuilder(); }

    public void readDateClosed(JsonParser jsonParser) throws IOException {
        builder.setDateClosed(jsonParser.readValueAs(String.class));
    }

    public void readDateComputed(JsonParser jsonParser) throws IOException {
        builder.setDateComputed(jsonParser.readValueAs(String.class));
    }

    public void readTotalUniqueDevices(JsonParser jsonParser) throws IOException {
        builder.setTotalUniqueDevices(jsonParser.readValueAs(int.class));
    }

    public void readResponseObjects(JsonParser jsonParser) throws IOException {
        builder.addDevicesReportResponseObject(jsonParser.readValueAs(new TypeReference<DevicesReportResponse>() {}));
    }

    @Override
    public DevicesReport validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
