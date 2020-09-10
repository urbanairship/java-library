package com.urbanairship.api.schedule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.schedule.model.ScheduleResponse;
import com.urbanairship.api.schedule.parse.ScheduleObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ScheduleResponseTest {

    @Test
    public void testAPIScheduleResponse() {
        String scheduleJSON = "{\"ok\":true,\"operation_id\":\"47ecebe0-27c4-11e4-ad5c-001b21c78f20\",\"schedule_urls\":[\"https://go.urbanairship.com/api/schedules/4f636bb9-e278-4af8-8fe4-873809acbd87\"],\"schedule_ids\":[\"4f636bb9-e278-4af8-8fe4-873809acbd87\"],\"schedules\":[{\"url\":\"https://go.urbanairship.com/api/schedules/4f636bb9-e278-4af8-8fe4-873809acbd87\",\"schedule\":{\"scheduled_time\":\"2014-08-19T17:15:27\"},\"name\":\"Urban Airship Scheduled Push\",\"push\":{\"audience\":\"ALL\",\"device_types\":[\"ios\"],\"notification\":{\"alert\":\"Scheduled API v3\"}},\"push_ids\":[\"70d84384-4c0a-4917-8e05-4443cf4e9575\"]}]}";

        ObjectMapper mapper = ScheduleObjectMapper.getInstance();
        try {
            ScheduleResponse response = mapper.readValue(scheduleJSON, ScheduleResponse.class);
            assertTrue(response.getOk());
            assertTrue(response.getOperationId().equals("47ecebe0-27c4-11e4-ad5c-001b21c78f20"));
            assertTrue(response.getScheduleUrls().get(0).equals("https://go.urbanairship.com/api/schedules/4f636bb9-e278-4af8-8fe4-873809acbd87"));
            assertTrue(response.getScheduleIds().get(0).equals("4f636bb9-e278-4af8-8fe4-873809acbd87"));
            assertTrue(response.getSchedulePayloadResponses().get(0).getUrl().get().equals("https://go.urbanairship.com/api/schedules/4f636bb9-e278-4af8-8fe4-873809acbd87"));
            assertTrue(response.getSchedulePayloadResponses().get(0).getPushPayload().getNotification().get().getAlert().get().equals("Scheduled API v3"));
        } catch (Exception ex) {
            fail("Exception " + ex.getMessage());
        }
    }

    @Test
    public void testUpdateAPIScheduleResponse() {
        String scheduleJSON = "{\"ok\":true,\"operation_id\":\"47ecebe0-27c4-11e4-ad5c-001b21c78f20\" }";

        ObjectMapper mapper = ScheduleObjectMapper.getInstance();
        try {
            ScheduleResponse response = mapper.readValue(scheduleJSON, ScheduleResponse.class);
            assertTrue(response.getOperationId().equals("47ecebe0-27c4-11e4-ad5c-001b21c78f20"));
        } catch (Exception ex) {
            fail("Exception " + ex.getMessage());
        }
    }
}
