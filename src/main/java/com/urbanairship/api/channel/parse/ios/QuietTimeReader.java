/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.channel.parse.ios;

import com.urbanairship.api.channel.model.ios.QuietTime;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;

public final class QuietTimeReader implements JsonObjectReader<QuietTime> {

    private final QuietTime.Builder builder;

    public QuietTimeReader() {
        this.builder = QuietTime.newBuilder();
    }

    public void readStart(JsonParser jsonParser) throws IOException {
        builder.setStart(jsonParser.readValueAs(String.class));
    }

    public void readEnd(JsonParser jsonParser) throws IOException {
        builder.setEnd(jsonParser.readValueAs(String.class));
    }

    @Override
    public QuietTime validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
