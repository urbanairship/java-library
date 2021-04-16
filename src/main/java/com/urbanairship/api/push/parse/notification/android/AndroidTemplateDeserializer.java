package com.urbanairship.api.push.parse.notification.android;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.urbanairship.api.push.model.notification.android.AndroidTemplate;

import java.io.IOException;

public class AndroidTemplateDeserializer extends JsonDeserializer<AndroidTemplate> {
    @Override
    public AndroidTemplate deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return parser.readValueAs(AndroidTemplate.class);
    }
}
