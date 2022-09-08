package com.urbanairship.api.channel.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.channel.model.attributes.Attribute;
import com.urbanairship.api.channel.model.attributes.AttributeAction;
import com.urbanairship.api.channel.model.attributes.ChannelAttributesPayload;
import com.urbanairship.api.channel.model.attributes.audience.AttributeAudience;
import com.urbanairship.api.channel.model.attributes.audience.AttributeAudienceType;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.push.model.audience.sms.SmsSelector;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

public class ChannelAttributesPayloadSerializerTest {
    private final ObjectMapper MAPPER = ChannelObjectMapper.getInstance();

    @Test
    public void testBasicPayload() throws Exception {
        DateTime dateTime = DateFormats.DATE_FORMATTER.parseDateTime("2020-10-03T00:39:27");

        Attribute attribute = Attribute.newBuilder()
                .setAction(AttributeAction.SET)
                .setKey("birthday")
                .setValue(dateTime)
                .setTimeStamp(dateTime)
                .build();

        SmsSelector smsSelector = SmsSelector.newBuilder()
                .setSender("sender_id")
                .setMsisdn("msisdn_id")
                .build();

        ChannelAttributesPayload payload = ChannelAttributesPayload.newBuilder()
                .addAttribute(attribute)
                .setAudience(AttributeAudience.newBuilder()
                        .addSmsSelector(smsSelector)
                        .addDeviceId(AttributeAudienceType.CHANNEL, "channel_id")
                        .addDeviceId(AttributeAudienceType.CHANNEL, "channel_id2")
                        .addDeviceId(AttributeAudienceType.EMAIL_ADDRESS, "test@test.com")
                        .build())
                .build();

        String json = MAPPER.writeValueAsString(payload);
        String expectedStr = "{\n" +
                "  \"audience\": {\n" +
                "    \"channel\": [\n" +
                "      \"channel_id2\",\n" +
                "      \"channel_id\"\n" +
                "    ],\n" +
                "    \"email_address\": [\n" +
                "      \"test@test.com\"\n" +
                "    ],\n" +
                "    \"sms_id\": [{\n" +
                "      \"msisdn\": \"msisdn_id\",\n" +
                "      \"sender\": \"sender_id\"\n" +
                "    }]\n" +
                "  },\n" +
                "  \"attributes\": [\n" +
                "    {\n" +
                "      \"key\": \"birthday\",\n" +
                "      \"action\": \"set\",\n" +
                "      \"timestamp\": \"2020-10-03T00:39:27\",\n" +
                "      \"value\": \"2020-10-03T00:39:27\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        JsonNode expectedJson = MAPPER.readTree(expectedStr);
        JsonNode actualJson = MAPPER.readTree(json);

        org.junit.Assert.assertEquals(expectedJson, actualJson);
    }

    @Test
    public void testSmsAudience() throws Exception {
        DateTime dateTime = DateFormats.DATE_FORMATTER.parseDateTime("2020-10-03T00:39:27");

        Attribute attribute = Attribute.newBuilder()
                .setAction(AttributeAction.SET)
                .setKey("birthday")
                .setValue(dateTime)
                .setTimeStamp(dateTime)
                .build();

        SmsSelector smsSelector = SmsSelector.newBuilder()
                .setSender("sender_id")
                .setMsisdn("msisdn_id")
                .build();

        ChannelAttributesPayload payload = ChannelAttributesPayload.newBuilder()
                .addAttribute(attribute)
                .setAudience(AttributeAudience.newBuilder()
                        .addSmsSelector(smsSelector)
                        .build())
                .build();

        String json = MAPPER.writeValueAsString(payload);

        String expectedJsonStr = "{\n" +
                "  \"audience\": {\n" +
                "    \"sms_id\": [{\n" +
                "      \"msisdn\": \"msisdn_id\",\n" +
                "      \"sender\": \"sender_id\"\n" +
                "    }]\n" +
                "  },\n" +
                "  \"attributes\": [\n" +
                "    {\n" +
                "      \"key\": \"birthday\",\n" +
                "      \"action\": \"set\",\n" +
                "      \"timestamp\": \"2020-10-03T00:39:27\",\n" +
                "      \"value\": \"2020-10-03T00:39:27\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        JsonNode expectedJson = MAPPER.readTree(expectedJsonStr);
        JsonNode actualJson = MAPPER.readTree(json);

        org.junit.Assert.assertEquals(expectedJson, actualJson);
    }

    @Test
    public void testAbsentValue() {
        DateTime now = DateTime.now();

        Exception exception = Assert.assertThrows(NullPointerException.class, () -> Attribute.newBuilder()
        .setAction(AttributeAction.SET)
        .setKey("birthday")
        .setTimeStamp(now)
        .build());
        
        String expectedMessage = "Value must not be null when setting attributes";
        String actualMessage = exception.getMessage();

        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testEmptyAudience() {

        Exception exception = Assert.assertThrows(IllegalArgumentException.class, () -> AttributeAudience.newBuilder().build());

        String expectedMessage = "Device types or SmsSelectors must be added.";
        String actualMessage = exception.getMessage();

        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }
}
