package com.urbanairship.api.push.parse.notification.ios;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.urbanairship.api.push.model.notification.ios.IOSFields;

import java.io.IOException;

public class IOSFieldsDeserializer extends JsonDeserializer<IOSFields> {
    @Override
    public IOSFields deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return parser.readValueAs(IOSFields.class);
    }
}
