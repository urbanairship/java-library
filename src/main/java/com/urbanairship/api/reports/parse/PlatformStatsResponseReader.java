package com.urbanairship.api.reports.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.reports.model.PlatformStats;
import com.urbanairship.api.reports.model.PlatformStatsResponse;

import java.io.IOException;
import java.util.List;

public class PlatformStatsResponseReader implements JsonObjectReader<PlatformStatsResponse> {
    private final PlatformStatsResponse.Builder builder;

    public PlatformStatsResponseReader() {
        this.builder = PlatformStatsResponse.newBuilder();
    }

    public void readNextPage(JsonParser jsonParser) throws IOException {
        builder.setNextPage(jsonParser.readValueAs(String.class));
    }

    public void readPlatformStats(JsonParser jsonParser) throws IOException {
        builder.addPlatformStatsObjects((List<PlatformStats>) jsonParser.readValueAs(new TypeReference<List<PlatformStats>>() {
        }));
    }

    @Override
    public PlatformStatsResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }

}
