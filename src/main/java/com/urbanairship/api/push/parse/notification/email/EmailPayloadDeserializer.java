package com.urbanairship.api.push.parse.notification.email;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.email.EmailPayload;

import java.io.IOException;

public class EmailPayloadDeserializer extends JsonDeserializer<EmailPayload> {

    private static final FieldParserRegistry<EmailPayload, EmailPayloadReader> FIELD_PARSERS = new MapFieldParserRegistry<EmailPayload, EmailPayloadReader>(
            ImmutableMap.<String, FieldParser<EmailPayloadReader>>builder()
                    .put("subject", (reader, json, context) -> reader.readSubject(json))
                    .put("html_body", (reader, json, context) -> reader.readhtmlBody(json))
                    .put("plaintext_body", (reader, json, context) -> reader.readPlainTextBody(json))
                    .put("message_type", (reader, json, context) -> reader.readMessageType(json))
                    .put("sender_name", (reader, json, context) -> reader.readSenderName(json))
                    .put("sender_address", (reader, json, context) -> reader.readSenderAddress(json))
                    .put("reply_to", (reader, json, context) -> reader.readReplyTo(json))
                    .put("template", (reader, json, context) -> reader.readTemplate(json))
                    .build()
    );

    private final StandardObjectDeserializer<EmailPayload, ?> deserializer;

    public EmailPayloadDeserializer() {
        deserializer = new StandardObjectDeserializer<>(
                FIELD_PARSERS,
                () -> new EmailPayloadReader()
        );
    }

    @Override
    public EmailPayload deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return deserializer.deserialize(parser, context);
    }
}