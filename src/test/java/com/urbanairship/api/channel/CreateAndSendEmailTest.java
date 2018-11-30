package com.urbanairship.api.channel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.common.Json;
import com.sun.org.apache.bcel.internal.generic.PUSH;
import com.sun.security.ntlm.Client;
import com.urbanairship.api.channel.model.email.OptInLevel;
import com.urbanairship.api.channel.model.email.RegisterEmailChannel;
import com.urbanairship.api.channel.model.email.RegisterEmailChannelRequest;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.audience.CreateAndSendAudience;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.model.notification.email.CreateAndSendEmailPayload;
import com.urbanairship.api.push.model.notification.email.EmailPayload;
import com.urbanairship.api.push.model.notification.email.MessageType;
import com.urbanairship.api.push.parse.PushObjectMapper;
import com.urbanairship.api.push.parse.notification.email.EmailPayloadReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;


/***
 * Client is already under test coverage so no need to test headers as they are set during client creation.
 * Here we test the JSON string that is passed to the client prior to sending
 */
public class CreateAndSendEmailTest {

    private static final ObjectMapper CHANNEL_OBJECT_MAPPER = ChannelObjectMapper.getInstance();
    private static final ObjectMapper PUSH_OBJECT_MAPPER = PushObjectMapper.getInstance();

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

        Notification notification = Notification.newBuilder()
            .addDeviceTypeOverride(DeviceType.EMAIL, emailPayload)
            .build();

        CreateAndSendEmailPayload payload = CreateAndSendEmailPayload.newBuilder()
                .setAudience(audience)
                .setNotification(notification)
                .build();


    @Test
    public void testNewChannel() {

        String expectedNewChannelString = "{\n" +
                "        \"ua_address\": \"new@email.com\",\n" +
                "        \"ua_commercial_opted_in\": \"2018-11-29T10:34:22\"}";

        try {
            String parsedJson = CHANNEL_OBJECT_MAPPER.writeValueAsString(newChannel);
            JsonNode actual = CHANNEL_OBJECT_MAPPER.readTree(parsedJson);
            JsonNode expected = CHANNEL_OBJECT_MAPPER.readTree(expectedNewChannelString);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testNewAudience(){

        String expectedAudienceString = "{\"audience\":{\"create_and_send\":[{\"ua_address\":\"new@email.com\",\"ua_commercial_opted_in\":\"2018-11-29T10:34:22\"},{\"ua_address\":\"ben@icetown.com\",\"ua_commercial_opted_in\":\"2018-11-29T12:45:10\"}]}}";
        try {
            String parsedJson = PUSH_OBJECT_MAPPER.writeValueAsString(audience);
            JsonNode actual = PUSH_OBJECT_MAPPER.readTree(parsedJson);
            JsonNode expected = PUSH_OBJECT_MAPPER.readTree(expectedAudienceString);

            Assert.assertEquals(expected,actual);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEmailPayload() {

        String expectedEmailpayloadString = "{\"subject\":\"Welcome to the Winter Sale! \",\"html_body\":\"\\\"<h1>Seasons Greetings</h1><p>Check out our winter deals!</p><p><a data-ua-unsubscribe=\\\\\\\"1\\\\\\\" title=\\\\\\\"unsubscribe\\\\\\\" href=\\\\\\\"http://unsubscribe.urbanairship.com/email/success.html\\\\\\\">Unsubscribe</a></p>\",\"plaintext_body\":\"Greetings! Check out our latest winter deals! [[ua-unsubscribe href=\\\\\\\"http://unsubscribe.urbanairship.com/email/success.html\\\\\\\"]]\",\"message_type\":\"commercial\",\"sender_name\":\"Urban Airship\",\"sender_address\":\"team@urbanairship.com\",\"reply_to\":\"no-reply@urbanairship.com\"}\n";
        try {
            String parsedJson = PUSH_OBJECT_MAPPER.writeValueAsString(emailPayload);
            JsonNode actual = PUSH_OBJECT_MAPPER.readTree(parsedJson);
            JsonNode expected = PUSH_OBJECT_MAPPER.readTree(expectedEmailpayloadString);

            Assert.assertEquals(expected,actual);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testNotification() {

        String expectedNewNotificationString = "{\"email\":{\"subject\":\"Welcome to the Winter Sale! \",\"html_body\":\"\\\"<h1>Seasons Greetings</h1><p>Check out our winter deals!</p><p><a data-ua-unsubscribe=\\\\\\\"1\\\\\\\" title=\\\\\\\"unsubscribe\\\\\\\" href=\\\\\\\"http://unsubscribe.urbanairship.com/email/success.html\\\\\\\">Unsubscribe</a></p>\",\"plaintext_body\":\"Greetings! Check out our latest winter deals! [[ua-unsubscribe href=\\\\\\\"http://unsubscribe.urbanairship.com/email/success.html\\\\\\\"]]\",\"message_type\":\"commercial\",\"sender_name\":\"Urban Airship\",\"sender_address\":\"team@urbanairship.com\",\"reply_to\":\"no-reply@urbanairship.com\"}}\n";

        try {
            String parsedJson = PUSH_OBJECT_MAPPER.writeValueAsString(notification);
            JsonNode actual = PUSH_OBJECT_MAPPER.readTree(parsedJson);
            JsonNode expected = PUSH_OBJECT_MAPPER.readTree(expectedNewNotificationString);

            Assert.assertEquals(expected,actual);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateAndSendEmailPayload() {
        String expectedCreateAndSendEmailPayloadString = "\n" +
                "{\n" +
                "  \"audience\": {\n" +
                "    \"create_and_send\" : [\n" +
                "      {\n" +
                "        \"ua_address\": \"new@email.com\",\n" +
                "        \"ua_commercial_opted_in\": \"2018-11-29T10:34:22\",\n" +
                "      },\n" +
                "      {\n" +
                "        \"ua_address\" : \"ben@icetown.com\",\n" +
                "        \"ua_commercial_opted_in\": \"2018-11-29T12:45:10\",\n" +
                "      }\n" +
                "    ]\n" +
                "  }";
        try {
            String parsedJson = PUSH_OBJECT_MAPPER.writeValueAsString(payload);
            JsonNode actual = PUSH_OBJECT_MAPPER.readTree(parsedJson);
            System.out.println(actual);
            JsonNode expected = PUSH_OBJECT_MAPPER.readTree(expectedCreateAndSendEmailPayloadString);

            Assert.assertEquals(expected,actual);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
