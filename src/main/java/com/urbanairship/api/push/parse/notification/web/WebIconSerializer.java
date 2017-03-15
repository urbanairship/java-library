package com.urbanairship.api.push.parse.notification.web;

import com.urbanairship.api.push.model.notification.web.WebIcon;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class WebIconSerializer extends JsonSerializer<WebIcon>{
    @Override
    public void serialize(WebIcon webIcon, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();

        jgen.writeStringField("url", webIcon.getUrl());

        jgen.writeEndObject();
    }
}
