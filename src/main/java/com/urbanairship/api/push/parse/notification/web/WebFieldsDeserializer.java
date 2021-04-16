package com.urbanairship.api.push.parse.notification.web;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.urbanairship.api.push.model.notification.web.WebFields;

import java.io.IOException;

public class WebFieldsDeserializer extends JsonDeserializer<WebFields> {
    @Override
    public WebFields deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return parser.readValueAs(WebFields.class);
    }
}
