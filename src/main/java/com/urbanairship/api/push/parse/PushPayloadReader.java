/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.PushOptions;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.model.notification.richpush.RichPushMessage;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;

public class PushPayloadReader implements JsonObjectReader<PushPayload> {

    private final PushPayload.Builder builder;

    public PushPayloadReader() {
        this.builder = PushPayload.newBuilder();
    }

    public void readAudience(JsonParser jsonParser) throws IOException {
        builder.setAudience(jsonParser.readValueAs(Selector.class));
    }

    public void readNotification(JsonParser jsonParser) throws IOException {
        builder.setNotification(jsonParser.readValueAs(Notification.class));
    }

    public void readMessage(JsonParser jsonParser) throws IOException {
        builder.setMessage(jsonParser.readValueAs(RichPushMessage.class));
    }

    public void readOptions(JsonParser parser) throws IOException {
        builder.setPushOptions(parser.readValueAs(PushOptions.class));
    }

    public void readDeviceTypes(JsonParser parser) throws IOException {
        builder.setDeviceTypes(parser.readValueAs(DeviceTypeData.class));
    }

    @Override
    public PushPayload validateAndBuild() throws IOException {
        try {
            return builder.build();
        }
        catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
