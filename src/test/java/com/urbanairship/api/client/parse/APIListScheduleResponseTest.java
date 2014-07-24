package com.urbanairship.api.client.parse;

import com.urbanairship.api.client.APIListScheduleResponse;
import com.urbanairship.api.schedule.model.ScheduleResponseObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class APIListScheduleResponseTest {

    @Test
    public void testAPIScheduleResponse(){

        String listscheduleresponse = "{\"ok\":true,\"count\":5,\"total_count\":6,\"schedules\":" +
                "[{\"url\":\"https://go.urbanairship.com/api/schedules/5a60e0a6-9aa7-449f-a038-6806e572baf3\",\"" +
                "schedule\":{\"scheduled_time\":\"2015-01-01T08:00:00\"},\"push\":{\"audience\":\"ALL\",\"device" +
                "_types\":[\"android\",\"ios\"],\"notification\":{\"alert\":\"Happy New Year 2015!\",\"android\"" +
                ":{},\"ios\":{}}},\"push_ids\":[\"8430f2e0-ec07-4c1e-adc4-0c7c7978e648\"]},{\"url\":\"https://go" +
                ".urbanairship.com/api/schedules/f53aa2bd-018a-4482-8d7d-691d13407973\",\"schedule\":{\"schedule" +
                "d_time\":\"2016-01-01T08:00:00\"},\"push\":{\"audience\":\"ALL\",\"device_types\":[\"android\"," +
                "\"ios\"],\"notification\":{\"alert\":\"Happy New Year 2016!\",\"android\":{},\"ios\":{}}},\"pus" +
                "h_ids\":[\"b217a321-922f-4aee-b239-ca1b58c6b652\"]}]}";

        ObjectMapper mapper = APIResponseObjectMapper.getInstance();

        try{
            APIListScheduleResponse response = mapper.readValue(listscheduleresponse, APIListScheduleResponse.class);
            assertTrue(response.getCount() == 5);
            assertTrue(response.getTotal_Count() == 6);
            assertTrue(response.getNext_Page() == null);

            List<ScheduleResponseObject> list = response.getSchedules();

            assertTrue(list.get(0).getUrl().equals("https://go.urbanairship.com/api/schedules/5a60e0a6-9aa7-449f-a038-6806e572baf3"));
            assertTrue(list.get(0).getPushIds().get(0).equals("8430f2e0-ec07-4c1e-adc4-0c7c7978e648"));

            Map<String,Object> firstschedule = list.get(0).getSchedule();
            assertTrue(firstschedule.get("scheduled_time").equals("2015-01-01T08:00:00"));

            Map<String,Object> firstpush = list.get(0).getPushPayload();
            assertTrue(firstpush.get("audience").equals("ALL"));
            assertTrue(firstpush.get("device_types").toString().equals("[android, ios]"));
            assertTrue(firstpush.get("notification").toString().equals("{alert=Happy New Year 2015!, android={}, ios={}}"));


            assertTrue(list.get(1).getUrl().equals("https://go.urbanairship.com/api/schedules/f53aa2bd-018a-4482-8d7d-691d13407973"));
            assertTrue(list.get(1).getPushIds().get(0).equals("b217a321-922f-4aee-b239-ca1b58c6b652"));
            Map<String, Object> secondschedule = list.get(1).getSchedule();
            assertTrue(secondschedule.get("scheduled_time").equals("2016-01-01T08:00:00"));

            Map<String,Object> secondpush = list.get(1).getPushPayload();
            assertTrue(secondpush.get("audience").equals("ALL"));
            assertTrue(secondpush.get("device_types").toString().equals("[android, ios]"));
            assertTrue(secondpush.get("notification").toString().equals("{alert=Happy New Year 2016!, android={}, ios={}}"));

        }
        catch (Exception ex){
            fail("Exception " + ex.getMessage());
        }
    }

    @Test
    public void testAPIScheduleResponseWithNextPage(){

        String listscheduleresponse = "{\"ok\":true,\"count\":5,\"total_count\":6,\"next_page\":\"puppies\",\"schedules\":" +
                "[{\"url\":\"https://go.urbanairship.com/api/schedules/5a60e0a6-9aa7-449f-a038-6806e572baf3\",\"" +
                "schedule\":{\"scheduled_time\":\"2015-01-01T08:00:00\"},\"push\":{\"audience\":\"ALL\",\"device" +
                "_types\":[\"android\",\"ios\"],\"notification\":{\"alert\":\"Happy New Year 2015!\",\"android\"" +
                ":{},\"ios\":{}}},\"push_ids\":[\"8430f2e0-ec07-4c1e-adc4-0c7c7978e648\"]},{\"url\":\"https://go" +
                ".urbanairship.com/api/schedules/f53aa2bd-018a-4482-8d7d-691d13407973\",\"schedule\":{\"schedule" +
                "d_time\":\"2016-01-01T08:00:00\"},\"push\":{\"audience\":\"ALL\",\"device_types\":[\"android\"," +
                "\"ios\"],\"notification\":{\"alert\":\"Happy New Year 2016!\",\"android\":{},\"ios\":{}}},\"pus" +
                "h_ids\":[\"b217a321-922f-4aee-b239-ca1b58c6b652\"]}]}";

        ObjectMapper mapper = APIResponseObjectMapper.getInstance();

        try{
            APIListScheduleResponse response = mapper.readValue(listscheduleresponse, APIListScheduleResponse.class);
            assertTrue(response.getCount() == 5);
            assertTrue(response.getTotal_Count() == 6);
            assertTrue(response.getNext_Page().equals("puppies"));

            List<ScheduleResponseObject> list = response.getSchedules();

            assertTrue(list.get(0).getUrl().equals("https://go.urbanairship.com/api/schedules/5a60e0a6-9aa7-449f-a038-6806e572baf3"));
            assertTrue(list.get(0).getPushIds().get(0).equals("8430f2e0-ec07-4c1e-adc4-0c7c7978e648"));

            Map<String,Object> firstschedule = list.get(0).getSchedule();
            assertTrue(firstschedule.get("scheduled_time").equals("2015-01-01T08:00:00"));

            Map<String,Object> firstpush = list.get(0).getPushPayload();
            assertTrue(firstpush.get("audience").equals("ALL"));
            assertTrue(firstpush.get("device_types").toString().equals("[android, ios]"));
            assertTrue(firstpush.get("notification").toString().equals("{alert=Happy New Year 2015!, android={}, ios={}}"));


            assertTrue(list.get(1).getUrl().equals("https://go.urbanairship.com/api/schedules/f53aa2bd-018a-4482-8d7d-691d13407973"));
            assertTrue(list.get(1).getPushIds().get(0).equals("b217a321-922f-4aee-b239-ca1b58c6b652"));
            Map<String, Object> secondschedule = list.get(1).getSchedule();
            assertTrue(secondschedule.get("scheduled_time").equals("2016-01-01T08:00:00"));

            Map<String,Object> secondpush = list.get(1).getPushPayload();
            assertTrue(secondpush.get("audience").equals("ALL"));
            assertTrue(secondpush.get("device_types").toString().equals("[android, ios]"));
            assertTrue(secondpush.get("notification").toString().equals("{alert=Happy New Year 2016!, android={}, ios={}}"));

        }
        catch (Exception ex){
            fail("Exception " + ex.getMessage());
        }
    }

}
