package com.urbanairship.api.schedule;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.parse.PushObjectMapper;
import com.urbanairship.api.schedule.model.BestTime;
import com.urbanairship.api.schedule.model.Schedule;
import com.urbanairship.api.schedule.model.SchedulePayload;
import org.apache.commons.lang.RandomStringUtils;
import org.joda.time.DateTime;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.List;

public class SchedulePayloadDeserializerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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

        org.junit.Assert.assertNotNull(payload);
        junit.framework.Assert.assertEquals(payload.getName(), Optional.of(name));
        junit.framework.Assert.assertEquals(payload.getSchedule().getScheduledTimestamp(), DateFormats.DATE_PARSER.parseDateTime("2013-05-05 00:00:01"));

        List<SchedulePayload> payloadList = MAPPER.readValue(json, new TypeReference<List<SchedulePayload>>() {
        });

        org.junit.Assert.assertNotNull(payloadList);
        junit.framework.Assert.assertEquals(payloadList.size(), 1);
    }

    @Test
    public void testBestTimeDeserialization() throws Exception {

        SchedulePayload payload = SchedulePayload.newBuilder()
                .setSchedule(Schedule.newBuilder()
                        .setBestTime(BestTime.newBuilder()
                                .setSendDate(DateTime.now())
                                .build())
                        .build())
                .setName("BestTimePushPayload")
                .setPushPayload(PushPayload.newBuilder()
                        .setAudience(Selectors.androidChannel("channel"))
                        .setDeviceTypes(DeviceTypeData.of(DeviceType.ANDROID))
                        .setNotification(Notification.newBuilder()
                                .setAlert("Hello Everyone")
                                .build())
                        .build())
                .build();

        String json = MAPPER.writeValueAsString(payload);

        SchedulePayload fromJson = MAPPER.readValue(json, SchedulePayload.class);

        junit.framework.Assert.assertEquals(payload, fromJson);
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

        org.junit.Assert.assertNotNull(payload);
        junit.framework.Assert.assertEquals(payload.getName(), Optional.<String>absent());
        junit.framework.Assert.assertEquals(payload.getSchedule().getScheduledTimestamp(), DateFormats.DATE_PARSER.parseDateTime("2013-05-05 00:00:01"));
    }

    @Test
    public void testInvalidScheduleObject() throws Exception {
        thrown.expect(APIParsingException.class);
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

    @Test
    public void testInvalidName() throws Exception {
        thrown.expect(APIParsingException .class);
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