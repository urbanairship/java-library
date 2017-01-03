/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.feedback.model;

import com.urbanairship.api.feedback.parse.FeedbackObjectMapper;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

import java.util.List;

import static com.urbanairship.api.common.parse.DateFormats.DATETIME_FORMAT_PATTERN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DeviceTokensFeedbackResponseTest {
    @Test
    public void testAPIDeviceTokensFeedbackResponse() {
        String twoResponse =
                "[{" +
                        "\"device_token\": \"1234123412341234123412341234123412341234123412341234123412341234\"," +
                        "\"marked_inactive_on\": \"2009-06-22 10:05:00\"," +
                        "\"alias\": \"bob\"" +
                        "}," +
                        "{" +
                        "\"device_token\": \"ABCDABCDABCDABCDABCDABCDABCDABCDABCDABCDABCDABCDABCDABCDABCDABCD\"," +
                        "\"marked_inactive_on\": \"2009-06-22 10:07:00\"," +
                        "\"alias\": \"Alice\"" +
                "}]";
        ObjectMapper mapper = FeedbackObjectMapper.getInstance();
        try{
            List<DeviceTokensFeedbackResponse> deviceTokesFeedbackResponses = mapper.readValue(twoResponse, new TypeReference<List<DeviceTokensFeedbackResponse>>(){});
            assertEquals(2, deviceTokesFeedbackResponses.size());

            assertEquals("1234123412341234123412341234123412341234123412341234123412341234", deviceTokesFeedbackResponses.get(0).getDeviceToken());
            assertEquals("2009-06-22 10:05:00", DateTimeFormat.forPattern(DATETIME_FORMAT_PATTERN).print(deviceTokesFeedbackResponses.get(0).getMarkedInactiveOn()));
            assertEquals("bob", deviceTokesFeedbackResponses.get(0).getAlias());

            assertEquals("ABCDABCDABCDABCDABCDABCDABCDABCDABCDABCDABCDABCDABCDABCDABCDABCD", deviceTokesFeedbackResponses.get(1).getDeviceToken());
            assertEquals("2009-06-22 10:07:00", DateTimeFormat.forPattern(DATETIME_FORMAT_PATTERN).print(deviceTokesFeedbackResponses.get(1).getMarkedInactiveOn()));
            assertEquals("Alice", deviceTokesFeedbackResponses.get(1).getAlias());
        } catch (Exception e){
            e.printStackTrace();
            fail("Exception " + e.getMessage());
        }
    }
}
