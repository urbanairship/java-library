package com.urbanairship.api.channel.parse.web;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.channel.model.web.Subscription;
import com.urbanairship.api.channel.model.web.WebSettings;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;

import java.io.IOException;

public class WebSettingsReader implements JsonObjectReader<WebSettings> {

    private final WebSettings.Builder builder;

    public WebSettingsReader() {
        this.builder = WebSettings.newBuilder();
    }

    public void readSubscription(JsonParser parser) throws IOException {
        builder.setSubscription(parser.readValueAs(Subscription.class));
    }

    public WebSettings validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
