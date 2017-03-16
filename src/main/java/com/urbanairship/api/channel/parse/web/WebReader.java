package com.urbanairship.api.channel.parse.web;

import com.urbanairship.api.channel.model.web.Subscription;
import com.urbanairship.api.channel.model.web.Web;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;

public class WebReader implements JsonObjectReader<Web> {

    private final Web.Builder builder;

    public WebReader() {
        this.builder = Web.newBuilder();
    }

    public void readSubscription(JsonParser parser) throws IOException {
        builder.setSubscription(parser.readValueAs(Subscription.class));
    }

    public Web validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
