/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.push.parse.notification.ios;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.ios.IOSLiveActivity;
import com.urbanairship.api.push.model.notification.ios.IOSLiveActivityAlert;

import java.io.IOException;

public class IOSLiveActivitySerializer extends JsonSerializer<IOSLiveActivity> {
    @Override
    public void serialize(IOSLiveActivity content, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeStringField("event", content.getIosLiveActivityEvent().getIosLiveActivityEvent());
        jgen.writeStringField("name", content.getName());

        if(content.getIosLiveActivityAlert().isPresent()) {
            jgen.writeObjectField("alert", content.getIosLiveActivityAlert());
        }

        if(!content.getContentState().get().isEmpty()) {
            jgen.writeObjectField("content_state", content.getContentState());
        }

        if(content.getDismissalDate().isPresent()) {
            jgen.writeNumberField("dismissal_date", content.getDismissalDate().get());
        }

        if(content.getPriority().isPresent()) {
            jgen.writeNumberField("priority", content.getPriority().get());
        }

        if(content.getRelevanceScore().isPresent()) {
            jgen.writeNumberField("relevance_score", content.getRelevanceScore().get());
        }

        if(content.getStaleDate().isPresent()) {
            jgen.writeNumberField("stale_date", content.getStaleDate().get());
        }

        if(content.getTimestamp().isPresent()) {
            jgen.writeNumberField("timestamp", content.getTimestamp().get());
        }

        if(content.getAttributesType().isPresent()) {
            jgen.writeStringField("attributes_Type", content.getAttributesType().get());
        }

        if(!content.getAttributes().get().isEmpty()) {
            jgen.writeObjectField("attributes", content.getAttributes());
        }

        jgen.writeEndObject();
    }
}