package com.urbanairship.api.createandsend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.createandsend.model.audience.CreateAndSendAudience;
import com.urbanairship.api.createandsend.model.audience.sms.SmsChannel;
import com.urbanairship.api.createandsend.model.audience.sms.SmsChannels;
import com.urbanairship.api.createandsend.model.notification.CreateAndSendPayload;
import com.urbanairship.api.createandsend.model.notification.CreateAndSendSchedulePayload;
import com.urbanairship.api.createandsend.model.notification.sms.SmsPayload;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.apache.http.entity.ContentType;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CreateAndSendScheduleTest {
    private String dateString;
    private CreateAndSendSchedulePayload schedulePayload;
    private CreateAndSendScheduleRequest request;
    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Before
    public void setUp() {
        DateTime dateTime = DateTime.now();

        dateString = dateTime.toString(DateFormats.DATE_FORMATTER);

        Map<String, String> substitutions = new HashMap<>();
        substitutions.put("key1", "value1");
        substitutions.put("key2", "value2");

        SmsChannel smsChannel = SmsChannel.newBuilder()
                .setSender("sender")
                .setMsisdn("msisdn")
                .setOptedIn(dateTime)
                .addAllSubstitutions(substitutions)
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

        CreateAndSendAudience audience = new CreateAndSendAudience(smsChannels);

        PushExpiry expiry = PushExpiry.newBuilder()
                .setExpirySeconds(1000)
                .build();

        SmsPayload smsPayload = SmsPayload.newBuilder()
                .setAlert("smsAlert")
                .setPushExpiry(expiry)
                .build();

        Notification notification = Notification.newBuilder()
                .addDeviceTypeOverride(DeviceType.SMS, smsPayload)
                .build();

        CreateAndSendPayload payload = CreateAndSendPayload.newBuilder()
                .setNotification(notification)
                .setAudience(audience)
                .build();

        schedulePayload = CreateAndSendSchedulePayload.newBuilder()
                .setName("name")
                .setPayload(payload)
                .setScheduleTime(DateTime.parse("2022-01-01T00:00:00.000Z"))
                .build();

        request = CreateAndSendScheduleRequest.newRequest(schedulePayload);
    }

    @Test
    public void testNotification() throws IOException {
        String actualJson = MAPPER.writeValueAsString(schedulePayload);
        String expectedJson = "{\"schedule\":{\"scheduled_time\":\"2022-01-01T00:00:00\"},\"name\":\"name\",\"push\":{\"audience\":{\"create_and_send\":[{\"ua_msisdn\":\"msisdn\",\"ua_sender\":\"sender\",\"ua_opted_in\":\"" + dateString + "\",\"key1\":\"value1\",\"key2\":\"value2\"},{\"ua_msisdn\":\"msisdn2\",\"ua_sender\":\"sender2\",\"ua_opted_in\":\"" + dateString + "\",\"key3\":\"value3\",\"key4\":\"value4\"}]},\"device_types\":[\"sms\"],\"notification\":{\"sms\":{\"alert\":\"smsAlert\",\"expiry\":1000}}}}";

        JsonNode actualNode = MAPPER.readTree(actualJson);
        JsonNode expectedNode = MAPPER.readTree(expectedJson);

        assertEquals(expectedNode, actualNode);
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(request.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(request.getHttpMethod(), Request.HttpMethod.POST);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(request.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedURI = URI.create("https://go.urbanairship.com/api/schedules/create-and-send");
        assertEquals(request.getUri(baseURI), expectedURI);
    }

    
}
