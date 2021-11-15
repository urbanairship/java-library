package com.urbanairship.api.schedule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.push.model.DeviceType;
import com.google.common.base.Optional;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.schedule.model.ListAllSchedulesResponse;
import com.urbanairship.api.schedule.model.Schedule;
import com.urbanairship.api.schedule.model.SchedulePayloadResponse;
import com.urbanairship.api.schedule.parse.ScheduleObjectMapper;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class ListSchedulesResponseTest {

    @Test
    public void testAPIScheduleResponse() {

        String listscheduleresponse = "{\"ok\":true,\"count\":5,\"total_count\":6,\"schedules\":" +
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
            ListAllSchedulesResponse response = mapper.readValue(listscheduleresponse, ListAllSchedulesResponse.class);
            assertTrue(response.getOk());
            assertTrue(response.getCount() == 5);
            assertTrue(response.getTotal_Count() == 6);
            assertEquals(response.getNext_Page(), Optional.absent());

            List<SchedulePayloadResponse> list = response.getSchedules();

            assertTrue(list.get(0).getUrl().get().equals("https://go.urbanairship.com/api/schedules/5a60e0a6-9aa7-449f-a038-6806e572baf3"));
            assertTrue(list.get(0).getPushIds().toString().equals("[8430f2e0-ec07-4c1e-adc4-0c7c7978e648]"));

            Schedule firstschedule = list.get(0).getSchedule();
            assertTrue(firstschedule.getScheduledTimestamp().toString().equals("2015-01-01T08:00:00.000Z"));

            PushPayload firstpush = list.get(0).getPushPayload();
            assertTrue(firstpush.getAudience().getType().getIdentifier().equals("all"));
            assertTrue(firstpush.getDeviceTypes().getDeviceTypes().get().contains(DeviceType.IOS));
            assertTrue(firstpush.getDeviceTypes().getDeviceTypes().get().contains(DeviceType.ANDROID));
            assertTrue(firstpush.getNotification().get().getAlert().get().equals("Happy New Year 2015!"));

            assertTrue(list.get(1).getUrl().get().equals("https://go.urbanairship.com/api/schedules/f53aa2bd-018a-4482-8d7d-691d13407973"));
            assertTrue(list.get(1).getPushIds().toString().equals("[b217a321-922f-4aee-b239-ca1b58c6b652]"));

            Schedule secondschedule = list.get(1).getSchedule();
            assertTrue(secondschedule.getScheduledTimestamp().toString().equals("2016-01-01T08:00:00.000Z"));

            PushPayload secondpush = list.get(1).getPushPayload();
            assertTrue(secondpush.getAudience().getType().getIdentifier().equals("all"));
            assertTrue(secondpush.getDeviceTypes().getDeviceTypes().get().contains(DeviceType.IOS));
            assertTrue(secondpush.getDeviceTypes().getDeviceTypes().get().contains(DeviceType.ANDROID));
            assertTrue(secondpush.getNotification().get().getAlert().get().equals("Happy New Year 2016!"));

        } catch (Exception ex) {
            fail("Exception " + ex.getMessage());
        }
    }

    @Test
    public void testAPIScheduleResponseWithNextPage() {

        String listscheduleresponse = "{\"ok\":true,\"count\":5,\"total_count\":6,\"next_page\":\"puppies\",\"schedules\":" +
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
            ListAllSchedulesResponse response = mapper.readValue(listscheduleresponse, ListAllSchedulesResponse.class);
            assertTrue(response.getOk());
            assertTrue(response.getCount() == 5);
            assertTrue(response.getTotal_Count() == 6);
            assertEquals(response.getNext_Page(), Optional.of("puppies"));

            List<SchedulePayloadResponse> list = response.getSchedules();

            assertTrue(list.get(0).getUrl().get().equals("https://go.urbanairship.com/api/schedules/5a60e0a6-9aa7-449f-a038-6806e572baf3"));
            assertTrue(list.get(0).getPushIds().toString().equals("[8430f2e0-ec07-4c1e-adc4-0c7c7978e648]"));

            Schedule firstschedule = list.get(0).getSchedule();
            assertTrue(firstschedule.getScheduledTimestamp().toString().equals("2015-01-01T08:00:00.000Z"));

            PushPayload firstpush = list.get(0).getPushPayload();
            assertTrue(firstpush.getAudience().getType().getIdentifier().equals("all"));
            assertTrue(firstpush.getDeviceTypes().getDeviceTypes().get().contains(DeviceType.IOS));
            assertTrue(firstpush.getDeviceTypes().getDeviceTypes().get().contains(DeviceType.ANDROID));
            assertTrue(firstpush.getNotification().get().getAlert().get().equals("Happy New Year 2015!"));

            assertTrue(list.get(1).getUrl().get().equals("https://go.urbanairship.com/api/schedules/f53aa2bd-018a-4482-8d7d-691d13407973"));
            assertTrue(list.get(1).getPushIds().toString().equals("[b217a321-922f-4aee-b239-ca1b58c6b652]"));

            Schedule secondschedule = list.get(1).getSchedule();
            assertTrue(secondschedule.getScheduledTimestamp().toString().equals("2016-01-01T08:00:00.000Z"));

            PushPayload secondpush = list.get(1).getPushPayload();
            assertTrue(secondpush.getAudience().getType().getIdentifier().equals("all"));
            assertTrue(secondpush.getDeviceTypes().getDeviceTypes().get().contains(DeviceType.IOS));
            assertTrue(secondpush.getDeviceTypes().getDeviceTypes().get().contains(DeviceType.ANDROID));
            assertTrue(secondpush.getNotification().get().getAlert().get().equals("Happy New Year 2016!"));
        } catch (Exception ex) {
            fail("Exception " + ex.getMessage());
        }
    }

}
