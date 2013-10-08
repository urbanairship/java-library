package com.urbanairship.api.schedule;

import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.parse.PushObjectMapper;
import com.urbanairship.api.schedule.model.Schedule;
import com.urbanairship.api.schedule.model.SchedulePayload;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SchedulePayloadSerializerTest {

    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testSerialization() throws Exception {

        PushPayload pushPayload = PushPayload.newBuilder()
                .setAudience(Selectors.tag("tag"))
                .setDeviceTypes(DeviceTypeData.newBuilder().addDeviceType(DeviceType.IOS).build())
                .setNotification(Notification.newBuilder().setAlert("alert").build())
                .build();
        SchedulePayload schedulePayload = SchedulePayload.newBuilder()
                .setSchedule(Schedule.newBuilder()
                                     .setScheduledTimestamp(new DateTime("2013-05-05T00:00:01", DateTimeZone.UTC))
                                     .build())
                .setPushPayload(pushPayload)
                .build();

        String json = MAPPER.writeValueAsString(schedulePayload);

        String properJson = "{\"schedule\":{\"scheduled_time\":\"2013-05-05T00:00:01\"},\"push\":{\"audience\":{\"tag\":\"tag\"},\"device_types\":[\"ios\"],\"notification\":{\"alert\":\"alert\"}}}";

        assertTrue(json.equals(properJson));
    }

}
