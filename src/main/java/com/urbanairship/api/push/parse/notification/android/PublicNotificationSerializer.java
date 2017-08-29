/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.android;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.android.PublicNotification;

import java.io.IOException;

public class PublicNotificationSerializer extends JsonSerializer<PublicNotification> {

    @Override
    public void serialize(PublicNotification publicNotification, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (publicNotification.getSummary().isPresent()) {
            jgen.writeStringField("summary", publicNotification.getSummary().get());
        }
        if (publicNotification.getAlert().isPresent()) {
            jgen.writeStringField("alert", publicNotification.getAlert().get());
        }
        if (publicNotification.getTitle().isPresent()) {
            jgen.writeStringField("title", publicNotification.getTitle().get());

        }

        jgen.writeEndObject();
    }
}
