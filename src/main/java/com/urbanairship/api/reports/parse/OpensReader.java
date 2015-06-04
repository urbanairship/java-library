/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.reports.model.Opens;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;

public final class OpensReader implements JsonObjectReader<Opens> {

    private final Opens.Builder builder;

    public OpensReader() {
        this.builder = Opens.newBuilder();
    }

    public void readAndroid(JsonParser jsonParser) throws IOException {
        builder.setAndroid(jsonParser.readValueAs(long.class));
    }

    public void readIOS(JsonParser jsonParser) throws IOException {
        builder.setIOS(jsonParser.readValueAs(long.class));
    }

    public void readDate(JsonParser jsonParser) throws IOException {
        String created = jsonParser.readValueAs(String.class);
        builder.setDate(DateFormats.DATE_PARSER.parseDateTime(created));
    }

    @Override
    public Opens validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
