
package com.urbanairship.api.feedback.parse;

import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import com.urbanairship.api.feedback.model.APIDeviceTokensFeedbackResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class APIDeviceTokensFeedbackResponseTest
{
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
        ObjectMapper mapper = APIResponseObjectMapper.getInstance();
        try{
            List<APIDeviceTokensFeedbackResponse> apidsFeedbackResponses = mapper.readValue(twoResponse, new TypeReference<List<APIDeviceTokensFeedbackResponse>>(){});
            assertEquals(2, apidsFeedbackResponses.size());

            assertEquals("1234123412341234123412341234123412341234123412341234123412341234", apidsFeedbackResponses.get(0).getDeviceToken());
            assertEquals("2009-06-22 10:05:00", apidsFeedbackResponses.get(0).getMarkedInactiveOn());
            assertEquals("bob", apidsFeedbackResponses.get(0).getAlias());

            assertEquals("ABCDABCDABCDABCDABCDABCDABCDABCDABCDABCDABCDABCDABCDABCDABCDABCD", apidsFeedbackResponses.get(1).getDeviceToken());
            assertEquals("2009-06-22 10:07:00", apidsFeedbackResponses.get(1).getMarkedInactiveOn());
            assertEquals("Alice", apidsFeedbackResponses.get(1).getAlias());
        } catch (Exception e){
            e.printStackTrace();
            fail("Exception " + e.getMessage());
        }
    }
}
