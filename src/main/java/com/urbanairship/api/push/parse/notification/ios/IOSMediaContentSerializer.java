/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.push.parse.notification.ios;

import com.urbanairship.api.push.model.notification.ios.IOSMediaContent;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class IOSMediaContentSerializer extends JsonSerializer<IOSMediaContent> {
    @Override
    public void serialize(IOSMediaContent content, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if(content.getBody().isPresent()) {
            jgen.writeStringField("body", content.getBody().get());
        }

        if(content.getTitle().isPresent()) {
            jgen.writeStringField("title", content.getTitle().get());
        }

        if(content.getSubtitle().isPresent()) {
            jgen.writeStringField("subtitle", content.getSubtitle().get());
        }

        jgen.writeEndObject();
    }
}