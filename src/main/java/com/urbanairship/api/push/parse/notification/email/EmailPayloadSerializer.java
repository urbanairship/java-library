package com.urbanairship.api.push.parse.notification.email;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.email.EmailPayload;

import java.io.IOException;

public class EmailPayloadSerializer extends JsonSerializer<EmailPayload> {


    @Override
    public void serialize(EmailPayload payload, JsonGenerator jgen, SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException {
        jgen.writeStartObject();

        if (payload.getAlert().isPresent()) {
            jgen.writeStringField("alert", payload.getAlert().get());
        }

        if (payload.getSubject().isPresent()) {
            jgen.writeStringField("subject", payload.getSubject().get());
        }

        if (payload.getHtmlBody().isPresent()) {
            jgen.writeStringField("html_body", payload.getHtmlBody().get());
        }

        if (payload.getPlaintextBody().isPresent()) {
            jgen.writeStringField("plaintext_body", payload.getPlaintextBody().get());
        }

        if (payload.getMessageType().isPresent()) {
            jgen.writeStringField("message_type", payload.getMessageType().get().getMessageType());
        }

        if (payload.getSenderName().isPresent()) {
            jgen.writeStringField("sender_name", payload.getSenderName().get());
        }

        if (payload.getSenderAddress().isPresent()) {
            jgen.writeStringField("sender_address", payload.getSenderAddress().get());
        }

        if (payload.getReplyTo().isPresent()) {
            jgen.writeStringField("reply_to", payload.getReplyTo().get());
        }

        jgen.writeEndObject();
    }
}
