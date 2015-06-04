/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.reports.model.TimeInApp;
import org.codehaus.jackson.JsonParser;

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
        builder.setDate(DateFormats.DATE_PARSER.parseDateTime(jsonParser.readValueAs(String.class)));
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
