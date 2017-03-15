package com.urbanairship.api.push.parse.notification.web;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.MapOfStringsDeserializer;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.push.model.notification.web.WebDevicePayload;
import com.urbanairship.api.push.model.notification.web.WebIcon;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;

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

    @Override
    public WebDevicePayload validateAndBuild() throws IOException {
        try{
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}