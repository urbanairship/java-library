package com.urbanairship.api.push.parse.notification.ios;

import com.urbanairship.api.push.model.notification.ios.Options;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class OptionsSerializer extends JsonSerializer<Options>{

    @Override
    public void serialize(Options options, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();

        if(options.getTime().isPresent()) {
            jgen.writeObjectField("time", options.getTime().get());
        }

        if(options.getCrop().isPresent()) {
            jgen.writeObjectField("crop", options.getCrop().get());
        }

        jgen.writeEndObject();
    }
}