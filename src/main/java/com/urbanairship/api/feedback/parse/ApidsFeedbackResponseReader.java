/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.feedback.parse;

import com.urbanairship.api.feedback.model.ApidsFeedbackResponse;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;

import org.codehaus.jackson.JsonParser;
import org.joda.time.DateTime;

import java.io.IOException;

public class ApidsFeedbackResponseReader implements JsonObjectReader<ApidsFeedbackResponse>
{
    private static ApidsFeedbackResponse.Builder builder;

    public ApidsFeedbackResponseReader() {
        builder = ApidsFeedbackResponse.newBuilder();
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
        builder.setMarkedInactiveOn(jsonParser.readValueAs(DateTime.class));
    }

    public void readAlias(JsonParser jsonParser) throws IOException {
        builder.setAlias(jsonParser.readValueAs(String.class));
    }

    @Override
    public ApidsFeedbackResponse validateAndBuild() throws IOException
    {
        try {
            return builder.build();
        } catch (Exception ex) {
            throw new APIParsingException(ex.getMessage());
        }
    }
}
