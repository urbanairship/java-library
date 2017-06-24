/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.push.parse.notification.ios;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.ios.IOSMediaOptions;

import java.io.IOException;

public class IOSMediaOptionsSerializer extends JsonSerializer<IOSMediaOptions> {

    @Override
    public void serialize(IOSMediaOptions options, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();

        if(options.getTime().isPresent()) {
            jgen.writeObjectField("time", options.getTime().get());
        }

        if(options.getCrop().isPresent()) {
            jgen.writeObjectField("crop", options.getCrop().get());
        }

        if(options.getHidden().isPresent()) {
            jgen.writeBooleanField("hidden", options.getHidden().get());
        }

        jgen.writeEndObject();
    }
}