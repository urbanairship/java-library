package com.urbanairship.api.push.parse.notification.web;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.web.WebImage;

import java.io.IOException;

public class WebImageSerializer extends JsonSerializer<WebImage> {
    @Override
    public void serialize(WebImage webImage, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeStringField("url", webImage.getUrl());

        jgen.writeEndObject();
    }
}
