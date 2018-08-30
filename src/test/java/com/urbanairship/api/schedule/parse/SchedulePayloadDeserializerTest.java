package com.urbanairship.api.schedule.parse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.parse.PushObjectMapper;
import com.urbanairship.api.schedule.model.BestTime;
import com.urbanairship.api.schedule.model.Schedule;
import com.urbanairship.api.schedule.model.SchedulePayload;
import com.urbanairship.api.schedule.parse.ScheduleObjectMapper;
import org.apache.commons.lang.RandomStringUtils;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SchedulePayloadDeserializerTest {

    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testDeserialization() throws Exception {
        String name = RandomStringUtils.randomAlphabetic(5);

        String json =
                "{" +
                        "\"schedule\": {" +
                        "\"scheduled_time\": \"2013-05-05 00:00:01\"" +
                        "}," +
                        "\"name\": " +
                        "\"" + name + "\"" +
                        "," +
                        "\"push\": {" +
                        "\"audience\" : \"all\"," +
                        "\"device_types\" : [ \"ios\" ]," +
                        "\"notification\" : { \"alert\" : \"derp\" }" +
                        "}" +
                        "}";

        SchedulePayload payload = MAPPER.readValue(json, SchedulePayload.class);

        assertNotNull(payload);
        assertEquals(payload.getName(), Optional.of(name));
        assertEquals(payload.getSchedule().getScheduledTimestamp(), DateFormats.DATE_PARSER.parseDateTime("2013-05-05 00:00:01"));

        List<SchedulePayload> payloadList = MAPPER.readValue(json, new TypeReference<List<SchedulePayload>>() {
        });

        assertNotNull(payloadList);
        assertEquals(payloadList.size(), 1);

    }

    @Test
    public void testBestTimeDeserialization() throws Exception {

        BestTime bestTime = BestTime.newBuilder()
                .setSendDate(DateTime.now())
                .build();

        Schedule schedule = Schedule.newBuilder()
                .setBestTime(bestTime)
                .build();

        SchedulePayload payload = SchedulePayload.newBuilder()
                .setSchedule(schedule)
                .setName("BestTimePushPayload")
                .setPushPayload(PushPayload.newBuilder()
                        .setAudience(Selectors.all())
                        .setDeviceTypes(DeviceTypeData.all())
                        .setNotification(Notification.newBuilder()
                                .setAlert("Hello Nerds")
                                .build())
                        .build())
                .build();

        String json = MAPPER.writeValueAsString(payload);

        SchedulePayload fromJson = MAPPER.readValue(json, SchedulePayload.class);

        assertEquals(payload, fromJson);
    }




    @Test
    public void testDeserializationWithoutName() throws Exception {
        String json =
                "{" +
                        "\"schedule\": {" +
                        "\"scheduled_time\": \"2013-05-05 00:00:01\"" +
                        "}," +
                        "\"push\": {" +
                        "\"audience\" : \"all\"," +
                        "\"device_types\" : [ \"ios\" ]," +
                        "\"notification\" : { \"alert\" : \"derp\" }" +
                        "}" +
                        "}";

        SchedulePayload payload = MAPPER.readValue(json, SchedulePayload.class);

        assertNotNull(payload);
        assertEquals(payload.getName(), Optional.<String>absent());
        assertEquals(payload.getSchedule().getScheduledTimestamp(), DateFormats.DATE_PARSER.parseDateTime("2013-05-05 00:00:01"));
    }

    @Test(expected = APIParsingException.class)
    public void testInvalidScheduleObject() throws Exception {
        String json =
                "{" +
                        "\"schedule\": {" +
                        " \"2013-05-05 00:00:01\"" +
                        "}," +
                        "\"push\": {" +
                        "\"audience\" : \"all\"," +
                        "\"device_types\" : [ \"ios\" ]," +
                        "\"notification\" : { \"alert\" : \"derp\" }" +
                        "}" +
                        "}";

        MAPPER.readValue(json, SchedulePayload.class);

    }

    @Test(expected = APIParsingException.class)
    public void testInvalidName() throws Exception {

        String json =
                "{" +
                        "\"schedule\": {" +
                        "\"scheduled_time\": \"2013-05-05 00:00:01\"" +
                        "}," +
                        "\"name\": " +
                        "\"" + " " + "\"" +
                        "," +
                        "\"push\": {" +
                        "\"audience\" : \"all\"," +
                        "\"device_types\" : [ \"ios\" ]," +
                        "\"notification\" : { \"alert\" : \"derp\" }" +
                        "}" +
                        "}";

        MAPPER.readValue(json, SchedulePayload.class);

    }
}