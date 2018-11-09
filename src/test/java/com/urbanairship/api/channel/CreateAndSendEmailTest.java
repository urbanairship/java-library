package com.urbanairship.api.channel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.channel.model.CreateAndSendChannel;
import com.urbanairship.api.channel.model.email.OptInLevel;
import com.urbanairship.api.channel.model.email.RegisterEmailChannel;
import com.urbanairship.api.push.model.CreateAndSendPayload;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.audience.CreateAndSendAudience;
import com.urbanairship.api.push.model.notification.email.CreateAndSendEmailNotification;
import com.urbanairship.api.push.model.notification.email.EmailPayload;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class CreateAndSendEmailTest {

    ObjectMapper MAPPER = new ObjectMapper();

    private String addressString = "/api/create-and-send";
    private String headerAcceptString = " application/vnd.urbanairship+json; version=3;";
    private String headerContentTypeString = "application/json";
    private String createAndSendString = "{\n" +
            "  \"audience\": {\n" +
            "    \"create_and_send\" : [\n" +
            "      {\n" +
            "        \"ua_address\": \"new@email.com\",\n" +
            "        \"ua_email_opt_in_level\": \"commercial\",\n" +
            "        \"name\": \"New Person, Esq.\",\n" +
            "        \"address\": \"1001 New Street #400 City State Zip\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"ua_address\" : \"ben@icetown.com\",\n" +
            "        \"ua_email_opt_in_level\": \"commercial\",\n" +
            "        \"name\": \"Ben Wyatt\",\n" +
            "        \"address\": \"1234 Main Street Pawnee IN 46001\"\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +
            "  \"device_types\" : [ \"email\" ],\n" +
            "  \"notification\" : {\n" +
            "    \"email\": {\n" +
            "      \"message_type\": \"commercial\",\n" +
            "      \"sender_name\": \"Urban Airship\",\n" +
            "      \"sender_address\": \"team@urbanairship.com\",\n" +
            "      \"reply_to\": \"no-reply@urbanairship.com\",\n" +
            "      \"template\": {\n" +
            "        \"template_id\" : \"09641749-f288-46e6-8dc6-fae592e8c092\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";

    @Test
    public void testCreateAndSendChannel(){
        String expectedString = "{\n" +
                "        \"ua_address\" : \"ben@icetown.com\",\n" +
                "        \"ua_email_opt_in_level\": \"commercial_opted_in:2018-08-28 00:00:00}";

        RegisterEmailChannel channel = RegisterEmailChannel.newBuilder()
                .setAddress("ben@icetown.com")
                .setEmailOptInLevel(OptInLevel.EMAIL_COMMERCIAL_OPTED_IN, "2018-08-28 00:00:00")
                .build();

        try {
            JsonNode jsonFromObject = MAPPER.readTree(channel.toString());
            JsonNode jsonFromString = MAPPER.readTree(expectedString);

            assertEquals(jsonFromObject, jsonFromString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateAndSendPayload(){


        CreateAndSendChannel firstChannel = CreateAndSendChannel.Builder
                .setUaEmailAddress
                .setUaEmailOptInLevel
                .build();

        CreateAndSendChannel secondChannel = CreateAndSendChannel.newBuilder
                .setUaEmailAddress
                .setUaEmailOptInLevel
                .build();

        CreateAndSendAudience audience = CreateAndSendAudience.newBuilder
                .setChannel(firstChannel)
                .setChannel(secondChannel)
                .build();

        EmailPayload emailPayload = EmailPayload.newBuilder()
                .setDeviceType(DeviceType.EMAIL)
                .setPlaintextBody()
                .setMessageType()
                .setSenderName()
                .setSenderAddress()
                .setReplyTo()
                .build();

        CreateAndSendEmailNotification notification = CreateAndSendEmailNotification.newBuilder
                .setEmailPayload(emailPayload)
                .build();

        CreateAndSendPayload payload = CreateAndSendPayload.newBuilder
                .setAudience(audience)
                .setDeviceTypes
                .setNotification(notification)
                .setTemplate()
                .setCampaign()
                .build();

        Assert.assertEquals(payload.getPayloadString(), createAndSendString);
    }
}
