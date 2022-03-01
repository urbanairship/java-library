package com.urbanairship.api.schedule;

import com.fasterxml.jackson.databind.JsonNode;
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
import com.urbanairship.api.schedule.model.SchedulePayloadResponse;
import com.urbanairship.api.schedule.parse.ScheduleObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

public class SchedulePayloadResponseSerializerTest {

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

        org.junit.Assert.assertEquals(json, properJson);
    }

    @Test
    public void testBestTimeSerialization() throws Exception {
        BestTime bestTime = BestTime.newBuilder()
                .setSendDate(new DateTime("2013-05-05T00:00:01", DateTimeZone.UTC))
                .build();

        PushPayload pushPayload = PushPayload.newBuilder()
                .setAudience(Selectors.tag("tag"))
                .setDeviceTypes(DeviceTypeData.newBuilder().addDeviceType(DeviceType.IOS).build())
                .setNotification(Notification.newBuilder().setAlert("alert").build())
                .setPushOptions(PushOptions.newBuilder().build())
                .build();
        SchedulePayload schedulePayload = SchedulePayload.newBuilder()
                .setSchedule(Schedule.newBuilder()
                        .setBestTime(bestTime)
                        .build())
                .setPushPayload(pushPayload)
                .build();

        String json = MAPPER.writeValueAsString(schedulePayload);

        String properJson = "{\"schedule\":{\"best_time\":{\"send_date\":\"2013-05-05\"}},\"push\":{\"audience\":{\"tag\":\"tag\"},\"device_types\":[\"ios\"],\"notification\":{\"alert\":\"alert\"},\"options\":{}}}";
        org.junit.Assert.assertEquals(json, properJson);
    }

    @Test(expected = Exception.class)
    public void testNoSchedule() throws Exception {

        PushPayload pushPayload = PushPayload.newBuilder()
                .setAudience(Selectors.tag("tag"))
                .setDeviceTypes(DeviceTypeData.newBuilder().addDeviceType(DeviceType.IOS).build())
                .setNotification(Notification.newBuilder().setAlert("alert").build())
                .setPushOptions(PushOptions.newBuilder().build())
                .build();
        SchedulePayloadResponse schedulePayload = SchedulePayloadResponse.newBuilder()
                .setSchedule(Schedule.newBuilder()
                        .build())
                .setPushPayload(pushPayload)
                .build();
        String json = MAPPER.writeValueAsString(schedulePayload);
        String properJson = "{\"schedule\":{},\"push\":{\"audience\":{\"tag\":\"tag\"},\"device_types\":[\"ios\"],\"notification\":{\"alert\":\"alert\"},\"options\":{\"present\":true}}}";

        org.junit.Assert.assertEquals(json, properJson);

    }

    @Test
    public void testLocalTimeFlag() throws Exception {

        PushPayload pushPayload = PushPayload.newBuilder()
                .setAudience(Selectors.tag("tag"))
                .setDeviceTypes(DeviceTypeData.newBuilder().addDeviceType(DeviceType.IOS).build())
                .setNotification(Notification.newBuilder().setAlert("alert").build())
                .setPushOptions(PushOptions.newBuilder().build())
                .build();
        SchedulePayloadResponse schedulePayload = SchedulePayloadResponse.newBuilder()
                .setSchedule(Schedule.newBuilder()
                        .setScheduledTimestamp(new DateTime("2013-05-05T00:00:01", DateTimeZone.UTC))
                        .build())
                .setPushPayload(pushPayload)
                .build();

        org.junit.Assert.assertFalse(schedulePayload.getSchedule().getLocalTimePresent());

        PushPayload pushPayloadLocal = PushPayload.newBuilder()
                .setAudience(Selectors.tag("tag"))
                .setDeviceTypes(DeviceTypeData.newBuilder().addDeviceType(DeviceType.IOS).build())
                .setNotification(Notification.newBuilder().setAlert("alert").build())
                .setPushOptions(PushOptions.newBuilder().build())
                .build();

        Schedule schedule = Schedule.newBuilder()
                .setLocalScheduledTimestamp(new DateTime("2013-05-05T00:00:01", DateTimeZone.UTC))
                .build();

        SchedulePayloadResponse schedulePayloadLocal = SchedulePayloadResponse.newBuilder()
                .setSchedule(schedule)
                .setPushPayload(pushPayloadLocal)
                .build();

        String expectedScheduled = "{\"local_scheduled_time\":\"2013-05-05T00:00:01\"}";
        String scheduleString = MAPPER.writeValueAsString(schedulePayloadLocal.getSchedule());

        JsonNode jsonNode = MAPPER.readTree(scheduleString);
        JsonNode jsonNodeExpected = MAPPER.readTree(expectedScheduled);

        org.junit.Assert.assertTrue(schedulePayloadLocal.getSchedule().getLocalTimePresent());
        org.junit.Assert.assertEquals(jsonNodeExpected, jsonNode);
    }

}
