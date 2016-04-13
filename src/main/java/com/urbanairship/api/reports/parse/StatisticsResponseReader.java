/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.reports.model.StatisticsResponse;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;

public final class StatisticsResponseReader implements JsonObjectReader<StatisticsResponse> {

    private final StatisticsResponse.Builder builder;

    public StatisticsResponseReader() {
        this.builder = StatisticsResponse.newBuilder();
    }

    public void readStartTime(JsonParser jsonParser) throws IOException {
        String created = jsonParser.readValueAs(String.class);
        builder.setStartTime(DateFormats.DATE_PARSER.parseDateTime(created));
    }

    public void readiOSCount(JsonParser jsonParser) throws IOException {
        builder.setIosCount(jsonParser.readValueAs(Integer.class));
    }

    public void readBlackBerryCount(JsonParser jsonParser) throws IOException {
        builder.setBlackBerryCount(jsonParser.readValueAs(Integer.class));
    }

    public void readC2DMCount(JsonParser jsonParser) throws IOException {
        builder.setC2dmCount(jsonParser.readValueAs(Integer.class));
    }

    public void readGCMCount(JsonParser jsonParser) throws IOException {
        builder.setGcmCount(jsonParser.readValueAs(Integer.class));
    }

    public void readWindows8Count(JsonParser jsonParser) throws IOException {
        builder.setWindows8Count(jsonParser.readValueAs(Integer.class));
    }

    public void readWindowsPhone8Count(JsonParser jsonParser) throws IOException {
        builder.setWindowsPhone8Count(jsonParser.readValueAs(Integer.class));
    }

    @Override
    public StatisticsResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }

}
