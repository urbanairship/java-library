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
import com.urbanairship.api.push.model.notification.actions.Actions;
import com.urbanairship.api.push.model.notification.ios.IOSDevicePayload;
import com.urbanairship.api.push.model.notification.ios.IOSInterruptionLevel;
import com.urbanairship.api.push.model.notification.ios.IOSTemplate;

import java.io.IOException;

public class IOSDevicePayloadReader implements JsonObjectReader<IOSDevicePayload> {

    private IOSDevicePayload.Builder builder = IOSDevicePayload.newBuilder();
    private IOSAlertDataDeserializer alertDS = new IOSAlertDataDeserializer();
    private MediaAttachmentDeserializer mediaAttachmentDS = new MediaAttachmentDeserializer();
    private IOSSoundDataDeserializer soundDS = new IOSSoundDataDeserializer();

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
        builder.setSoundData(soundDS.deserialize(parser, context));
    }

    public void readMediaAttachment(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setMediaAttachment(mediaAttachmentDS.deserialize(parser, context));
    }

    public void readCollapseId(JsonParser parser) throws IOException {
        builder.setCollapseId(StringFieldDeserializer.INSTANCE.deserialize(parser, "collapse_id"));
    }

    public void readThreadId(JsonParser parser) throws IOException {
        builder.setThreadId(StringFieldDeserializer.INSTANCE.deserialize(parser, "thread_id"));
    }

    public void readActions(JsonParser parser) throws IOException {
        builder.setActions(parser.readValueAs(Actions.class));
    }

    public void readTargetContentId(JsonParser parser) throws IOException {
        builder.setTargetContentId(StringFieldDeserializer.INSTANCE.deserialize(parser, "target_content_id"));
    }

    public void readIosTemplate(JsonParser parser) throws IOException {
        builder.setIosTemplate(parser.readValueAs(IOSTemplate.class));
    }

    public void readIosInterruptionLevel(JsonParser parser) throws IOException {
        builder.setIosInterruptionLevel(parser.readValueAs(IOSInterruptionLevel.class));
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
