/*
 * Copyright (c) 2013-2017.  Urban Airship and Contributors
 */

package com.urbanairship.api.experiments.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.model.ErrorDetails;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.experiments.model.ExperimentResponse;

import java.io.IOException;

public class ExperimentResponseReader implements JsonObjectReader<ExperimentResponse> {

    private final ExperimentResponse.Builder builder;

    public ExperimentResponseReader() {
        this.builder = ExperimentResponse.newBuilder();
    }

    public void readOperationId(JsonParser jsonParser) throws IOException {
        builder.setOperationId(jsonParser.readValueAs(String.class));
    }

    public void readPushId(JsonParser jsonParser) throws IOException {
        builder.setPushId(jsonParser.readValueAs(String.class));
    }

    public void readOk(JsonParser jsonParser) throws IOException {
        builder.setOk(jsonParser.getBooleanValue());
    }

    public void readExperimentId(JsonParser jsonParser) throws IOException {
        builder.setExperimentId(jsonParser.readValueAs(String.class));
    }

    public void readError(JsonParser jsonParser) throws IOException {
        builder.setError(jsonParser.readValueAs(String.class));
    }

    public void readErrorDetails(JsonParser jsonParser) throws IOException {
        builder.setErrorDetails(jsonParser.readValueAs(ErrorDetails.class));
    }

    @Override
    public ExperimentResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
