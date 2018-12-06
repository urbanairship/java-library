package com.urbanairship.api.createandsend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import com.urbanairship.api.client.UrbanAirshipClient;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.createandsend.model.audience.EmailChannel;
import com.urbanairship.api.createandsend.model.audience.EmailChannels;
import com.urbanairship.api.push.model.Campaigns;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.createandsend.model.audience.CreateAndSendAudience;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.createandsend.model.notification.CreateAndSendPayload;
import com.urbanairship.api.push.model.notification.email.EmailPayload;
import com.urbanairship.api.push.model.notification.email.MessageType;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


/***
 * Client is already under test coverage so no need to test headers as they are set during client creation.
 * Here we test the JSON string that is passed to the client prior to sending
 */
public class CreateAndSendEmailTest {

    private static final ObjectMapper CHANNEL_OBJECT_MAPPER = ChannelObjectMapper.getInstance();
    private static final ObjectMapper PUSH_OBJECT_MAPPER = PushObjectMapper.getInstance();

    String htmlBodyString = "<h1>Seasons Greetings</h1><p>Check out our winter deals!</p><p><a data-ua-unsubscribe=\"1\" title=\"unsubscribe\" href=\"http://unsubscribe.urbanairship.com/email/success.html\">Unsubscribe</a></p>";
    String plaintextBodyString = "Greetings! Check out our latest winter deals! [[ua-unsubscribe href=\"http://unsubscribe.urbanairship.com/email/success.html\"]]";

    EmailChannel newChannel;
    EmailChannel benChannel;
    CreateAndSendAudience audience;
    EmailPayload emailPayload;
    Notification notification;
    Campaigns campaign;
    CreateAndSendPayload payload;
    CreateAndSendRequest request;
    UrbanAirshipClient client;

