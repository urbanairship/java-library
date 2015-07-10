/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */
package com.urbanairship.api.feedback.parse;

import com.urbanairship.api.feedback.model.APIApidsFeedbackResponse;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;

public class APIApidsFeedbackResponseReader implements JsonObjectReader<APIApidsFeedbackResponse>
{
    private static APIApidsFeedbackResponse.Builder builder;

    public APIApidsFeedbackResponseReader() {
        builder = APIApidsFeedbackResponse.newBuilder();
    }

    public void readApid(JsonParser jsonParser) throws IOException
    {
        builder.setApid(jsonParser.readValueAs(String.class));
    }

    public void readGcmRegistrationId(JsonParser jsonParser) throws IOException
    {
        builder.setGcmRegistrationId(jsonParser.readValueAs(String.class));
    }

    public void readMarkedInactiveOn(JsonParser jsonParser) throws IOException {
        builder.setMarkedInactiveOn(jsonParser.readValueAs(String.class));
    }

    public void readAlias(JsonParser jsonParser) throws IOException {
        builder.setAlias(jsonParser.readValueAs(String.class));
    }

    @Override
    public APIApidsFeedbackResponse validateAndBuild() throws IOException
    {
        try {
            return builder.build();
        } catch (Exception ex) {
            throw new APIParsingException(ex.getMessage());
        }
    }
}
