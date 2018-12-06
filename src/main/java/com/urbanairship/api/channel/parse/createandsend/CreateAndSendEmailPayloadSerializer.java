package com.urbanairship.api.channel.parse.createandsend;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.createandsend.model.notification.CreateAndSendEmailPayload;

import java.io.IOException;

public class CreateAndSendEmailPayloadSerializer extends JsonSerializer<CreateAndSendEmailPayload> {

    @Override
    public void serialize(CreateAndSendEmailPayload payload, JsonGenerator jgen, SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException {
        jgen.writeStartObject();

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
