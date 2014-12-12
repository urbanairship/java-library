/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.reports.model.Opens;
import com.urbanairship.api.reports.model.ReportsAPIOpensResponse;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.List;

public class ReportsAPIOpensResponseReader implements JsonObjectReader<ReportsAPIOpensResponse> {

    private final ReportsAPIOpensResponse.Builder builder;

    public ReportsAPIOpensResponseReader() {
        this.builder = ReportsAPIOpensResponse.newBuilder();
    }

    public void readOpens(JsonParser jsonParser) throws IOException {
        builder.setObject((List<Opens>) jsonParser.readValueAs(new TypeReference<List<Opens>>() { }));
    }

    @Override
    public ReportsAPIOpensResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw  new APIParsingException(e.getMessage());
        }
    }
}
