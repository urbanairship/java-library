package com.urbanairship.api.push.parse.notification.web;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.urbanairship.api.push.model.notification.web.WebTemplate;

import java.io.IOException;

public class WebTemplateDeserializer extends JsonDeserializer<WebTemplate> {
    @Override
    public WebTemplate deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return parser.readValueAs(WebTemplate.class);
    }
}
