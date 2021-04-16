package com.urbanairship.api.push.parse.notification.adm;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.urbanairship.api.push.model.notification.adm.ADMFields;

import java.io.IOException;

public class ADMFieldsDeserializer extends JsonDeserializer<ADMFields> {
    @Override
    public ADMFields deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return parser.readValueAs(ADMFields.class);
    }
}
