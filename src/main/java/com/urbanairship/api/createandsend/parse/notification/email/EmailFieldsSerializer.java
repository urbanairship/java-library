package com.urbanairship.api.createandsend.parse.notification.email;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.createandsend.model.notification.email.EmailFields;

import java.io.IOException;

public class EmailFieldsSerializer extends JsonSerializer<EmailFields> {
    @Override
    public void serialize(EmailFields emailFields, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeStringField("plaintext_body", emailFields.getPlainTextBody());
        jgen.writeStringField("subject", emailFields.getSubject());

        jgen.writeEndObject();
    }
}
