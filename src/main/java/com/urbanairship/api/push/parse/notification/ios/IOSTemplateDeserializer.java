package com.urbanairship.api.push.parse.notification.ios;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.urbanairship.api.push.model.notification.ios.IOSTemplate;

import java.io.IOException;

public class IOSTemplateDeserializer extends JsonDeserializer<IOSTemplate> {
    @Override
    public IOSTemplate deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return parser.readValueAs(IOSTemplate.class);
    }
}
