/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.ios;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.BooleanFieldDeserializer;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.MapOfStringsDeserializer;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.model.notification.ios.IOSDevicePayload;

import java.io.IOException;

public class IOSDevicePayloadReader implements JsonObjectReader<IOSDevicePayload> {

    private IOSDevicePayload.Builder builder = IOSDevicePayload.newBuilder();
    private IOSAlertDataDeserializer alertDS = new IOSAlertDataDeserializer();
    private MediaAttachmentDeserializer mediaAttachmentDS = new MediaAttachmentDeserializer();
    private IOSSoundDataDeserializer soundDataDS = new IOSSoundDataDeserializer();

    public IOSDevicePayloadReader() {
    }

    public void readAlert(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setAlert(alertDS.deserialize(parser, context));
    }

    public void readBadge(JsonParser parser) throws IOException {
        builder.setBadge(IOSBadgeDataDeserializer.INSTANCE.deserialize(parser));
    }

    public void readContentAvailable(JsonParser parser) throws IOException {
        builder.setContentAvailable(BooleanFieldDeserializer.INSTANCE.deserialize(parser, "content_available"));
    }

    public void readExtra(JsonParser parser) throws IOException {
        builder.addAllExtraEntries(MapOfStringsDeserializer.INSTANCE.deserialize(parser, "extra"));
    }

    public void readCategory(JsonParser parser) throws IOException {
        builder.setCategory(StringFieldDeserializer.INSTANCE.deserialize(parser, "category"));
    }

    public void readInteractive(JsonParser parser) throws IOException {
        builder.setInteractive(parser.readValueAs(Interactive.class));
    }

    public void readExpiry(JsonParser parser) throws IOException {
        builder.setExpiry(parser.readValueAs(PushExpiry.class));
    }

    public void readPriority(JsonParser parser) throws IOException {
        builder.setPriority(parser.getIntValue());
    }

    public void readTitle(JsonParser parser) throws IOException {
        builder.setTitle(StringFieldDeserializer.INSTANCE.deserialize(parser, "title"));
    }

    public void readSubtitle(JsonParser parser) throws IOException{
        builder.setSubtitle(StringFieldDeserializer.INSTANCE.deserialize(parser, "subtitle"));
    }

    public void readMutableContent(JsonParser parser) throws IOException {
        builder.setMutableContent(BooleanFieldDeserializer.INSTANCE.deserialize(parser, "mutable_content"));
    }

    public void readSoundData(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setSoundData(soundDataDS.deserialize(parser, context));
    }

    public void readMediaAttachment(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setMediaAttachment(mediaAttachmentDS.deserialize(parser, context));
    }

    public void readCollapseId(JsonParser parser) throws IOException {
        builder.setCollapseId(StringFieldDeserializer.INSTANCE.deserialize(parser, "collapse_id"));
    }

    @Override
    public IOSDevicePayload validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
