package com.urbanairship.api.channel.parse.email;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.channel.model.email.EmailChannelResponse;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;

import java.io.IOException;

public class RegisterEmailChannelResponseReader implements JsonObjectReader<EmailChannelResponse> {

    private final EmailChannelResponse.Builder builder;

    public RegisterEmailChannelResponseReader() {
        this.builder = EmailChannelResponse.newBuilder();
    }

    public void readOk(JsonParser jsonParser) throws IOException {
        builder.setOk(jsonParser.getBooleanValue());
    }

    public void readChannelId(JsonParser jsonParser) throws IOException {
        builder.setChannelId(jsonParser.readValueAs(String.class));
    }

    @Override
    public EmailChannelResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception ex) {
            throw new APIParsingException(ex.getMessage());
        }
    }
}
