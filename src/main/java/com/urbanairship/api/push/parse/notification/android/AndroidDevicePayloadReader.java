/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
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
import org.codehaus.jackson.JsonParser;

import java.io.IOException;

public class AndroidDevicePayloadReader implements JsonObjectReader<AndroidDevicePayload> {

    private AndroidDevicePayload.Builder builder = AndroidDevicePayload.newBuilder();

    public AndroidDevicePayloadReader() {
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

    @Override
    public AndroidDevicePayload validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
