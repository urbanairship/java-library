/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.reports.model.PlatformStats;

import java.io.IOException;

public final class PlatformStatsReader implements JsonObjectReader<PlatformStats> {

    private final PlatformStats.Builder builder;

    public PlatformStatsReader() {
        this.builder = PlatformStats.newBuilder();
    }

    public void readAndroid(JsonParser jsonParser) throws IOException {
        builder.setAndroid(jsonParser.readValueAs(int.class));
    }

    public void readIOS(JsonParser jsonParser) throws IOException {
        builder.setIOS(jsonParser.readValueAs(int.class));
    }

    public void readDate(JsonParser jsonParser) throws IOException {
        String created = jsonParser.readValueAs(String.class);
        builder.setDate(DateFormats.DATE_PARSER.parseDateTime(created));
    }

    @Override
    public PlatformStats validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
