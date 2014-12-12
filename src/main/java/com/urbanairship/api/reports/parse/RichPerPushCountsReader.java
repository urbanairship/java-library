/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.reports.model.RichPerPushCounts;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;

public final class RichPerPushCountsReader implements JsonObjectReader<RichPerPushCounts> {

    private final RichPerPushCounts.Builder builder;

    public RichPerPushCountsReader() {
        this.builder = RichPerPushCounts.newBuilder();
    }

    public void readRespones(JsonParser jsonParser) throws IOException {
        builder.setResponses(jsonParser.readValueAs(long.class));
    }

    public void readSends(JsonParser jsonParser) throws IOException {
        builder.setSends(jsonParser.readValueAs(long.class));
    }

    @Override
    public RichPerPushCounts validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
