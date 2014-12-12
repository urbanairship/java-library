/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;

import com.urbanairship.api.client.model.APIReportsPushListingResponse;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.reports.model.SinglePushInfoResponse;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.List;

public final class APIReportsListingResponseReader implements JsonObjectReader<APIReportsPushListingResponse> {

    private final APIReportsPushListingResponse.Builder builder;

    public APIReportsListingResponseReader() {
        this.builder = APIReportsPushListingResponse.newBuilder();
    }

    public void readNextPage(JsonParser jsonParser) throws IOException {
        builder.setNextPage(jsonParser.readValueAs(String.class));
    }

    public void readPushInfoResponses(JsonParser jsonParser) throws IOException {
        builder.addAllPushInfoResponses(
                (List<SinglePushInfoResponse>) jsonParser.readValueAs(
                        new TypeReference<List<SinglePushInfoResponse>>() {
                        })
        );
    }

    @Override
    public APIReportsPushListingResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception ex) {
            throw new APIParsingException(ex.getMessage());
        }
    }
}
