/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.reports.model.AppStats;
import org.codehaus.jackson.JsonParser;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.IOException;

public final class AppStatsReader implements JsonObjectReader<AppStats> {

    private final AppStats.Builder builder;

    public AppStatsReader() {
        this.builder = AppStats.newBuilder();
    }

    public void readStartTime(JsonParser jsonParser) throws IOException {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        builder.setStartTime(DateTime.parse(jsonParser.readValueAs(String.class), DateTimeFormat.forPattern(pattern)));
    }

    public void readiOSCount(JsonParser jsonParser) throws IOException {
        builder.setIOSCount(jsonParser.readValueAs(Integer.class));
    }

    public void readBlackBerryCount(JsonParser jsonParser) throws IOException {
        builder.setBlackBerryCount(jsonParser.readValueAs(Integer.class));
    }

    public void readC2DMCount(JsonParser jsonParser) throws IOException {
        builder.setC2DMCount(jsonParser.readValueAs(Integer.class));
    }

    public void readGCMCount(JsonParser jsonParser) throws IOException {
        builder.setGCMCount(jsonParser.readValueAs(Integer.class));
    }

    public void readWindows8Count(JsonParser jsonParser) throws IOException {
        builder.setWindows8Count(jsonParser.readValueAs(Integer.class));
    }

    public void readWindowsPhone8Count(JsonParser jsonParser) throws IOException {
        builder.setWindowsPhone8Count(jsonParser.readValueAs(Integer.class));
    }

    @Override
    public AppStats validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw  new APIParsingException(e.getMessage());
        }
    }

}
