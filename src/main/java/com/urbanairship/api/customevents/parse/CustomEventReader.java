package com.urbanairship.api.customevents.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.customevents.model.CustomEventPayload;
import com.urbanairship.api.customevents.model.CustomEventBody;
import com.urbanairship.api.customevents.model.CustomEventUser;
import org.joda.time.DateTime;

import java.io.IOException;

public class CustomEventReader implements JsonObjectReader<CustomEventPayload> {

    private final CustomEventPayload.Builder builder;

    public CustomEventReader() {
        this.builder = CustomEventPayload.newBuilder();
    }

    public void readUser(JsonParser parser) throws IOException {
        builder.setCustomEventUser(parser.readValueAs(CustomEventUser.class));
    }

    public void readBody(JsonParser parser) throws IOException {
        builder.setCustomEventBody(parser.readValueAs(CustomEventBody.class));
    }

    public void readOccurred(JsonParser parser) throws IOException {
        builder.setOccurred(parser.readValueAs(DateTime.class));
    }

    @Override
    public CustomEventPayload validateAndBuild() throws IOException {
        return null;
    }
}
