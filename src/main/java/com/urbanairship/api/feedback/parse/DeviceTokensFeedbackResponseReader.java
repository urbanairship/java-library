/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.feedback.parse;

import com.urbanairship.api.feedback.model.DeviceTokensFeedbackResponse;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import org.codehaus.jackson.JsonParser;
import org.joda.time.DateTime;

import java.io.IOException;

public class DeviceTokensFeedbackResponseReader implements JsonObjectReader<DeviceTokensFeedbackResponse>
{
    private static DeviceTokensFeedbackResponse.Builder builder;

    public DeviceTokensFeedbackResponseReader() {
        builder = DeviceTokensFeedbackResponse.newBuilder();
    }

    public void readDeviceToken(JsonParser jsonParser) throws IOException{
        builder.setDeviceToken(jsonParser.readValueAs(String.class));
    }

    public void readMarkedInactiveOn(JsonParser jsonParser) throws IOException {
        builder.setMarkedInactiveOn(jsonParser.readValueAs(DateTime.class));
    }

    public void readAlias(JsonParser jsonParser) throws IOException {
        builder.setAlias(jsonParser.readValueAs(String.class));
    }

    @Override
    public DeviceTokensFeedbackResponse validateAndBuild() throws IOException
    {
        try {
            return builder.build();
        } catch (Exception ex) {
            throw new APIParsingException(ex.getMessage());
        }
    }
}
