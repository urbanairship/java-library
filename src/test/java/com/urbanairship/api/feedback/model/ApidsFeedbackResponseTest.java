/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.feedback.model;

import com.urbanairship.api.feedback.parse.FeedbackObjectMapper;

import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;
import org.codehaus.jackson.type.TypeReference;


import java.util.List;

import static com.urbanairship.api.common.parse.DateFormats.DATETIME_FORMAT_PATTERN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ApidsFeedbackResponseTest
{
    @Test
    public void testAPIApidsFeedbackResponse() {
        String twoResponse =
                "["+
                "{" +
                "\"apid\": \"00000000-0000-0000-0000-000000000000\"," +
                "\"gcm_registration_id\": \"abcdefghijklmn\", "+
                "\"marked_inactive_on\": \"2009-06-22 10:05:00\"," +
                "\"alias\": \"bob\"" +
                "}," +
                "{" +
                "\"apid\": \"00000000-0000-0000-0000-000000000001\"," +
                "\"gcm_registration_id\": \"opqrstuvmxyz\", "+
                "\"marked_inactive_on\": \"2009-06-22 10:07:00\"," +
                "\"alias\": \"Alice\"" +
                "}" +
                "]";
        ObjectMapper mapper = FeedbackObjectMapper.getInstance();
        try{
            List<ApidsFeedbackResponse> apidsFeedbackResponses = mapper.readValue(twoResponse, new TypeReference<List<ApidsFeedbackResponse>>(){});
            assertEquals(2, apidsFeedbackResponses.size());

            assertEquals("00000000-0000-0000-0000-000000000000", apidsFeedbackResponses.get(0).getApid());
            assertEquals("abcdefghijklmn", apidsFeedbackResponses.get(0).getGcmRegistrationId());
            assertEquals("2009-06-22 10:05:00", DateTimeFormat.forPattern(DATETIME_FORMAT_PATTERN).print(apidsFeedbackResponses.get(0).getMarkedInactiveOn()));
            assertEquals("bob", apidsFeedbackResponses.get(0).getAlias());

            assertEquals("00000000-0000-0000-0000-000000000001", apidsFeedbackResponses.get(1).getApid());
            assertEquals("opqrstuvmxyz", apidsFeedbackResponses.get(1).getGcmRegistrationId());
            assertEquals("2009-06-22 10:07:00", DateTimeFormat.forPattern(DATETIME_FORMAT_PATTERN).print(apidsFeedbackResponses.get(1).getMarkedInactiveOn()));
            assertEquals("Alice", apidsFeedbackResponses.get(1).getAlias());
        } catch (Exception e){
            e.printStackTrace();
            fail("Exception " + e.getMessage());
        }
    }
}
