package com.urbanairship.api.schedule;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.model.notification.Notifications;
import com.urbanairship.api.schedule.model.BestTime;
import com.urbanairship.api.schedule.model.Schedule;
import com.urbanairship.api.schedule.model.SchedulePayloadResponse;
import com.urbanairship.api.schedule.model.ScheduleResponse;
import com.urbanairship.api.schedule.parse.ScheduleObjectMapper;
import org.apache.commons.lang.RandomStringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

public class SchedulePayloadResponseDeserializerTest {

    private static final ObjectMapper MAPPER = ScheduleObjectMapper.getInstance();

    @Test
    public void testDeserialization() throws Exception {
        String name = RandomStringUtils.randomAlphabetic(5);

        String json =
                "{" +
                        "\"schedule\": {" +
                        "\"scheduled_time\": \"2013-05-05T00:00:01\"" +
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

        SchedulePayloadResponse payload = MAPPER.readValue(json, SchedulePayloadResponse.class);

        org.junit.Assert.assertNotNull(payload);
        Assert.assertEquals(payload.getName(), Optional.of(name));
        Assert.assertEquals(payload.getSchedule().getScheduledTimestamp(), DateFormats.DATE_PARSER.parseDateTime("2013-05-05 00:00:01"));

        List<SchedulePayloadResponse> payloadList = MAPPER.readValue(json, new TypeReference<List<SchedulePayloadResponse>>() {
        });

        org.junit.Assert.assertNotNull(payloadList);
        Assert.assertEquals(payloadList.size(), 1);
    }

    @Test
    public void testBestTimeDeserialization() throws Exception {
        DateTime dateTime = DateTime.now();
        String json = "{\n" +
                "          \"url\": \"https://go.urbanairship.com/api/schedules/6c7c9bf5-cb2b-47cb-b27f-f85981391c4e\",\n" +
                "          \"schedule\": {\n" +
                "            \"best_time\": {\n" +
                "              \"send_date\": \"" + dateTime + "\"\n" +
                "            }\n" +
                "          },\n" +
                "          \"name\": \"BestTimePushPayload\",\n" +
                "          \"push\": {\n" +
                "            \"audience\": { \"tag\": \"normalPeople\" },\n" +
                "            \"notification\": { \"alert\": \"Hello Everyone\" },\n" +
                "            \"device_types\": [ \"ios\", \"android\" ]\n" +
                "         },\n" +
                "         \"push_ids\": [ \"8438e81-bb31-82a9-5feb-e7fd5b21ca7e\" ]\n" +
                "      }";

        SchedulePayloadResponse expected = SchedulePayloadResponse.newBuilder()
                .setSchedule(Schedule.newBuilder()
                        .setBestTime(BestTime.newBuilder()
                                .setSendDate(dateTime)
                                .build())
                        .build())
                .setName("BestTimePushPayload")
                .setPushPayload(PushPayload.newBuilder()
                        .setAudience(Selectors.tag("normalPeople"))
                        .setDeviceTypes(DeviceTypeData.of(DeviceType.ANDROID, DeviceType.IOS))
                        .setNotification(Notification.newBuilder()
                                .setAlert("Hello Everyone")
                                .build())
                        .build())
                .addPushId("8438e81-bb31-82a9-5feb-e7fd5b21ca7e")
                .setUrl("https://go.urbanairship.com/api/schedules/6c7c9bf5-cb2b-47cb-b27f-f85981391c4e")
                .build();

        SchedulePayloadResponse fromJson = MAPPER.readValue(json, SchedulePayloadResponse.class);

        Assert.assertEquals(expected, fromJson);
    }

