/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.reports.model.PerPushCounts;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;

public final class PerPushCountsReader implements JsonObjectReader<PerPushCounts> {

    private final PerPushCounts.Builder builder;

    public PerPushCountsReader() {
        this.builder = PerPushCounts.newBuilder();
    }

    public void readDirectResponses(JsonParser jsonParser) throws IOException {
        builder.setDirectResponses(jsonParser.readValueAs(long.class));
    }

    public void readInfluencedResponses(JsonParser jsonParser) throws IOException {
        builder.setInfluencedResponses(jsonParser.readValueAs(long.class));
    }

    public void readSends(JsonParser jsonParser) throws IOException {
        builder.setSends(jsonParser.readValueAs(long.class));
    }

    @Override
    public PerPushCounts validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
