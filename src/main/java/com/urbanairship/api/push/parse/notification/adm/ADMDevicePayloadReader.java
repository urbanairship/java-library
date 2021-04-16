/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.adm;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.MapOfStringsDeserializer;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.model.notification.actions.Actions;
import com.urbanairship.api.push.model.notification.adm.ADMDevicePayload;
import com.urbanairship.api.push.model.notification.adm.ADMTemplate;
import com.urbanairship.api.push.model.notification.android.Style;

import java.io.IOException;

public class ADMDevicePayloadReader implements JsonObjectReader<ADMDevicePayload> {

    private ADMDevicePayload.Builder builder = ADMDevicePayload.newBuilder();

    public ADMDevicePayloadReader() {
    }

    public void readAlert(JsonParser parser) throws IOException {
        builder.setAlert(StringFieldDeserializer.INSTANCE.deserialize(parser, "alert"));
    }

    public void readConsolidationKey(JsonParser parser) throws IOException {
        builder.setConsolidationKey(StringFieldDeserializer.INSTANCE.deserialize(parser, "consolidation_key"));
    }

    public void readExpiresAfter(JsonParser parser) throws IOException {
        builder.setExpiresAfter(parser.readValueAs(PushExpiry.class));
    }

    public void readExtra(JsonParser parser) throws IOException {
        builder.addAllExtraEntries(MapOfStringsDeserializer.INSTANCE.deserialize(parser, "extra"));
    }

    public void readInteractive(JsonParser parser) throws IOException {
        builder.setInteractive(parser.readValueAs(Interactive.class));
    }

    public void readActions(JsonParser parser) throws IOException {
        builder.setActions(parser.readValueAs(Actions.class));
    }

    public void readIcon(JsonParser parser) throws IOException {
        builder.setIcon(StringFieldDeserializer.INSTANCE.deserialize(parser, "icon"));
    }

    public void readIconColor(JsonParser parser) throws IOException {
        builder.setIconColor(StringFieldDeserializer.INSTANCE.deserialize(parser, "icon_color"));
    }

    public void readNotificationChannel(JsonParser parser) throws IOException {
        builder.setNotificationChannel(StringFieldDeserializer.INSTANCE.deserialize(parser, "notification_channel"));
    }

    public void readNotificationTag(JsonParser parser) throws IOException {
        builder.setNotificationTag(StringFieldDeserializer.INSTANCE.deserialize(parser, "notification_tag"));
    }

    public void readSound(JsonParser parser) throws IOException {
        builder.setSound(StringFieldDeserializer.INSTANCE.deserialize(parser, "sound"));
    }

    public void readSummary(JsonParser parser) throws IOException {
        builder.setSummary(StringFieldDeserializer.INSTANCE.deserialize(parser, "summary"));
    }

    public void readTitle(JsonParser parser) throws IOException {
        builder.setTitle(StringFieldDeserializer.INSTANCE.deserialize(parser, "title"));
    }

    public void readStyle(JsonParser parser) throws IOException {
        builder.setStyle(parser.readValueAs(Style.class));
    }

    public void readTemplate(JsonParser parser) throws IOException {
        builder.setTemplate(parser.readValueAs(ADMTemplate.class));
    }

    @Override
    public ADMDevicePayload validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
