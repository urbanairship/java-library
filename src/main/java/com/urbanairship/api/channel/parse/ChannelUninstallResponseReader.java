package com.urbanairship.api.channel.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.channel.model.ChannelUninstallResponse;
import com.urbanairship.api.common.model.ErrorDetails;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;

import java.io.IOException;

public class ChannelUninstallResponseReader implements JsonObjectReader<ChannelUninstallResponse> {
    private final ChannelUninstallResponse.Builder builder;

    public ChannelUninstallResponseReader() {
        this.builder = ChannelUninstallResponse.newBuilder();
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
    public ChannelUninstallResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
