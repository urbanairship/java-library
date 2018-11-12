package com.urbanairship.api.channel.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.channel.model.SmsRegistrationResponse;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;

import java.io.IOException;

public class SmsRegistrationResponseReader implements JsonObjectReader<SmsRegistrationResponse> {
    private final SmsRegistrationResponse.Builder builder;

    public SmsRegistrationResponseReader() {
        this.builder = SmsRegistrationResponse.newBuilder();
    }

    public void readOk(JsonParser parser) throws IOException {
        builder.setOk(parser.getBooleanValue());
    }

    public void readChannelId(JsonParser parser) throws IOException {
        builder.setChannelId(parser.readValueAs(String.class));
    }

    public void readStatus(JsonParser parser) throws IOException {
        builder.setStatus(parser.readValueAs(String.class));
    }

    public void readErrors(JsonParser parser) throws IOException {
        builder.setErrors(parser.readValueAs(String.class));
    }

    @Override
    public SmsRegistrationResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
