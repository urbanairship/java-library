/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.blackberry;

import com.urbanairship.api.push.model.notification.blackberry.BlackberryDevicePayload;
import com.urbanairship.api.push.parse.*;
import com.urbanairship.api.common.parse.*;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;

import java.io.IOException;

public class BlackberryDevicePayloadReader implements JsonObjectReader<BlackberryDevicePayload> {

    private BlackberryDevicePayload.Builder builder = BlackberryDevicePayload.newBuilder();

    public BlackberryDevicePayloadReader() {
    }

    public void readAlert(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setAlert(StringFieldDeserializer.INSTANCE.deserialize(parser, "alert"));
    }

    public void readBody(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setBody(StringFieldDeserializer.INSTANCE.deserialize(parser, "body"));
    }

    public void readContentType(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setContentType(StringFieldDeserializer.INSTANCE.deserialize(parser, "content_type"));
    }

    @Override
    public BlackberryDevicePayload validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
