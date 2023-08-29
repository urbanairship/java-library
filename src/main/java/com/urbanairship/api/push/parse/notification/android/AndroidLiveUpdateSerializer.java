/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.push.parse.notification.android;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.android.AndroidLiveUpdate;
import com.urbanairship.api.push.model.notification.ios.IOSLiveActivity;

import java.io.IOException;

public class AndroidLiveUpdateSerializer extends JsonSerializer<AndroidLiveUpdate> {
    @Override
    public void serialize(AndroidLiveUpdate content, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeStringField("event", content.getAndroidLiveUpdateEvent().getAndroidLiveUpdateEvent());
        jgen.writeStringField("name", content.getName());

        if(content.getContentState().isPresent()) {
            jgen.writeObjectField("content_state", content.getContentState());
        }

        if(content.getDismissalDate().isPresent()) {
            jgen.writeNumberField("dismissal_date", content.getDismissalDate().get());
        }

        if(content.getTimestamp().isPresent()) {
            jgen.writeNumberField("timestamp", content.getTimestamp().get());
        }

        if(content.getType().isPresent()) {
            jgen.writeStringField("type", content.getType().get());
        }

        jgen.writeEndObject();
    }
}