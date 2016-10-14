/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.push.parse.notification.ios;

import com.urbanairship.api.push.model.notification.ios.Crop;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class CropSerializer extends JsonSerializer<Crop>{
    @Override
    public void serialize(Crop crop, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();

        if (crop.getX().isPresent()) {
            jgen.writeObjectField("x", crop.getX().get());
        }

        if (crop.getY().isPresent()) {
            jgen.writeObjectField("y", crop.getY().get());
        }

        if (crop.getWidth().isPresent()) {
            jgen.writeObjectField("width", crop.getWidth().get());
        }

        if (crop.getHeight().isPresent()) {
            jgen.writeObjectField("height", crop.getHeight().get());
        }

        jgen.writeEndObject();
    }
}
