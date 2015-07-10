/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */
package com.urbanairship.api.feedback.parse;

import com.urbanairship.api.feedback.model.APIDeviceTokensFeedbackResponse;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;

public class APIDeviceTokensFeedbackResponseReader implements JsonObjectReader<APIDeviceTokensFeedbackResponse>
{
    private static APIDeviceTokensFeedbackResponse.Builder builder;

    public APIDeviceTokensFeedbackResponseReader() {
        builder = APIDeviceTokensFeedbackResponse.newBuilder();
    }

    public void readDeviceToken(JsonParser jsonParser) throws IOException{
        builder.setDeviceToken(jsonParser.readValueAs(String.class));
    }

    public void readMarkedInactiveOn(JsonParser jsonParser) throws IOException {
        builder.setMarkedInactiveOn(jsonParser.readValueAs(String.class));
    }

    public void readAlias(JsonParser jsonParser) throws IOException {
        builder.setAlias(jsonParser.readValueAs(String.class));
    }

    @Override
    public APIDeviceTokensFeedbackResponse validateAndBuild() throws IOException
    {
        try {
            return builder.build();
        } catch (Exception ex) {
            throw new APIParsingException(ex.getMessage());
        }
    }
}
