package com.urbanairship.api.channel.parse.web;

import com.urbanairship.api.channel.Constants;
import com.urbanairship.api.channel.model.web.Subscription;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;

public class SubscriptionReader implements JsonObjectReader<Subscription> {
    private final Subscription.Builder builder;

    public SubscriptionReader() {
        this.builder = Subscription.newBuilder();
    }

    public void readAuth(JsonParser jsonParser) throws IOException {
        builder.setAuth(StringFieldDeserializer.INSTANCE.deserialize(jsonParser, Constants.AUTH));
    }

    public void readP256dh(JsonParser jsonParser) throws IOException {
        builder.setP256dh(StringFieldDeserializer.INSTANCE.deserialize(jsonParser, Constants.P256DH));
    }

    public Subscription validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
