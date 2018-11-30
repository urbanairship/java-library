package com.urbanairship.api.push.parse.notification.email;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.push.model.notification.email.EmailPayload;
import com.urbanairship.api.push.model.notification.email.MessageType;

import java.io.IOException;

public class EmailPayloadReader implements JsonObjectReader<EmailPayload> {

    private final EmailPayload.Builder builder;

    public EmailPayloadReader() {
        this.builder = EmailPayload.newBuilder();
    }

    public void readSubject(JsonParser parser) throws IOException {
        builder.setSubject(StringFieldDeserializer.INSTANCE.deserialize(parser, "subject"));
    }

    public void readhtmlBody(JsonParser parser) throws IOException {
        builder.setHtmlBody(StringFieldDeserializer.INSTANCE.deserialize(parser, "html_body"));
    }

    public void readPlainTextBody(JsonParser parser) throws IOException {
        builder.setPlaintextBody(StringFieldDeserializer.INSTANCE.deserialize(parser, "plaintext_body"));
    }

    public void readMessageType(JsonParser parser) throws IOException {
        builder.setMessageType(parser.readValueAs(MessageType.class));
    }

    public void readSenderName(JsonParser parser) throws IOException {
        builder.setSenderName(StringFieldDeserializer.INSTANCE.deserialize(parser, "sender_name"));
    }

    public void readSenderAddress(JsonParser parser) throws IOException {
        builder.setSenderAddress(StringFieldDeserializer.INSTANCE.deserialize(parser, "sender_address"));
    }

    public void readReplyTo(JsonParser parser) throws IOException {
        builder.setSenderName(StringFieldDeserializer.INSTANCE.deserialize(parser, "reply_to"));
    }

    @Override
    public EmailPayload validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}