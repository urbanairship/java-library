/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.audience.location;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.IntFieldDeserializer;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.push.model.audience.location.DateRangeUnit;
import com.urbanairship.api.push.model.audience.location.RecentDateRange;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;

public class RecentDateRangeReader implements JsonObjectReader<RecentDateRange.Builder> {

    private RecentDateRange.Builder builder = null;

    public RecentDateRangeReader() { }

    public void readValue(JsonParser parser) throws IOException {
        if (builder == null) {
            builder = RecentDateRange.newBuilder();
        } else {
            APIParsingException.raise("Only one date range is allowed on 'recent'", parser);
        }
        String type = parser.getCurrentName();
        DateRangeUnit unit = DateRangeUnit.getUnitForIdentifier(type);
        if (unit == null) {
            APIParsingException.raise(String.format("Unknown date range unit '%s'", type), parser);
        }
        int value = IntFieldDeserializer.INSTANCE.deserialize(parser, type);
        builder.setResolution(unit)
            .setUnits(value);
    }

    @Override
    public RecentDateRange.Builder validateAndBuild() throws IOException {
        return builder;
    }
}
