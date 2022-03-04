package com.urbanairship.api.channel.parse.attributes;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.channel.model.ChannelAttributesResponse;
import com.urbanairship.api.common.model.ErrorDetails;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;

import java.io.IOException;

public class ChannelAttributesResponseReader implements JsonObjectReader<ChannelAttributesResponse> {
    private final ChannelAttributesResponse.Builder builder;

    public ChannelAttributesResponseReader() {
        this.builder = ChannelAttributesResponse.newBuilder();
    }

    public void readOk(JsonParser parser) throws IOException {
        builder.setOk(parser.getBooleanValue());
    }

    public void readWarning(JsonParser parser) throws IOException {
        builder.setWarning(parser.readValueAs(String.class));
    }

    public void readError(JsonParser parser) throws IOException {
        builder.setError(parser.readValueAs(String.class));
    }

    public void readErrorDetails(JsonParser parser) throws IOException {
        builder.setErrorDetails(parser.readValueAs(ErrorDetails.class));
    }

    @Override
    public ChannelAttributesResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
