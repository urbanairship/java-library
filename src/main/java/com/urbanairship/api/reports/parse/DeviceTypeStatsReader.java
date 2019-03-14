package com.urbanairship.api.reports.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.reports.model.DeviceTypeStats;

import java.io.IOException;

public final class DeviceTypeStatsReader implements JsonObjectReader<DeviceTypeStats> {

    private final DeviceTypeStats.Builder builder;

    public DeviceTypeStatsReader() { this.builder = DeviceTypeStats.newBuilder(); }

    public void readOptedIn(JsonParser jsonParser) throws IOException {
        builder.setOptedIn(jsonParser.readValueAs(int.class));
    }

    public void readOptedOut(JsonParser jsonParser) throws IOException {
        builder.setOptedOut(jsonParser.readValueAs(int.class));
    }

    public void readUninstalled(JsonParser jsonParser) throws IOException {
        builder.setUninstalled(jsonParser.readValueAs(int.class));
    }

    public void readUniqueDevices(JsonParser jsonParser) throws IOException {
        builder.setUniqueDevices(jsonParser.readValueAs(int.class));
    }

    @Override
    public DeviceTypeStats validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
