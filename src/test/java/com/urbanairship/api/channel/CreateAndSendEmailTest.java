package com.urbanairship.api.channel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.security.ntlm.Client;
import com.urbanairship.api.channel.model.email.OptInLevel;
import com.urbanairship.api.channel.model.email.RegisterEmailChannel;
import com.urbanairship.api.channel.model.email.RegisterEmailChannelRequest;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import com.urbanairship.api.push.model.CreateAndSendPayload;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.audience.CreateAndSendAudience;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.model.notification.email.CreateAndSendEmailNotification;
import com.urbanairship.api.push.model.notification.email.EmailPayload;
import com.urbanairship.api.push.model.notification.email.MessageType;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;


/***
 * Client is already under test coverage so no need to test headers as they are set during client creation.
 * Here we test the JSON string that is passed to the client prior to sending
 */
public class CreateAndSendEmailTest {

    private static final ObjectMapper MAPPER = ChannelObjectMapper.getInstance();

    String channelString = "";
    String audienceString = "";
    String emailPayloadString = "";
    String notificationString = "";
    String payloadString = "";

    String htmlBodyString = "\"<h1>Seasons Greetings</h1><p>Check out our winter deals!</p>" +
                "<p><a data-ua-unsubscribe=\\\"1\\\" title=\\\"unsubscribe\\\" " +
                "href=\\\"http://unsubscribe.urbanairship.com/email/success.html\\\">Unsubscribe</a></p>";
    String plaintextBodyString = "Greetings! Check out our latest winter deals! " +
                "[[ua-unsubscribe href=\\\"http://unsubscribe.urbanairship.com/email/success.html\\\"]]";

    RegisterEmailChannel newChannel = RegisterEmailChannel.newBuilder()
            .setUaAddress("new@email.com")
            .setEmailOptInLevel(OptInLevel.EMAIL_COMMERCIAL_OPTED_IN,"2019-10-12T12:12:12")
            .build();

        RegisterEmailChannel benChannel = RegisterEmailChannel.newBuilder()
                .setUaAddress("ben@icetown.com")
                .setEmailOptInLevel(OptInLevel.EMAIL_TRANSACTIONAL_OPTED_IN,"2019-10-12T12:12:12")
                .build();

        CreateAndSendAudience audience = CreateAndSendAudience.newBuilder()
            .setChannel(newChannel)
            .setChannel(benChannel)
            .build();
//
//        EmailPayload emailPayload = EmailPayload.newBuilder()
//                .setSubject("Welcome to the Winter Sale! ")
//                .setHtmlBody(htmlBodyString)
//                .setPlaintextBody(plaintextBodyString)
//                .setMessageType(MessageType.COMMERCIAL)
//                .setSenderName("Urban Airship")
//                .setSenderAddress("team@urbanairship.com")
//                .setReplyTo("no-reply@urbanairship.com")
//                .build();
//
//        Notification notification = Notification.newBuilder()
//                .setEmailPayload(emailPayload)
//                .build();
//
//        CreateAndSendPayload payload = CreateAndSendPayload.newBuilder()
//                .setAudience(audience)
//                .setDeviceType(DeviceType.EMAIL)
//                .setNotification(notification)
//                .build();


    @Test
    public void testNewChannel() {
        String expectedNewChannelString = "{\n" +
                "        \"ua_address\": \"new@email.com\",\n" +
                "        \"ua_commercial_opted_in\": \"2018-11-29T10:34:22\",\n}";
        try {
            Assert.assertEquals(expectedNewChannelString, MAPPER.readTree(newChannel.toJSON()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
