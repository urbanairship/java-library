/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.reports.model.PushInfoResponse;
import com.urbanairship.api.reports.model.PushListingResponse;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.List;

public final class PushListingResponseReader implements JsonObjectReader<PushListingResponse> {

    private final PushListingResponse.Builder builder;

    public PushListingResponseReader() {
        this.builder = PushListingResponse.newBuilder();
    }

    public void readNextPage(JsonParser jsonParser) throws IOException {
        builder.setNextPage(jsonParser.readValueAs(String.class));
    }

    public void readPushInfoObjects(JsonParser jsonParser) throws IOException {
        builder.addPushInfoObjects(
                (List<PushInfoResponse>)jsonParser.readValueAs(
                        new TypeReference<List<PushInfoResponse>>() {
                        })
        );
    }

    @Override
    public PushListingResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception ex) {
            throw new APIParsingException(ex.getMessage());
        }
    }
}
