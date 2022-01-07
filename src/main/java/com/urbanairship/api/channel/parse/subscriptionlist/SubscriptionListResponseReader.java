package com.urbanairship.api.channel.parse.subscriptionlist;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.channel.model.subscriptionlist.SubscriptionListResponse;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;

import java.io.IOException;

public class SubscriptionListResponseReader implements JsonObjectReader<SubscriptionListResponse> {
    private final SubscriptionListResponse.Builder builder;

    public SubscriptionListResponseReader() {
        this.builder = SubscriptionListResponse.newBuilder();
    }

    public void readOk(JsonParser parser) throws IOException {
        builder.setOk(parser.getBooleanValue());
    }

    public void readError(JsonParser parser) throws IOException {
        builder.setError(parser.readValueAs(String.class));
    }

    @Override
    public SubscriptionListResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
