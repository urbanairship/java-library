/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.reports.model.TimeInApp;
import org.codehaus.jackson.JsonParser;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.IOException;

public final class TimeInAppReader implements JsonObjectReader<TimeInApp> {

    private final TimeInApp.Builder builder;

    public TimeInAppReader() {
        this.builder = TimeInApp.newBuilder();
    }

    public void readAndroid(JsonParser jsonParser) throws IOException {
        builder.setAndroid(jsonParser.readValueAs(float.class));
    }

    public void readIOS(JsonParser jsonParser) throws IOException {
        builder.setIOS(jsonParser.readValueAs(float.class));
    }

    public void readDate(JsonParser jsonParser) throws IOException {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        builder.setDate(DateTime.parse(jsonParser.readValueAs(String.class), DateTimeFormat.forPattern(pattern)));
    }

    @Override
    public TimeInApp validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
