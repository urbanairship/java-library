/*
 * Copyright (c) 2013-2017.  Urban Airship and Contributors
 */

package com.urbanairship.api.experiments.parse;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.experiments.model.VariantPushPayload;
import com.urbanairship.api.push.model.InApp;
import com.urbanairship.api.push.model.PushOptions;
import com.urbanairship.api.push.model.notification.Notification;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;

public class VariantPushPayloadReader implements JsonObjectReader<VariantPushPayload> {
    private final VariantPushPayload.Builder builder;

    public VariantPushPayloadReader() {
        this.builder = VariantPushPayload.newBuilder();
    }

    public void readNotification(JsonParser jsonParser) throws IOException {
        builder.setNotification(jsonParser.readValueAs(Notification.class));
    }

    public void readPushOptions(JsonParser jsonParser) throws IOException {
        builder.setPushOptions(jsonParser.readValueAs(PushOptions.class));
    }

    public void readInApp(JsonParser jsonParser) throws IOException {
        builder.setInApp(jsonParser.readValueAs(InApp.class));
    }

    @Override
    public VariantPushPayload validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
