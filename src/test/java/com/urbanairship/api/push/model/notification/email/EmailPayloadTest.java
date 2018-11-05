package com.urbanairship.api.push.model.notification.email;

import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.notification.android.AndroidDevicePayload;
import com.urbanairship.api.push.model.notification.open.OpenPayload;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EmailPayloadTest {

    @Test
    public void testBuilder() {
        DeviceType deviceTypeEmail = DeviceType.open("email");

        EmailPayload openPayloadEmail = EmailPayload.newBuilder()
                .setSubject("Welcome to the Winter Sale! ")
                .setPlaintextBody("Greetings! Check out our latest winter deals! [[ua-unsubscribe href=\"http://unsubscribe.urbanairship.com/email/success.html\"]]")
                .setHtmlBody("<h1>Seasons Greetings</h1><p>Check out our winter deals!</p><p><a data-ua-unsubscribe=\"1\" title=\"unsubscribe\" href=\"http://unsubscribe.urbanairship.com/email/success.html\">Unsubscribe</a></p>")
                .setMessageType(MessageType.COMMERCIAL)
                .setDeviceType(deviceTypeEmail)
                .setSenderName("Urban Airship")
                .setSenderAddress("team@urbanairship.com")
                .setReplyTo("no-reply@urbanairship.com")
                .build();

        assertEquals(openPayloadEmail.getSubject().get(), "Welcome to the Winter Sale! ");
        assertEquals(openPayloadEmail.getHtmlBody().get(), "<h1>Seasons Greetings</h1><p>Check out our winter deals!</p><p><a data-ua-unsubscribe=\"1\" title=\"unsubscribe\" href=\"http://unsubscribe.urbanairship.com/email/success.html\">Unsubscribe</a></p>");
        assertEquals(openPayloadEmail.getPlaintextBody().get(), "Greetings! Check out our latest winter deals! [[ua-unsubscribe href=\"http://unsubscribe.urbanairship.com/email/success.html\"]]");
        assertEquals(openPayloadEmail.getMessageType().get(), MessageType.COMMERCIAL);
        assertEquals(openPayloadEmail.getSenderName().get(), "Urban Airship");
        assertEquals(openPayloadEmail.getSenderAddress().get(), "team@urbanairship.com");
        assertEquals(openPayloadEmail.getReplyTo().get(), "no-reply@urbanairship.com");
    }
}