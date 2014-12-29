/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.reports.model.PerPushSeriesResponse;
import com.urbanairship.api.reports.model.PlatformCounts;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class PerPushSeriesResponseReader implements JsonObjectReader<PerPushSeriesResponse> {

    private final PerPushSeriesResponse.Builder builder;

    public PerPushSeriesResponseReader() {
        this.builder = PerPushSeriesResponse.newBuilder();
    }

    public void readAppKey(JsonParser jsonParser) throws IOException {
        builder.setAppKey(jsonParser.readValueAs(String.class));
    }

    public void readPushID(JsonParser jsonParser) throws IOException {
        builder.setPushID(jsonParser.readValueAs(UUID.class));
    }

    public void readStart(JsonParser jsonParser) throws IOException {
        String created = jsonParser.readValueAs(String.class);
        builder.setStart(DateFormats.DATE_PARSER.parseDateTime(created));
    }

    public void readEnd(JsonParser jsonParser) throws IOException {
        String created = jsonParser.readValueAs(String.class);
        builder.setEnd(DateFormats.DATE_PARSER.parseDateTime(created));
    }

    public void readPrecision(JsonParser jsonParser) throws IOException {
        builder.setPrecision(jsonParser.readValueAs(String.class));
    }

    public void readCounts(JsonParser jsonParser) throws IOException {
        List<PlatformCounts> counts = jsonParser.readValueAs(new TypeReference<List<PlatformCounts>>() {
        });
        builder.addAllPlatformCounts(counts);
    }

    @Override
    public PerPushSeriesResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
