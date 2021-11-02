/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.ios;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.ios.IOSAlertData;

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
            if (alert.getTitle().isPresent()) {
                jgen.writeStringField("title", alert.getTitle().get());
            }
            if (alert.getTitleLocArgs().isPresent()) {
                jgen.writeArrayFieldStart("title-loc-args");
                for (String value : alert.getTitleLocArgs().get()) {
                    jgen.writeString(value);
                }
                jgen.writeEndArray();
            }
            if (alert.getTitleLocKey().isPresent()) {
                jgen.writeStringField("title-loc-key", alert.getTitleLocKey().get());
            }
            if (alert.getSummaryArg().isPresent()) {
                jgen.writeStringField("summary-arg", alert.getSummaryArg().get());
            }
            if (alert.getSummaryArgCount().isPresent()) {
                jgen.writeNumberField("summary-arg-count", alert.getSummaryArgCount().get());
            }
            if (alert.getSubtitleLocArgs().isPresent()) {
                jgen.writeArrayFieldStart("subtitle-loc-args");
                for (String value : alert.getSubtitleLocArgs().get()) {
                    jgen.writeString(value);
                }
                jgen.writeEndArray();
            }
            if (alert.getSubtitleLocKey().isPresent()) {
                jgen.writeStringField("subtitle-loc-key", alert.getSubtitleLocKey().get());
            }
            jgen.writeEndObject();
        }
    }
}
