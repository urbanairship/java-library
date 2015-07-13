
package com.urbanairship.api.feedback.parse;

import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import com.urbanairship.api.feedback.model.APIApidsFeedbackResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.codehaus.jackson.type.TypeReference;


import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class APIApidsFeedbackResponseTest
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
        ObjectMapper mapper = APIResponseObjectMapper.getInstance();
        try{
            List<APIApidsFeedbackResponse> apidsFeedbackResponses = mapper.readValue(twoResponse, new TypeReference<List<APIApidsFeedbackResponse>>(){});
            assertEquals(2, apidsFeedbackResponses.size());

            assertEquals("00000000-0000-0000-0000-000000000000", apidsFeedbackResponses.get(0).getApid());
            assertEquals("abcdefghijklmn", apidsFeedbackResponses.get(0).getGcmRegistrationId());
            assertEquals("2009-06-22 10:05:00", apidsFeedbackResponses.get(0).getMarkedInactiveOn());
            assertEquals("bob", apidsFeedbackResponses.get(0).getAlias());

            assertEquals("00000000-0000-0000-0000-000000000001", apidsFeedbackResponses.get(1).getApid());
            assertEquals("opqrstuvmxyz", apidsFeedbackResponses.get(1).getGcmRegistrationId());
            assertEquals("2009-06-22 10:07:00", apidsFeedbackResponses.get(1).getMarkedInactiveOn());
            assertEquals("Alice", apidsFeedbackResponses.get(1).getAlias());
        } catch (Exception e){
            e.printStackTrace();
            fail("Exception " + e.getMessage());
        }
    }
}
