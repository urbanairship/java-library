package com.urbanairship.api.push.parse.notification.web;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.push.model.notification.actions.Actions;
import com.urbanairship.api.push.model.notification.web.Button;

import java.io.IOException;

public class ButtonReader implements JsonObjectReader<Button> {
    private Button.Builder builder = Button.newBuilder();

    public void readId(JsonParser parser) throws IOException {
        builder.setId(StringFieldDeserializer.INSTANCE.deserialize(parser, "id"));
    }

    public void readActions(JsonParser parser) throws IOException {
        builder.setActions(parser.readValueAs(Actions.class));
    }

    public void readLabel(JsonParser parser) throws IOException {
        builder.setLabel(StringFieldDeserializer.INSTANCE.deserialize(parser, "label"));
    }

    @Override
    public Button validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
