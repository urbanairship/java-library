package com.urbanairship.api.channel.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.google.common.base.Optional;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.channel.model.ChannelUninstallResponse;

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
        builder.setError(Optional.fromNullable(parser.getText()));
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
