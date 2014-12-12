/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.ios;

import com.urbanairship.api.push.model.notification.ios.IOSAlertData;
import com.urbanairship.api.common.parse.*;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;

import java.io.IOException;

public class IOSAlertDataReader implements JsonObjectReader<IOSAlertData> {

    private IOSAlertData.Builder builder = IOSAlertData.newBuilder();

    public IOSAlertDataReader() {
    }

    public void readBody(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setBody(StringFieldDeserializer.INSTANCE.deserialize(parser, "body"));
    }

    public void readActionLocKey(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setActionLocKey(StringFieldDeserializer.INSTANCE.deserialize(parser, "action-loc-key"));
    }

    public void readLocKey(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setLocKey(StringFieldDeserializer.INSTANCE.deserialize(parser, "loc-key"));
    }

    public void readLocArgs(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setLocArgs(ListOfStringsDeserializer.INSTANCE.deserialize(parser, "loc-args"));
    }

    public void readLaunchImage(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setLaunchImage(StringFieldDeserializer.INSTANCE.deserialize(parser, "launch-image"));
    }

    @Override
        public IOSAlertData validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
