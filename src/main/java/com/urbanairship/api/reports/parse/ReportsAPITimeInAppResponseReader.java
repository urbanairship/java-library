/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.reports.model.ReportsAPITimeInAppResponse;
import com.urbanairship.api.reports.model.TimeInApp;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.List;

public class ReportsAPITimeInAppResponseReader implements JsonObjectReader<ReportsAPITimeInAppResponse> {

    private final ReportsAPITimeInAppResponse.Builder builder;

    public ReportsAPITimeInAppResponseReader() {
        this.builder = ReportsAPITimeInAppResponse.newBuilder();
    }

    public void readTimeInApp(JsonParser jsonParser) throws IOException {
        builder.setObject((List<TimeInApp>) jsonParser.readValueAs(new TypeReference<List<TimeInApp>>() { }));
    }

    @Override
    public ReportsAPITimeInAppResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw  new APIParsingException(e.getMessage());
        }
    }
}
