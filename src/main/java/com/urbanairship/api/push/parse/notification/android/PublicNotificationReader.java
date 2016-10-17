/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.android;


import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.push.model.notification.android.PublicNotification;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;

public class PublicNotificationReader implements JsonObjectReader<PublicNotification> {

    private final PublicNotification.Builder builder;

    public PublicNotificationReader() {
        this.builder = PublicNotification.newBuilder();
    }

    public void readTitle(JsonParser parser) throws IOException {
        builder.setTitle(StringFieldDeserializer.INSTANCE.deserialize(parser, "title"));
    }

    public void readAlert(JsonParser parser) throws IOException {
        builder.setAlert(StringFieldDeserializer.INSTANCE.deserialize(parser, "alert"));
    }

    public void readSummary(JsonParser parser) throws IOException {
        builder.setSummary(StringFieldDeserializer.INSTANCE.deserialize(parser, "summary"));
    }

    @Override
    public PublicNotification validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
