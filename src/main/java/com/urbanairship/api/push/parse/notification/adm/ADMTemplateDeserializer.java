package com.urbanairship.api.push.parse.notification.adm;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.urbanairship.api.push.model.notification.adm.ADMTemplate;

import java.io.IOException;

public class ADMTemplateDeserializer extends JsonDeserializer<ADMTemplate> {
    @Override
    public ADMTemplate deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return parser.readValueAs(ADMTemplate.class);
    }
}
