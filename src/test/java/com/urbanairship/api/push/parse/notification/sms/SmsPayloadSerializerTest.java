package com.urbanairship.api.push.parse.notification.sms;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.notification.sms.SmsPayload;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SmsPayloadSerializerTest {
    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testFullPayload() throws Exception {
        DateTime dateTime = new DateTime(2018, 3, 17, 11, 35, DateTimeZone.UTC);

        PushExpiry expiry = PushExpiry.newBuilder()
                .setExpiryTimeStamp(dateTime)
                .build();

        SmsPayload smsPayload = SmsPayload.newBuilder()
                .setAlert("Alert Text")
                .setExpiry(expiry)
                .build();

        String parsedJson =  MAPPER.writeValueAsString(smsPayload);
        String expected = "{\"alert\":\"Alert Text\",\"expiry\":\"2018-03-17T11:35:00\"}";

        JsonNode jsonNode = MAPPER.readTree(parsedJson);
        JsonNode expectedNode = MAPPER.readTree(expected);

        assertEquals(expectedNode, jsonNode);
    }
}
