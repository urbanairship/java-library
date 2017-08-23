package com.urbanairship.api.customevents.parse;


import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.customevents.model.CustomEventChannelType;
import com.urbanairship.api.customevents.model.CustomEventUser;

import java.io.IOException;

public class CustomEventUserReader implements JsonObjectReader<CustomEventUser> {

    private final CustomEventUser.Builder builder;

    public CustomEventUserReader() {
        builder = CustomEventUser.newBuilder();
    }

    public void readChannel(CustomEventChannelType type, JsonParser parser) throws IOException {
        builder.setCustomEventChannelType(type);
        builder.setChannel(parser.getText());
    }

    @Override
    public CustomEventUser validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
