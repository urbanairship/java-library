package com.urbanairship.api.customevents.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.customevents.model.CustomEventResponse;

import java.io.IOException;

public class CustomEventResponseReader implements JsonObjectReader<CustomEventResponse> {
    private final CustomEventResponse.Builder builder;

    public CustomEventResponseReader() {
        this.builder = CustomEventResponse.newBuilder();
    }

    public void readOk(JsonParser jsonParser) throws IOException {
        builder.setOk(jsonParser.getBooleanValue());
    }

    public void readOperationId(JsonParser jsonParser) throws IOException {
        builder.addOperationId(jsonParser.readValueAs(String.class));
    }

    public CustomEventResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
