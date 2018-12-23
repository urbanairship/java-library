package com.urbanairship.api.push.model.notification.email;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.notification.android.AndroidDevicePayload;
import com.urbanairship.api.push.model.notification.open.OpenPayload;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EmailPayloadTest {

    @Test
    public void testBuilder() {
        DeviceType deviceTypeEmail = DeviceType.open("email");
        String expectedPayloadString = "";

        EmailPayload emailPayload = EmailPayload.newBuilder()
                .setSubject("Welcome to the Winter Sale! ")
                .setPlaintextBody("Greetings! Check out our latest winter deals! [[ua-unsubscribe href=\"http://unsubscribe.urbanairship.com/email/success.html\"]]")
                .setHtmlBody("<h1>Seasons Greetings</h1><p>Check out our winter deals!</p><p><a data-ua-unsubscribe=\"1\" title=\"unsubscribe\" href=\"http://unsubscribe.urbanairship.com/email/success.html\">Unsubscribe</a></p>")
                .setMessageType(MessageType.COMMERCIAL)
                .setDeviceType(deviceTypeEmail)
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
}