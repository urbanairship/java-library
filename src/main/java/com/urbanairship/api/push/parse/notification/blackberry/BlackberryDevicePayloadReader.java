/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.blackberry;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.push.model.notification.blackberry.BlackberryDevicePayload;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;

public class BlackberryDevicePayloadReader implements JsonObjectReader<BlackberryDevicePayload> {

    private BlackberryDevicePayload.Builder builder = BlackberryDevicePayload.newBuilder();

    public BlackberryDevicePayloadReader() {
    }

    public void readAlert(JsonParser parser) throws IOException {
        builder.setAlert(StringFieldDeserializer.INSTANCE.deserialize(parser, "alert"));
    }

    public void readBody(JsonParser parser) throws IOException {
        builder.setBody(StringFieldDeserializer.INSTANCE.deserialize(parser, "body"));
    }

    public void readContentType(JsonParser parser) throws IOException {
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
