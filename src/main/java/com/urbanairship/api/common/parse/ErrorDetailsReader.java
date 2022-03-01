/*
 * Copyright (c) 2013-2022. Airship and Contributors
 */

package com.urbanairship.api.common.parse;


import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.model.ErrorDetails;

import java.io.IOException;

public final class ErrorDetailsReader implements JsonObjectReader<ErrorDetails> {

    private final ErrorDetails.Builder builder;

    public ErrorDetailsReader() {
        this.builder = ErrorDetails.newBuilder();
    }

    public void readError(JsonParser jsonParser) throws IOException {
        builder.setError(jsonParser.readValueAs(String.class));
    }

    public void readPath(JsonParser jsonParser) throws IOException {
        builder.setPath(jsonParser.readValueAs(String.class));
    }

    @Override
    public ErrorDetails validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception ex) {
            throw new APIParsingException(ex.getMessage());
        }
    }
}
