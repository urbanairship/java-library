/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.ios;

import com.urbanairship.api.push.model.notification.ios.IOSAlertData;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class IOSAlertDataSerializer extends JsonSerializer<IOSAlertData> {
    @Override
    public void serialize(IOSAlertData alert, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (!alert.isCompound() && alert.getBody().isPresent()) {
            jgen.writeString(alert.getBody().get());
        } else {
            jgen.writeStartObject();
            if (alert.getBody().isPresent()) {
                jgen.writeStringField("body", alert.getBody().get());
            }
            if (alert.getActionLocKey().isPresent()) {
                jgen.writeStringField("action-loc-key", alert.getActionLocKey().get());
            }
            if (alert.getLocKey().isPresent()) {
                jgen.writeStringField("loc-key", alert.getLocKey().get());
            }
            if (alert.getLocArgs().isPresent()) {
                jgen.writeArrayFieldStart("loc-args");
                for (String value : alert.getLocArgs().get()) {
                    jgen.writeString(value);
                }
                jgen.writeEndArray();
            }
            if (alert.getLaunchImage().isPresent()) {
                jgen.writeStringField("launch-image", alert.getLaunchImage().get());
            }
            jgen.writeEndObject();
        }
    }
}
