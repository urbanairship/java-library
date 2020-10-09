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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ChannelAttributesPayloadSerializerTest {
    private final ObjectMapper MAPPER = ChannelObjectMapper.getInstance();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

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
                        .addDeviceId(AttributeAudienceType.CHANNEL, "chann_id")
                        .addDeviceId(AttributeAudienceType.CHANNEL, "chann_id2")
                        .addDeviceId(AttributeAudienceType.EMAIL_ADDRESS, "test@test.com")
                        .build())
                .build();

        String json = MAPPER.writeValueAsString(payload);
        String expectedStr = "{\n" +
                "  \"audience\": {\n" +
                "    \"channel\": [\n" +
                "      \"chann_id2\",\n" +
                "      \"chann_id\"\n" +
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
    public void testAbsentValue() throws Exception {
        DateTime now = DateTime.now();

        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Value must not be null when setting attributes");

        Attribute attribute = Attribute.newBuilder()
                .setAction(AttributeAction.SET)
                .setKey("birthday")
                .setTimeStamp(now)
                .build();
    }

    @Test
    public void testEmptyAudience() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Device types or SmsSelectors must be added.");

        AttributeAudience audience = AttributeAudience.newBuilder().build();
    }
}
