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
                .setSubject("subject")
                .setPlaintextBody("plaintextBody ua-unsubscribe")
                .setHtmlBody("htmlBody ua-unsubscribe")
                .setMessageType(MessageType.COMMERCIAL)
                .setDeviceType(deviceTypeEmail)
                .setSenderName("John Doe Testerman")
                .setSenderAddress("ua@sparkpost-staging.urbanairship.com")
                .setReplyTo("no-reply@trials.urbanairship.com")
                .build();

        assertEquals(openPayloadEmail.getSubject().get(), "subject");
        assertEquals(openPayloadEmail.getHtmlBody().get(), "htmlBody ua-unsubscribe");
        assertEquals(openPayloadEmail.getPlaintextBody().get(), "plaintextBody ua-unsubscribe");
        assertEquals(openPayloadEmail.getMessageType().get(), MessageType.COMMERCIAL);
        assertEquals(openPayloadEmail.getSenderName().get(), "John Doe Testerman");
        assertEquals(openPayloadEmail.getSenderAddress().get(), "ua@sparkpost-staging.urbanairship.com");
        assertEquals(openPayloadEmail.getReplyTo().get(), "no-reply@trials.urbanairship.com");
    }
}
