package com.urbanairship.api.createandsend.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.createandsend.model.notification.Fields;

import java.io.IOException;

public class FieldsSerializer extends JsonSerializer<Fields> {
    @Override
    public void serialize(Fields fields, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeStringField("plaintext_body", fields.getPlainTextBody());
        jgen.writeStringField("subject", fields.getSubject());

        jgen.writeEndObject();
    }
}
