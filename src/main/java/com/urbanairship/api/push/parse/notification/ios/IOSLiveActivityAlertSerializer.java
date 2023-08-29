/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.push.parse.notification.ios;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.ios.IOSLiveActivityAlert;
import com.urbanairship.api.push.model.notification.ios.IOSMediaContent;

import java.io.IOException;

public class IOSLiveActivityAlertSerializer extends JsonSerializer<IOSLiveActivityAlert> {
    @Override
    public void serialize(IOSLiveActivityAlert content, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if(content.getBody().isPresent()) {
            jgen.writeStringField("body", content.getBody().get());
        }

        if(content.getTitle().isPresent()) {
            jgen.writeStringField("title", content.getTitle().get());
        }

        if(content.getSound().isPresent()) {
            jgen.writeStringField("sound", content.getSound().get());
        }

        jgen.writeEndObject();
    }
}