/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.urbanairship.api.common.model.ErrorDetails;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.reports.model.PushInfoResponse;
import com.urbanairship.api.reports.model.PushListingResponse;

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

    public void readOk(JsonParser jsonParser) throws IOException {
        builder.setOk(jsonParser.readValueAs(Boolean.class));
    }

    public void readError(JsonParser jsonParser) throws IOException {
        builder.setError(jsonParser.readValueAs(String.class));
    }

    public void readErrorDetails(JsonParser jsonParser) throws IOException {
        builder.setErrorDetails(jsonParser.readValueAs(ErrorDetails.class));
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
