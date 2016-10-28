/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.android;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.BooleanFieldDeserializer;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.MapOfStringsDeserializer;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.model.notification.android.AndroidDevicePayload;
import com.urbanairship.api.push.model.notification.android.Category;
import com.urbanairship.api.push.model.notification.android.PublicNotification;
import com.urbanairship.api.push.model.notification.android.Style;
import com.urbanairship.api.push.model.notification.android.Wearable;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;

public class AndroidDevicePayloadReader implements JsonObjectReader<AndroidDevicePayload> {

    private final AndroidDevicePayload.Builder builder;

    public AndroidDevicePayloadReader() {
        this.builder = AndroidDevicePayload.newBuilder();
    }

    public void readAlert(JsonParser parser) throws IOException {
        builder.setAlert(StringFieldDeserializer.INSTANCE.deserialize(parser, "alert"));
    }

    public void readCollapseKey(JsonParser parser) throws IOException {
        builder.setCollapseKey(StringFieldDeserializer.INSTANCE.deserialize(parser, "collapse_key"));
    }

    public void readTimeToLive(JsonParser parser) throws IOException {
        builder.setTimeToLive(parser.readValueAs(PushExpiry.class));
    }

    public void readDeliveryPriority(JsonParser parser) throws IOException {
        builder.setDeliveryPriority(parser.readValueAs(String.class));
    }

    public void readDelayWhileIdle(JsonParser parser) throws IOException {
        builder.setDelayWhileIdle(BooleanFieldDeserializer.INSTANCE.deserialize(parser, "delay_while_idle"));
    }

    public void readExtra(JsonParser parser) throws IOException {
        builder.addAllExtraEntries(MapOfStringsDeserializer.INSTANCE.deserialize(parser, "extra"));
    }

    public void readInteractive(JsonParser parser) throws IOException {
        builder.setInteractive(parser.readValueAs(Interactive.class));
    }

    public void readTitle(JsonParser parser) throws IOException {
        builder.setTitle(StringFieldDeserializer.INSTANCE.deserialize(parser, "title"));
    }

    public void readWearable(JsonParser parser) throws IOException {
        builder.setWearable(parser.readValueAs(Wearable.class));
    }

    public void readLocalOnly(JsonParser parser) throws IOException {
        builder.setLocalOnly(parser.readValueAs(Boolean.class));
    }

    public void readSummary(JsonParser parser) throws IOException {
        builder.setSummary(parser.readValueAs(String.class));
    }

    public void readStyle(JsonParser parser) throws IOException {
        builder.setStyle(parser.readValueAs(Style.class));
    }

    public void readSound(JsonParser parser) throws IOException {
        builder.setSound(parser.readValueAs(String.class));
    }

    public void readPriority(JsonParser parser) throws IOException {
        builder.setPriority(parser.readValueAs(Integer.class));
    }

    public void readCategory(JsonParser parser) throws IOException {
        builder.setCategory(parser.readValueAs(Category.class));
    }

    public void readVisibility(JsonParser parser) throws IOException {
        builder.setVisibility(parser.readValueAs(Integer.class));
    }

    public void readPublicNotification(JsonParser parser) throws IOException {
        builder.setPublicNotification(parser.readValueAs(PublicNotification.class));
    }

    @Override
    public AndroidDevicePayload validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
