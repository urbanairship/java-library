package com.urbanairship.api.schedule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.PushOptions;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.schedule.model.BestTime;
import com.urbanairship.api.schedule.model.Schedule;
import com.urbanairship.api.schedule.model.SchedulePayload;
import com.urbanairship.api.schedule.parse.ScheduleObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScheduleSerializerTest {

    private static final ObjectMapper MAPPER = ScheduleObjectMapper.getInstance();

    @Test
    public void testSerialization() throws Exception {

        PushPayload pushPayload = PushPayload.newBuilder()
                .setAudience(Selectors.tag("tag"))
                .setDeviceTypes(DeviceTypeData.newBuilder().addDeviceType(DeviceType.IOS).build())
                .setNotification(Notification.newBuilder().setAlert("alert").build())
                .setPushOptions(PushOptions.newBuilder().build())
                .build();
        SchedulePayload schedulePayload = SchedulePayload.newBuilder()
                .setSchedule(Schedule.newBuilder()
                        .setScheduledTimestamp(new DateTime("2013-05-05T00:00:01", DateTimeZone.UTC))
                        .build())
                .setPushPayload(pushPayload)
                .build();

        String json = MAPPER.writeValueAsString(schedulePayload);

        String properJson = "{\"schedule\":{\"scheduled_time\":\"2013-05-05T00:00:01\"},\"push\":{\"audience\":{\"tag\":\"tag\"},\"device_types\":[\"ios\"],\"notification\":{\"alert\":\"alert\"},\"options\":{}}}";

        assertEquals(json, properJson);

        BestTime bestTime = BestTime.newBuilder()
                .setSendDate(DateTime.now())
                .build();

        Schedule schedule = Schedule.newBuilder()
                .setBestTime(bestTime)
                .build();

        String scheduledBestTimeJson = MAPPER.writeValueAsString(schedule);

        System.out.println(scheduledBestTimeJson);

    }

}
