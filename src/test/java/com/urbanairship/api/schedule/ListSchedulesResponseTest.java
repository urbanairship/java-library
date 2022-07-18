package com.urbanairship.api.schedule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.schedule.model.ListAllSchedulesResponse;
import com.urbanairship.api.schedule.model.Schedule;
import com.urbanairship.api.schedule.model.SchedulePayloadResponse;
import com.urbanairship.api.schedule.parse.ScheduleObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ListSchedulesResponseTest {

    @Test
    public void testAPIScheduleResponse() {

        String listScheduleResponse = "{\"ok\":true,\"count\":5,\"total_count\":6,\"schedules\":" +
                "[{\"url\":\"https://go.urbanairship.com/api/schedules/5a60e0a6-9aa7-449f-a038-6806e572baf3\",\"" +
                "schedule\":{\"scheduled_time\":\"2015-01-01T08:00:00\"},\"push\":{\"audience\":\"ALL\",\"device" +
                "_types\":[\"android\",\"ios\"],\"notification\":{\"alert\":\"Happy New Year 2015!\",\"android\"" +
                ":{},\"ios\":{}}},\"push_ids\":[\"8430f2e0-ec07-4c1e-adc4-0c7c7978e648\"]},{\"url\":\"https://go" +
                ".urbanairship.com/api/schedules/f53aa2bd-018a-4482-8d7d-691d13407973\",\"schedule\":{\"schedule" +
                "d_time\":\"2016-01-01T08:00:00\"},\"push\":{\"audience\":\"ALL\",\"device_types\":[\"android\"," +
                "\"ios\"],\"notification\":{\"alert\":\"Happy New Year 2016!\",\"android\":{},\"ios\":{}}},\"pus" +
                "h_ids\":[\"b217a321-922f-4aee-b239-ca1b58c6b652\"]}]}";

        ObjectMapper mapper = ScheduleObjectMapper.getInstance();

        try {
            ListAllSchedulesResponse response = mapper.readValue(listScheduleResponse, ListAllSchedulesResponse.class);
            assertTrue(response.getOk());
            assertEquals(5, response.getCount());
            assertEquals(6, response.getTotal_Count());
            assertEquals(response.getNext_Page(), Optional.empty());

            List<SchedulePayloadResponse> list = response.getSchedules();

            assertEquals("https://go.urbanairship.com/api/schedules/5a60e0a6-9aa7-449f-a038-6806e572baf3", list.get(0).getUrl().get());
            assertEquals("[8430f2e0-ec07-4c1e-adc4-0c7c7978e648]", list.get(0).getPushIds().toString());

            Schedule firstSchedule = list.get(0).getSchedule();
            assertEquals("2015-01-01T08:00:00.000Z", firstSchedule.getScheduledTimestamp().toString());

            PushPayload firstPush = list.get(0).getPushPayload();
            assertEquals("all", firstPush.getAudience().getType().getIdentifier());
            assertTrue(firstPush.getDeviceTypes().getDeviceTypes().get().contains(DeviceType.IOS));
            assertTrue(firstPush.getDeviceTypes().getDeviceTypes().get().contains(DeviceType.ANDROID));
            assertEquals("Happy New Year 2015!", firstPush.getNotification().get().getAlert().get());

            assertEquals("https://go.urbanairship.com/api/schedules/f53aa2bd-018a-4482-8d7d-691d13407973", list.get(1).getUrl().get());
            assertEquals("[b217a321-922f-4aee-b239-ca1b58c6b652]", list.get(1).getPushIds().toString());

            Schedule secondSchedule = list.get(1).getSchedule();
            assertEquals("2016-01-01T08:00:00.000Z", secondSchedule.getScheduledTimestamp().toString());

            PushPayload secondPush = list.get(1).getPushPayload();
            assertEquals("all", secondPush.getAudience().getType().getIdentifier());
            assertTrue(secondPush.getDeviceTypes().getDeviceTypes().get().contains(DeviceType.IOS));
            assertTrue(secondPush.getDeviceTypes().getDeviceTypes().get().contains(DeviceType.ANDROID));
            assertEquals("Happy New Year 2016!", secondPush.getNotification().get().getAlert().get());

        } catch (Exception ex) {
            fail("Exception " + ex.getMessage());
        }
    }

    @Test
    public void testAPIScheduleResponseWithNextPage() {

        String listScheduleResponse = "{\"ok\":true,\"count\":5,\"total_count\":6,\"next_page\":\"puppies\",\"schedules\":" +
                "[{\"url\":\"https://go.urbanairship.com/api/schedules/5a60e0a6-9aa7-449f-a038-6806e572baf3\",\"" +
                "schedule\":{\"scheduled_time\":\"2015-01-01T08:00:00\"},\"push\":{\"audience\":\"ALL\",\"device" +
                "_types\":[\"android\",\"ios\"],\"notification\":{\"alert\":\"Happy New Year 2015!\",\"android\"" +
                ":{},\"ios\":{}}},\"push_ids\":[\"8430f2e0-ec07-4c1e-adc4-0c7c7978e648\"]},{\"url\":\"https://go" +
                ".urbanairship.com/api/schedules/f53aa2bd-018a-4482-8d7d-691d13407973\",\"schedule\":{\"schedule" +
                "d_time\":\"2016-01-01T08:00:00\"},\"push\":{\"audience\":\"ALL\",\"device_types\":[\"android\"," +
                "\"ios\"],\"notification\":{\"alert\":\"Happy New Year 2016!\",\"android\":{},\"ios\":{}}},\"pus" +
                "h_ids\":[\"b217a321-922f-4aee-b239-ca1b58c6b652\"]}]}";

        ObjectMapper mapper = ScheduleObjectMapper.getInstance();

        try {
            ListAllSchedulesResponse response = mapper.readValue(listScheduleResponse, ListAllSchedulesResponse.class);
            assertTrue(response.getOk());
            assertEquals(5, response.getCount());
            assertEquals(6, response.getTotal_Count());
            assertEquals("puppies", response.getNext_Page().get());

            List<SchedulePayloadResponse> list = response.getSchedules();

            assertEquals("https://go.urbanairship.com/api/schedules/5a60e0a6-9aa7-449f-a038-6806e572baf3", list.get(0).getUrl().get());
            assertEquals("[8430f2e0-ec07-4c1e-adc4-0c7c7978e648]", list.get(0).getPushIds().toString());

            Schedule firstSchedule = list.get(0).getSchedule();
            assertEquals("2015-01-01T08:00:00.000Z", firstSchedule.getScheduledTimestamp().toString());

            PushPayload firstPush = list.get(0).getPushPayload();
            assertEquals("all", firstPush.getAudience().getType().getIdentifier());
            assertTrue(firstPush.getDeviceTypes().getDeviceTypes().get().contains(DeviceType.IOS));
            assertTrue(firstPush.getDeviceTypes().getDeviceTypes().get().contains(DeviceType.ANDROID));
            assertEquals("Happy New Year 2015!", firstPush.getNotification().get().getAlert().get());

            assertEquals("https://go.urbanairship.com/api/schedules/f53aa2bd-018a-4482-8d7d-691d13407973", list.get(1).getUrl().get());
            assertEquals("[b217a321-922f-4aee-b239-ca1b58c6b652]", list.get(1).getPushIds().toString());

            Schedule secondSchedule = list.get(1).getSchedule();
            assertEquals("2016-01-01T08:00:00.000Z", secondSchedule.getScheduledTimestamp().toString());

            PushPayload secondPush = list.get(1).getPushPayload();
            assertEquals("all", secondPush.getAudience().getType().getIdentifier());
            assertTrue(secondPush.getDeviceTypes().getDeviceTypes().get().contains(DeviceType.IOS));
            assertTrue(secondPush.getDeviceTypes().getDeviceTypes().get().contains(DeviceType.ANDROID));
            assertEquals("Happy New Year 2016!", secondPush.getNotification().get().getAlert().get());
        } catch (Exception ex) {
            fail("Exception " + ex.getMessage());
        }
    }

    @Test
    public void testErrorAPIScheduleResponse() throws IOException {
        String jsonResponse = "{\n" +
                "    \"ok\": false,\n" +
                "    \"error\": \"error\",\n" +
                "    \"details\": {\n" +
                "        \"error\": \"error\"\n" +
                "    }\n" +
                "}";

        ObjectMapper mapper = ScheduleObjectMapper.getInstance();
        ListAllSchedulesResponse response = mapper.readValue(jsonResponse, ListAllSchedulesResponse.class);
        assertEquals("error", response.getError().get());
        assertEquals("error", response.getErrorDetails().get().getError().get());
        assertFalse(response.getOk());
    }

}
