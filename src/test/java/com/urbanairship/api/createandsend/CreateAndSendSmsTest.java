package com.urbanairship.api.createandsend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.createandsend.model.audience.CreateAndSendAudience;
import com.urbanairship.api.createandsend.model.audience.sms.SmsChannel;
import com.urbanairship.api.createandsend.model.audience.sms.SmsChannels;
import com.urbanairship.api.createandsend.model.notification.CreateAndSendPayload;
import com.urbanairship.api.createandsend.model.notification.sms.SmsFields;
import com.urbanairship.api.createandsend.model.notification.sms.SmsPayload;
import com.urbanairship.api.createandsend.model.notification.sms.SmsTemplate;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

import java.io.IOException;

public class CreateAndSendSmsTest {
    private SmsPayload smsPayload;
    private SmsPayload smsPayloadWithTemplate;
    private SmsTemplate smsTemplate;
    private CreateAndSendAudience audience;
    private String dateString;
    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Before
    public void setUp() {
        DateTime dateTime = DateTime.now();

        dateString = dateTime.toString(DateFormats.DATE_FORMATTER);

        SmsChannel smsChannel = SmsChannel.newBuilder()
                .setSender("sender")
                .setMsisdn("msisdn")
                .setOptedIn(dateTime)
                .addSubstitution("key1", "value1")
                .addSubstitution("key2", "value2")
                .build();

        SmsChannel smsChannel1 = SmsChannel.newBuilder()
                .setSender("sender2")
                .setMsisdn("msisdn2")
                .setOptedIn(dateTime)
                .addSubstitution("key3", "value3")
                .addSubstitution("key4", "value4")
                .build();

        SmsChannels smsChannels = SmsChannels.newBuilder()
                .addSmsChannel(smsChannel)
                .addSmsChannel(smsChannel1)
                .build();

        audience = new CreateAndSendAudience(smsChannels);

        PushExpiry expiry = PushExpiry.newBuilder()
                .setExpirySeconds(1000)
                .build();

        SmsFields smsFields = SmsFields.newBuilder()
                .setAlert("smsFieldAlert")
                .build();

        smsTemplate = SmsTemplate.newBuilder()
                .setSmsFields(smsFields)
                .build();

        smsPayloadWithTemplate = SmsPayload.newBuilder()
                .setSmsTemplate(smsTemplate)
                .setPushExpiry(expiry)
                .build();

        smsPayload = SmsPayload.newBuilder()
                .setAlert("smsAlert")
                .setPushExpiry(expiry)
                .build();
    }

    @Test
    public void testSimplePayload() throws IOException {
        String expectedJson = "{\"alert\":\"smsAlert\",\"expiry\":1000}";
        String actualJson = MAPPER.writeValueAsString(smsPayload);

        JsonNode actualNode = MAPPER.readTree(actualJson);
        JsonNode expectedNode = MAPPER.readTree(expectedJson);

        assertEquals(expectedNode, actualNode);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadPayload() {
        SmsPayload smsPayload = SmsPayload.newBuilder()
                .setAlert("Awesome Alert")
                .setSmsTemplate(smsTemplate)
                .build();
    }

    @Test
    public void testFields() throws IOException {
        String expectedJson = "{\"alert\":\"smsFieldAlert\"}";
        String actualJson = MAPPER.writeValueAsString(smsPayloadWithTemplate.getSmsTemplate().get().getSmsFields().get());

        JsonNode actualNode = MAPPER.readTree(actualJson);
        JsonNode expectedNode = MAPPER.readTree(expectedJson);

        assertEquals(expectedNode, actualNode);
    }

    @Test
    public void testSmsTemplate() throws IOException {
        String expectedJson = "{\"fields\":{\"alert\":\"smsFieldAlert\"}}";
        String actualJson = MAPPER.writeValueAsString(smsPayloadWithTemplate.getSmsTemplate().get());

        JsonNode actualNode = MAPPER.readTree(actualJson);
        JsonNode expectedNode = MAPPER.readTree(expectedJson);

        assertEquals(expectedNode, actualNode);
    }

    @Test
    public void testSmsPayload() throws IOException {
        String expectedJson = "{\"template\":{\"fields\":{\"alert\":\"smsFieldAlert\"}},\"expiry\":1000}";
        String actualJson = MAPPER.writeValueAsString(smsPayloadWithTemplate);

        JsonNode actualNode = MAPPER.readTree(actualJson);
        JsonNode expectedNode = MAPPER.readTree(expectedJson);

        assertEquals(expectedNode, actualNode);
    }

    @Test
    public void testAudience() throws IOException {
        String expectedJson  = "{\n" +
                "    \"create_and_send\": [\n" +
                "        {\n" +
                "            \"ua_msisdn\": \"msisdn\",\n" +
                "            \"ua_sender\": \"sender\",\n" +
                "            \"ua_opted_in\": \"" + dateString + "\",\n" +
                "            \"key1\": \"value1\",\n" +
                "            \"key2\": \"value2\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"ua_msisdn\": \"msisdn2\",\n" +
                "            \"ua_sender\": \"sender2\",\n" +
                "            \"ua_opted_in\": \"" + dateString + "\",\n" +
                "            \"key3\": \"value3\",\n" +
                "            \"key4\": \"value4\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        String actualJson = MAPPER.writeValueAsString(audience);

        JsonNode actualNode = MAPPER.readTree(actualJson);
        JsonNode expectedNode = MAPPER.readTree(expectedJson);

        assertEquals(expectedNode, actualNode);
    }

    @Test
    public void testNotification() throws IOException {
        Notification notification = Notification.newBuilder()
                .addDeviceTypeOverride(DeviceType.SMS, smsPayload)
                .build();

        CreateAndSendPayload payload = CreateAndSendPayload.newBuilder()
                .setNotification(notification)
                .setAudience(audience)
                .build();


        String actualJson = MAPPER.writeValueAsString(payload);
        String expectedJson = "{\n" +
                "    \"audience\": {\n" +
                "        \"create_and_send\": [\n" +
                "            {\n" +
                "                \"ua_msisdn\": \"msisdn\",\n" +
                "                \"ua_sender\": \"sender\",\n" +
                "                \"ua_opted_in\": \"" + dateString + "\",\n" +
                "                \"key1\": \"value1\",\n" +
                "                \"key2\": \"value2\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"ua_msisdn\": \"msisdn2\",\n" +
                "                \"ua_sender\": \"sender2\",\n" +
                "                \"ua_opted_in\": \"" + dateString + "\",\n" +
                "                \"key3\": \"value3\",\n" +
                "                \"key4\": \"value4\"\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    \"device_types\": [\n" +
                "        \"sms\"\n" +
                "    ],\n" +
                "    \"notification\": {\n" +
                "        \"sms\": {\n" +
                "            \"alert\": \"smsAlert\",\n" +
                "            \"expiry\": 1000\n" +
                "        }\n" +
                "    }\n" +
                "}";

        JsonNode actualNode = MAPPER.readTree(actualJson);
        JsonNode expectedNode = MAPPER.readTree(expectedJson);

        assertEquals(expectedNode, actualNode);
    }
}
