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
                    .put("subject", new FieldParser<EmailPayloadReader>() {
                        public void parse(EmailPayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readSubject(json);
                        }
                    })
                    .put("html_body", new FieldParser<EmailPayloadReader>() {
                        public void parse(EmailPayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readhtmlBody(json);
                        }
                    })
                    .put("plaintext_body", new FieldParser<EmailPayloadReader>() {
                        public void parse(EmailPayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readPlainTextBody(json);
                        }
                    })
                    .put("message_type", new FieldParser<EmailPayloadReader>() {
                        public void parse(EmailPayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readMessageType(json);
                        }
                    })
                    .put("sender_name", new FieldParser<EmailPayloadReader>() {
                        public void parse(EmailPayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readSenderName(json);
                        }
                    })
                    .put("sender_address", new FieldParser<EmailPayloadReader>() {
                        public void parse(EmailPayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readSenderAddress(json);
                        }
                    })
                    .put("reply_to", new FieldParser<EmailPayloadReader>() {
                        public void parse(EmailPayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readReplyTo(json);
                        }
                    })
                    .put("template", new FieldParser<EmailPayloadReader>() {
                        public void parse(EmailPayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readTemplate(json);
                        }
                    })
                    .build()
    );

    private final StandardObjectDeserializer<EmailPayload, ?> deserializer;

    public EmailPayloadDeserializer() {
        deserializer = new StandardObjectDeserializer<>(
                FIELD_PARSERS,
                new Supplier<EmailPayloadReader>() {
                    @Override
                    public EmailPayloadReader get() {
                        return new EmailPayloadReader();
                    }
                }
        );
    }

    @Override
    public EmailPayload deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return deserializer.deserialize(parser, context);
    }
}