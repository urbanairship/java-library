package com.urbanairship.api.client.parse;


import com.urbanairship.api.client.APIScheduleResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class APIScheduleResponseTest {

    @Test
    public void testAPIScheduleResponse(){
        String scheduleJSON = "{\n" +
                "    \"ok\" : true,\n" +
                "    \"operation_id\" : \"ID\",\n" +
                "    \"schedule_urls\" : [\n" +
                "        \"SCHEDULE\"\n" +
                "     ]\n" +
                "}";

        ObjectMapper mapper = APIResponseObjectMapper.getInstance();
        try{
            APIScheduleResponse response = mapper.readValue(scheduleJSON, APIScheduleResponse.class);
            assertTrue(response.getOperationId().equals("ID"));
            assertTrue(response.getScheduleUrls().get(0).equals("SCHEDULE"));
        }
        catch (Exception ex){
            fail("Exception " + ex.getMessage());
        }
    }
}
