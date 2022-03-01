package com.urbanairship.api.nameduser.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.model.ErrorDetails;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.nameduser.model.NamedUserAttributeResponse;

import java.io.IOException;

public class NamedUserAttributeResponseReader implements JsonObjectReader<NamedUserAttributeResponse> {
    private final NamedUserAttributeResponse.Builder builder;

    public NamedUserAttributeResponseReader() {
        this.builder = NamedUserAttributeResponse.newBuilder();
    }

    public void readOk(JsonParser parser) throws IOException {
        builder.setOk(parser.getBooleanValue());
    }

    public void readError(JsonParser parser) throws IOException {
        builder.setError(parser.readValueAs(String.class));
    }

    public void readErrorDetails(JsonParser parser) throws IOException {
        builder.setErrorDetails(parser.readValueAs(ErrorDetails.class));
    }

    @Override
    public NamedUserAttributeResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
