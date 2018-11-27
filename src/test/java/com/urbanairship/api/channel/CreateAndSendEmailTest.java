package com.urbanairship.api.channel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.common.Json;
import com.sun.security.ntlm.Client;
import com.urbanairship.api.channel.model.email.OptInLevel;
import com.urbanairship.api.channel.model.email.RegisterEmailChannel;
import com.urbanairship.api.channel.model.email.RegisterEmailChannelRequest;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
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
            .setCreateAndSendOptInLevel("ua_commercial_opted_in")
            .setCreateAndSendTimestamp("2018-11-29T10:34:22")
            .build();

        RegisterEmailChannel benChannel = RegisterEmailChannel.newBuilder()
                .setUaAddress("ben@icetown.com")
                .setCreateAndSendOptInLevel("ua_commercial_opted_in")
                .setCreateAndSendTimestamp("2018-11-29T12:45:10")
                .build();

        CreateAndSendAudience audience = CreateAndSendAudience.newBuilder()
            .setChannel(newChannel)
            .setChannel(benChannel)
            .build();

        EmailPayload emailPayload = EmailPayload.newBuilder()
                .setDeviceType(DeviceType.EMAIL)
                .setSubject("Welcome to the Winter Sale! ")
                .setHtmlBody(htmlBodyString)
                .setPlaintextBody(plaintextBodyString)
                .setMessageType(MessageType.COMMERCIAL)
                .setSenderName("Urban Airship")
                .setSenderAddress("team@urbanairship.com")
                .setReplyTo("no-reply@urbanairship.com")
                .build();
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
                "        \"ua_commercial_opted_in\": \"2018-11-29T10:34:22\"}";

        try {
            String parsedJson = MAPPER.writeValueAsString(newChannel);
            JsonNode actual = MAPPER.readTree(parsedJson);
            JsonNode expected = MAPPER.readTree(expectedNewChannelString);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testNewAudience(){

        String expectedAudienceString = "{\"audience\":{\"create_and_send\":[{\"ua_address\":\"new@email.com\",\"ua_commercial_opted_in\":\"2018-11-29T10:34:22\"},{\"ua_address\":\"ben@icetown.com\",\"ua_commercial_opted_in\":\"2018-11-29T12:45:10\"}]}}";
        try {
            String parsedJson = MAPPER.writeValueAsString(audience);
            JsonNode actual = MAPPER.readTree(parsedJson);
            JsonNode expected = MAPPER.readTree(expectedAudienceString);

            Assert.assertEquals(expected,actual);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testNewEmailPayload() {

        String expectedEmailpayloadString = "{\"email\":{\"subject\":\"Welcome to the Winter Sale! \",\"html_body\":\"<h1>Seasons Greetings<\\/h1><p>Check out our winter deals!<\\/p><p><a data-ua-unsubscribe=\\\"1\\\" title=\\\"unsubscribe\\\" href=\\\"http://unsubscribe.urbanairship.com/email/success.html\\\">Unsubscribe<\\/a><\\/p>\",\"plaintext_body\":\"Greetings! Check out our latest winter deals! [[ua-unsubscribe href=\\\"http://unsubscribe.urbanairship.com/email/success.html\\\"]]\",\"message_type\":\"commercial\",\"sender_name\":\"Urban Airship\",\"sender_address\":\"team@urbanairship.com\",\"reply_to\":\"no-reply@urbanairship.com\"}}\n";
        try {
            String parsedJson = MAPPER.writeValueAsString(emailPayload);
            JsonNode actual = MAPPER.readTree(parsedJson);
            JsonNode expected = MAPPER.readTree(expectedEmailpayloadString);

            Assert.assertEquals(expected,actual);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
