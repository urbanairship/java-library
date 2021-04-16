package com.urbanairship.api.push.parse.notification.web;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.BooleanFieldDeserializer;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.MapOfStringsDeserializer;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.notification.actions.Actions;
import com.urbanairship.api.push.model.notification.web.Button;
import com.urbanairship.api.push.model.notification.web.WebDevicePayload;
import com.urbanairship.api.push.model.notification.web.WebIcon;
import com.urbanairship.api.push.model.notification.web.WebImage;
import com.urbanairship.api.push.model.notification.web.WebTemplate;

import java.io.IOException;
import java.util.List;

public class WebDevicePayloadReader implements JsonObjectReader<WebDevicePayload> {

    private WebDevicePayload.Builder builder;

    public WebDevicePayloadReader() {
        this.builder = WebDevicePayload.newBuilder();
    }

    public void readAlert(JsonParser parser) throws IOException {
        builder.setAlert(StringFieldDeserializer.INSTANCE.deserialize(parser, "alert"));
    }

    public void readTitle(JsonParser parser) throws IOException {
        builder.setTitle(StringFieldDeserializer.INSTANCE.deserialize(parser, "title"));
    }

    public void readExtra(JsonParser parser) throws IOException {
        builder.addAllExtraEntries(MapOfStringsDeserializer.INSTANCE.deserialize(parser, "extra"));
    }

    public void readWebIcon(JsonParser parser) throws IOException {
        builder.setWebIcon(parser.readValueAs(WebIcon.class));
    }

    public void readRequireInteraction(JsonParser parser) throws IOException {
        builder.setRequireInteraction(BooleanFieldDeserializer.INSTANCE.deserialize(parser, "require_interaction"));
    }

    public void readActions(JsonParser parser) throws IOException {
        builder.setActions(parser.readValueAs(Actions.class));
    }

    public void readWebImage(JsonParser parser) throws IOException {
        builder.setWebImage(parser.readValueAs(WebImage.class));
    }

    public void readExpiry(JsonParser parser) throws IOException {
        builder.setExpiry(parser.readValueAs(PushExpiry.class));
    }

    public void readButtons(JsonParser parser) throws IOException {
        List<Button> buttons = (List<Button>)parser.readValueAs(new TypeReference<List<Button>>() {
        });

        builder.addAllButtons(buttons);
    }

    public void readTemplate(JsonParser parser) throws IOException {
        builder.setTemplate(parser.readValueAs(WebTemplate.class));
    }

    @Override
    public WebDevicePayload validateAndBuild() throws IOException {
        try{
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}