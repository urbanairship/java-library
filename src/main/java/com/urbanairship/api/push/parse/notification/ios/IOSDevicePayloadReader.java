/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.ios;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.BooleanFieldDeserializer;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.MapOfStringsDeserializer;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.model.notification.ios.IOSDevicePayload;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;

import java.io.IOException;

public class IOSDevicePayloadReader implements JsonObjectReader<IOSDevicePayload> {

    private IOSDevicePayload.Builder builder = IOSDevicePayload.newBuilder();
    private IOSAlertDataDeserializer alertDS = new IOSAlertDataDeserializer();

    public IOSDevicePayloadReader() {
    }

    public void readAlert(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setAlert(alertDS.deserialize(parser, context));
    }

    public void readSound(JsonParser parser) throws IOException {
        builder.setSound(StringFieldDeserializer.INSTANCE.deserialize(parser, "sound"));
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

    @Override
    public IOSDevicePayload validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