    @Before
    public void setUp() {
        DateTime newDateTime = DateTime.parse("2018-11-29T10:34:22", DateFormats.DATE_FORMATTER);
        DateTime benDateTime = DateTime.parse("2018-11-29T12:45:10", DateFormats.DATE_FORMATTER);

        newChannel = EmailChannel.newBuilder()
                .setAddress("new@email.com")
                .setCommertialOptedIn(newDateTime)
                .build();

        benChannel = EmailChannel.newBuilder()
                .setAddress("ben@icetown.com")
                .setCommertialOptedIn(benDateTime)
                .build();

        audience = new CreateAndSendAudience(EmailChannels.newBuilder()
                .addChannel(newChannel)
                .addChannel(benChannel)
                .build());

        emailPayload = EmailPayload.newBuilder()
                .setDeviceType(DeviceType.EMAIL)
                .setSubject("Welcome to the Winter Sale! ")
                .setHtmlBody(htmlBodyString)
                .setPlaintextBody(plaintextBodyString)
                .setMessageType(MessageType.COMMERCIAL)
                .setSenderName("Urban Airship")
                .setSenderAddress("team@urbanairship.com")
                .setReplyTo("no-reply@urbanairship.com")
                .build();

        notification = Notification.newBuilder()
                .addDeviceTypeOverride(DeviceType.EMAIL, emailPayload)
                .build();

        campaign = Campaigns.newBuilder()
                .addCategory("winter sale")
                .addCategory("west coast")
                .build();

        payload = CreateAndSendPayload.newBuilder()
                .setAudience(audience)
                .setNotification(notification)
                .setCampaigns(campaign)
                .build();

        request = CreateAndSendRequest.newRequest(payload);

        client = UrbanAirshipClient.newBuilder()
                .setKey("ISex_TTJRuarzs9-o_Gkhg")
                .setSecret("nDq-bQ3CT92PqCIXNtQyCQ")
                .build();
    }

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
    public void testNewAudience() {

        String expectedAudienceString = "{\"create_and_send\":[{\"ua_address\":\"new@email.com\",\"ua_commercial_opted_in\":\"2018-11-29T10:34:22\"},{\"ua_address\":\"ben@icetown.com\",\"ua_commercial_opted_in\":\"2018-11-29T12:45:10\"}]}";
        try {
            String parsedJson = PUSH_OBJECT_MAPPER.writeValueAsString(audience);
            JsonNode actual = PUSH_OBJECT_MAPPER.readTree(parsedJson);
            JsonNode expected = PUSH_OBJECT_MAPPER.readTree(expectedAudienceString);

            Assert.assertEquals(expected, actual);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEmailPayload() {

        String expectedEmailpayloadString = "{\"subject\":\"Welcome to the Winter Sale! \",\"html_body\":\"<h1>Seasons Greetings</h1><p>Check out our winter deals!</p><p><a data-ua-unsubscribe=\\\"1\\\" title=\\\"unsubscribe\\\" href=\\\"http://unsubscribe.urbanairship.com/email/success.html\\\">Unsubscribe</a></p>\",\"plaintext_body\":\"Greetings! Check out our latest winter deals! [[ua-unsubscribe href=\\\"http://unsubscribe.urbanairship.com/email/success.html\\\"]]\",\"message_type\":\"commercial\",\"sender_name\":\"Urban Airship\",\"sender_address\":\"team@urbanairship.com\",\"reply_to\":\"no-reply@urbanairship.com\"}";
        try {
            String parsedJson = PUSH_OBJECT_MAPPER.writeValueAsString(emailPayload);
            JsonNode actual = PUSH_OBJECT_MAPPER.readTree(parsedJson);
            JsonNode expected = PUSH_OBJECT_MAPPER.readTree(expectedEmailpayloadString);

            Assert.assertEquals(expected, actual);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testNotification() {

        String expectedNewNotificationString = "{\"email\":{\"subject\":\"Welcome to the Winter Sale! \",\"html_body\":\"<h1>Seasons Greetings</h1><p>Check out our winter deals!</p><p><a data-ua-unsubscribe=\\\"1\\\" title=\\\"unsubscribe\\\" href=\\\"http://unsubscribe.urbanairship.com/email/success.html\\\">Unsubscribe</a></p>\",\"plaintext_body\":\"Greetings! Check out our latest winter deals! [[ua-unsubscribe href=\\\"http://unsubscribe.urbanairship.com/email/success.html\\\"]]\",\"message_type\":\"commercial\",\"sender_name\":\"Urban Airship\",\"sender_address\":\"team@urbanairship.com\",\"reply_to\":\"no-reply@urbanairship.com\"}}";

        try {
            String parsedJson = PUSH_OBJECT_MAPPER.writeValueAsString(notification);
            JsonNode actual = PUSH_OBJECT_MAPPER.readTree(parsedJson);
            JsonNode expected = PUSH_OBJECT_MAPPER.readTree(expectedNewNotificationString);

            Assert.assertEquals(expected, actual);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateAndSendEmailPayload() {
        String expectedCreateAndSendEmailPayloadString = "{\n" +
                "\t\"audience\": {\n" +
                "\t\t\"create_and_send\": [{\n" +
                "\t\t\t\t\"ua_address\": \"new@email.com\",\n" +
                "\t\t\t\t\"ua_commercial_opted_in\": \"2018-11-29T10:34:22\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"ua_address\": \"ben@icetown.com\",\n" +
                "\t\t\t\t\"ua_commercial_opted_in\": \"2018-11-29T12:45:10\"\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t},\n" +
                "\t\"device_types\": [\"email\"],\n" +
                "\t\"notification\": {\n" +
                "\t\t\"email\": {\n" +
                "\t\t\t\"subject\": \"Welcome to the Winter Sale! \",\n" +
                "\t\t\t\"html_body\": \"<h1>Seasons Greetings</h1><p>Check out our winter deals!</p><p><a data-ua-unsubscribe=\\\"1\\\" title=\\\"unsubscribe\\\" href=\\\"http://unsubscribe.urbanairship.com/email/success.html\\\">Unsubscribe</a></p>\",\n" +
                "\t\t\t\"plaintext_body\": \"Greetings! Check out our latest winter deals! [[ua-unsubscribe href=\\\"http://unsubscribe.urbanairship.com/email/success.html\\\"]]\",\n" +
                "\t\t\t\"message_type\": \"commercial\",\n" +
                "\t\t\t\"sender_name\": \"Urban Airship\",\n" +
                "\t\t\t\"sender_address\": \"team@urbanairship.com\",\n" +
                "\t\t\t\"reply_to\": \"no-reply@urbanairship.com\"\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t\"campaigns\": {\n" +
                "\t\t\"categories\": [\"winter sale\", \"west coast\"]\n" +
                "\t}\n" +
                "}";
        try {
            String parsedJson = PUSH_OBJECT_MAPPER.writeValueAsString(payload);
            JsonNode actual = PUSH_OBJECT_MAPPER.readTree(parsedJson);
            JsonNode expected = PUSH_OBJECT_MAPPER.readTree(expectedCreateAndSendEmailPayloadString);

            Assert.assertEquals(expected, actual);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

