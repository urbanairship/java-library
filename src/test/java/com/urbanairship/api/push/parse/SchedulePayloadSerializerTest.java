package com.urbanairship.api.push.parse;

import com.urbanairship.api.push.model.Platform;
import com.urbanairship.api.push.model.PlatformData;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.schedule.model.Schedule;
import com.urbanairship.api.schedule.model.SchedulePayload;
import org.apache.commons.lang.RandomStringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Ignore("Ignore until refactored")
public class SchedulePayloadSerializerTest {

    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testSerialization() throws Exception {

        PushPayload pushPayload = PushPayload.newBuilder()
                .setAudience(Selectors.tag(RandomStringUtils.randomAlphabetic(5)))
                .setPlatforms(PlatformData.newBuilder().addPlatform(Platform.IOS).build())
                .setNotification(Notification.newBuilder().setAlert(RandomStringUtils.randomAlphabetic(10)).build())
                .build();
        SchedulePayload schedulePayload = SchedulePayload.newBuilder()
                .setSchedule(Schedule.newBuilder().setScheduledTimestamp(new DateTime("2013-05-05T00:00:01")).build())
                .setPushPayload(pushPayload)
                .build();

        String json = MAPPER.writeValueAsString(schedulePayload);

        SchedulePayload parsed = MAPPER.readValue(json, SchedulePayload.class);

        assertEquals(schedulePayload, parsed);
    }

}
