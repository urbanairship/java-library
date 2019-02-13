package com.urbanairship.api.reports.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.reports.model.DeviceStats;

import java.io.IOException;

public final class DeviceStatsReader implements JsonObjectReader<DeviceStats> {

    private final DeviceStats.Builder builder;

    public DeviceStatsReader() { this.builder = DeviceStats.newBuilder(); }

    public void readDirect(JsonParser jsonParser) throws IOException {
        builder.setDirect(jsonParser.readValueAs(int.class));
    }

    public void readInfluenced(JsonParser jsonParser) throws IOException {
        builder.setInfluenced(jsonParser.readValueAs(int.class));
    }

    @Override
    public DeviceStats validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
