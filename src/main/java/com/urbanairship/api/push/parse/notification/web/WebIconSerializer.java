package com.urbanairship.api.push.parse.notification.web;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.web.WebIcon;

import java.io.IOException;

public class WebIconSerializer extends JsonSerializer<WebIcon> {
    @Override
    public void serialize(WebIcon webIcon, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();

        jgen.writeStringField("url", webIcon.getUrl());

        jgen.writeEndObject();
    }
}
