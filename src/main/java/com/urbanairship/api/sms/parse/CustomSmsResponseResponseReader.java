package com.urbanairship.api.sms.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.sms.model.CustomSmsResponseResponse;

import java.io.IOException;

public class CustomSmsResponseResponseReader implements JsonObjectReader<CustomSmsResponseResponse> {

    private final CustomSmsResponseResponse.Builder builder;

    public CustomSmsResponseResponseReader() {
        this.builder = CustomSmsResponseResponse.newBuilder();
    }

    public void readOk(JsonParser jsonParser) throws IOException {
        builder.setOk(jsonParser.getBooleanValue());
    }

    public void readPushId(JsonParser jsonParser) throws IOException {
        builder.setPushId(jsonParser.readValueAs(String.class));
    }

    public void readOperationId(JsonParser jsonParser) throws IOException {
        builder.setOperationId(jsonParser.readValueAs(String.class));
    }

    @Override
    public CustomSmsResponseResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception ex) {
            throw new APIParsingException(ex.getMessage());
        }
    }
}
