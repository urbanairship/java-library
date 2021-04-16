package com.urbanairship.api.push.parse.notification.android;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.urbanairship.api.push.model.notification.android.AndroidFields;

import java.io.IOException;

public class AndroidFieldsDeserializer extends JsonDeserializer<AndroidFields> {
    @Override
    public AndroidFields deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return parser.readValueAs(AndroidFields.class);
    }
}
