package com.urbanairship.api.channel.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.channel.model.OpenChannelResponse;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;

import java.io.IOException;

public class OpenChannelResponseReader implements JsonObjectReader<OpenChannelResponse> {
    private final OpenChannelResponse.Builder builder;

    public OpenChannelResponseReader() {
        this.builder = OpenChannelResponse.newBuilder();
    }

    public void readOk(JsonParser parser) throws IOException {
        builder.setOk(parser.getBooleanValue());
    }

    public void readChannelId(JsonParser parser) throws IOException {
        builder.setChannelId(parser.readValueAs(String.class));
    }

    @Override
    public OpenChannelResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
