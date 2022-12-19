package com.urbanairship.api.push.model.notification.email;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EmailPayloadTest {
    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testBuilder() {
        EmailPayload emailPayload = EmailPayload.newBuilder()
                .setSubject("Welcome to the Winter Sale! ")
                .setPlaintextBody("Greetings! Check out our latest winter deals! [[ua-unsubscribe href=\"http://unsubscribe.urbanairship.com/email/success.html\"]]")
                .setHtmlBody("<h1>Seasons Greetings</h1><p>Check out our winter deals!</p><p><a data-ua-unsubscribe=\"1\" title=\"unsubscribe\" href=\"http://unsubscribe.urbanairship.com/email/success.html\">Unsubscribe</a></p>")
                .setMessageType(MessageType.COMMERCIAL)
                .setSenderName("Urban Airship")
                .setSenderAddress("team@urbanairship.com")
                .setReplyTo("no-reply@urbanairship.com")
                .build();

        assertEquals(emailPayload.getSubject().get(), "Welcome to the Winter Sale! ");
        assertEquals(emailPayload.getHtmlBody().get(), "<h1>Seasons Greetings</h1><p>Check out our winter deals!</p><p><a data-ua-unsubscribe=\"1\" title=\"unsubscribe\" href=\"http://unsubscribe.urbanairship.com/email/success.html\">Unsubscribe</a></p>");
        assertEquals(emailPayload.getPlaintextBody().get(), "Greetings! Check out our latest winter deals! [[ua-unsubscribe href=\"http://unsubscribe.urbanairship.com/email/success.html\"]]");
        assertEquals(emailPayload.getMessageType().get(), MessageType.COMMERCIAL);
        assertEquals(emailPayload.getSenderName().get(), "Urban Airship");
        assertEquals(emailPayload.getSenderAddress().get(), "team@urbanairship.com");
        assertEquals(emailPayload.getReplyTo().get(), "no-reply@urbanairship.com");
    }

    @Test
    public void testSerializer() throws JsonProcessingException {
        String expectedPayloadString = "{\n" +
                "    \"subject\": \"Welcome to the Winter Sale! \",\n" +
                "    \"html_body\": \"<h1>Seasons Greetings</h1><p>Check out our winter deals!</p><p><a data-ua-unsubscribe=\\\"1\\\" title=\\\"unsubscribe\\\" href=\\\"http://unsubscribe.urbanairship.com/email/success.html\\\">Unsubscribe</a></p>\",\n" +
                "    \"plaintext_body\": \"Greetings! Check out our latest winter deals! [[ua-unsubscribe href=\\\"http://unsubscribe.urbanairship.com/email/success.html\\\"]]\",\n" +
                "    \"message_type\": \"commercial\",\n" +
                "    \"sender_name\": \"Urban Airship\",\n" +
                "    \"sender_address\": \"team@urbanairship.com\",\n" +
                "    \"reply_to\": \"no-reply@urbanairship.com\",\n" +
                "    \"attachments\": [\n" +
                "        {\n" +
                "            \"id\": \"firstAttachmentId\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"secondAttachmentId\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"click_tracking\": false,\n" +
                "    \"open_tracking\": false\n" +
                "}";

        Attachment attachment = Attachment.newBuilder()
                .setId("firstAttachmentId")
                .build();

        Attachment secondAttachment = Attachment.newBuilder()
                .setId("secondAttachmentId")
                .build();

        EmailPayload emailPayload = EmailPayload.newBuilder()
                .setSubject("Welcome to the Winter Sale! ")
                .setPlaintextBody("Greetings! Check out our latest winter deals! [[ua-unsubscribe href=\"http://unsubscribe.urbanairship.com/email/success.html\"]]")
                .setHtmlBody("<h1>Seasons Greetings</h1><p>Check out our winter deals!</p><p><a data-ua-unsubscribe=\"1\" title=\"unsubscribe\" href=\"http://unsubscribe.urbanairship.com/email/success.html\">Unsubscribe</a></p>")
                .setMessageType(MessageType.COMMERCIAL)
                .setSenderName("Urban Airship")
                .setSenderAddress("team@urbanairship.com")
                .setReplyTo("no-reply@urbanairship.com")
                .addAttachment(attachment)
                .addAttachment(secondAttachment)
                .setClickTracking(false)
                .setOpenTracking(false)
                .build();

        String actualPayloadString = MAPPER.writeValueAsString(emailPayload);

        JsonNode actualJson = MAPPER.readTree(actualPayloadString);
        JsonNode expectedJson = MAPPER.readTree(expectedPayloadString);

        assertEquals(expectedJson, actualJson);
    }
}