    @Test
    public void testScheduleResponseDeserialization() throws Exception {

        DateTime dateTime = DateTime.now(DateTimeZone.UTC);
        BestTime bestTime = BestTime.newBuilder()
                .setSendDate(dateTime)
                .build();

        SchedulePayloadResponse schedulePayloadResponse = SchedulePayloadResponse.newBuilder()
                .setName("Morning People")
                .setSchedule(Schedule.newBuilder()
                        .setScheduledTimestamp(dateTime)
                        .build()
                )
                .setPushPayload(PushPayload.newBuilder()
                        .setNotification(Notifications.alert("Good Day Sunshine"))
                        .setAudience(Selectors.tag("earlyBirds"))
                        .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS, DeviceType.ANDROID))
                        .build())
                .setUrl("https://go.urbanairship.com/api/schedules/eac2ace6-349a-41a2-b874-5496d7bf0100")
                .addPushId("83046227-9b06-4114-9f23-0df349792bbd")
                .build();

        SchedulePayloadResponse schedulePayloadResponse2 = SchedulePayloadResponse.newBuilder()
                .setUrl("https://go.urbanairship.com/api/schedules/6c7c9bf5-cb2b-47cb-b27f-f85981391c4e")
                .setSchedule(Schedule.newBuilder()
                        .setBestTime(bestTime)
                        .build())
                .setName("Everybody Else")
                .setPushPayload(PushPayload.newBuilder()
                        .setAudience(Selectors.tag("normalPeople"))
                        .setNotification(Notifications.alert("Stay Up Late"))
                        .setDeviceTypes(DeviceTypeData.of(DeviceType.ANDROID, DeviceType.IOS))
                        .build())
                .addPushId("8438e81-bb31-82a9-5feb-e7fd5b21ca7e")
                .build();

        ScheduleResponse expectedResponse = ScheduleResponse.newBuilder()
                .setOk(true)
                .setOperationId("efb18e92-9a60-6689-45c2-82fedab36399")
                .addScheduleUrl("https://go.urbanairship.com/api/schedules/eac2ace6-349a-41a2-b874-5496d7bf0100")
                .addScheduleUrl("https://go.urbanairship.com/api/schedules/6c7c9bf5-cb2b-47cb-b27f-f85981391c4e")
                .addScheduleId("eac2ace6-349a-41a2-b874-5496d7bf0100")
                .addScheduleId("6c7c9bf5-cb2b-47cb-b27f-f85981391c4e")
                .addSchedulePayload(schedulePayloadResponse)
                .addSchedulePayload(schedulePayloadResponse2)
                .build();

        String responseJson = "{\n" +
                "   \"ok\": true,\n" +
                "   \"operation_id\": \"efb18e92-9a60-6689-45c2-82fedab36399\",\n" +
                "   \"schedule_urls\": [\n" +
                "        \"https://go.urbanairship.com/api/schedules/eac2ace6-349a-41a2-b874-5496d7bf0100\",\n" +
                "        \"https://go.urbanairship.com/api/schedules/6c7c9bf5-cb2b-47cb-b27f-f85981391c4e\"\n" +
                "    ],\n" +
                "    \"schedule_ids\": [\n" +
                "        \"eac2ace6-349a-41a2-b874-5496d7bf0100\",\n" +
                "        \"6c7c9bf5-cb2b-47cb-b27f-f85981391c4e\"\n" +
                "    ],\n" +
                "   \"schedules\": [\n" +
                "      {\n" +
                "         \"url\": \"https://go.urbanairship.com/api/schedules/eac2ace6-349a-41a2-b874-5496d7bf0100\",\n" +
                "         \"schedule\": {\n" +
                "            \"scheduled_time\": \"" + dateTime + "\"\n" +
                "         },\n" +
                "         \"name\": \"Morning People\",\n" +
                "         \"push\": {\n" +
                "            \"audience\": { \"tag\": \"earlyBirds\" },\n" +
                "            \"notification\": { \"alert\": \"Good Day Sunshine\" },\n" +
                "            \"device_types\": [ \"ios\", \"android\" ]\n" +
                "         },\n" +
                "         \"push_ids\": [ \"83046227-9b06-4114-9f23-0df349792bbd\" ]\n" +
                "      },\n" +
                "      {\n" +
                "          \"url\": \"https://go.urbanairship.com/api/schedules/6c7c9bf5-cb2b-47cb-b27f-f85981391c4e\",\n" +
                "          \"schedule\": {\n" +
                "            \"best_time\": {\n" +
                "              \"send_date\": \"2018-06-03\"\n" +
                "            }\n" +
                "          },\n" +
                "          \"name\": \"Everybody Else\",\n" +
                "          \"push\": {\n" +
                "            \"audience\": { \"tag\": \"normalPeople\" },\n" +
                "            \"notification\": { \"alert\": \"Stay Up Late\" },\n" +
                "            \"device_types\": [ \"ios\", \"android\" ]\n" +
                "         },\n" +
                "         \"push_ids\": [ \"8438e81-bb31-82a9-5feb-e7fd5b21ca7e\" ]\n" +
                "      }\n" +
                "   ]\n" +
                "}";


        ScheduleResponse scheduleResponse = MAPPER.readValue(responseJson, ScheduleResponse.class);

        Assert.assertEquals(expectedResponse, scheduleResponse);
    }

    @Test
    public void testDeserializationWithoutName() throws Exception {
        String json =
                "{" +
                        "\"schedule\": {" +
                        "\"scheduled_time\": \"2013-05-05T00:00:01\"" +
                        "}," +
                        "\"push\": {" +
                        "\"audience\" : \"all\"," +
                        "\"device_types\" : [ \"ios\" ]," +
                        "\"notification\" : { \"alert\" : \"derp\" }" +
                        "}" +
                        "}";

        SchedulePayloadResponse payload = MAPPER.readValue(json, SchedulePayloadResponse.class);

        org.junit.Assert.assertNotNull(payload);
        Assert.assertEquals(payload.getName(), Optional.<String>empty());
        Assert.assertEquals(payload.getSchedule().getScheduledTimestamp(), DateFormats.DATE_PARSER.parseDateTime("2013-05-05 00:00:01"));
    }

    @Test
    public void testInvalidScheduleObject() {
        Assert.assertThrows(APIParsingException.class, () -> {
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

                MAPPER.readValue(json, SchedulePayloadResponse.class);
            });

    }

    @Test
    public void testInvalidName() {
        Assert.assertThrows(APIParsingException.class, () -> {
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

                MAPPER.readValue(json, SchedulePayloadResponse.class);
            });

    }
}