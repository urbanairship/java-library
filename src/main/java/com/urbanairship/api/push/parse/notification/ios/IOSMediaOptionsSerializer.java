/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.push.parse.notification.ios;

import com.urbanairship.api.push.model.notification.ios.IOSMediaOptions;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class IOSMediaOptionsSerializer extends JsonSerializer<IOSMediaOptions>{

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
            jgen.writeObjectField("hidden", options.getHidden().get());
        }

        jgen.writeEndObject();
    }
}