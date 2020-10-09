package com.urbanairship.api.channel.parse.attributes;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.channel.Constants;
import com.urbanairship.api.channel.model.attributes.Attribute;
import com.urbanairship.api.common.parse.DateFormats;

import java.io.IOException;

public class AttributeSerializer extends JsonSerializer<Attribute> {

    @Override
    public void serialize(Attribute attribute, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeStringField(Constants.KEY, attribute.getKey());
        jgen.writeStringField(Constants.ACTION, attribute.getAction().getIdentifier());

        if (attribute.getTimeStamp().isPresent()) {
            jgen.writeStringField(Constants.TIMESTAMP, DateFormats.DATE_FORMATTER.print(attribute.getTimeStamp().get()));
        }

        if (attribute.getValue().isPresent()) {
            jgen.writeStringField(Constants.VALUE, attribute.getValue().get());
        }

        jgen.writeEndObject();
    }
}
