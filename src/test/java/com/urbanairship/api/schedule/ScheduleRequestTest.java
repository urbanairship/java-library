package com.urbanairship.api.schedule;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notifications;
import com.urbanairship.api.schedule.model.Schedule;
import com.urbanairship.api.schedule.model.SchedulePayload;
import com.urbanairship.api.schedule.model.ScheduleResponse;
import com.urbanairship.api.schedule.parse.ScheduleObjectMapper;
import org.apache.http.entity.ContentType;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ScheduleRequestTest {

    PushPayload pushPayload = PushPayload.newBuilder()
        .setAudience(Selectors.all())
        .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS))
        .setNotification(Notifications.alert("Foo"))
        .build();

    DateTime dateTime = DateTime.now(DateTimeZone.UTC).plusSeconds(60);
    Schedule schedule = Schedule.newBuilder()
        .setScheduledTimestamp(dateTime)
        .build();

    SchedulePayload schedulePayload = SchedulePayload.newBuilder()
        .setPushPayload(pushPayload)
        .setSchedule(schedule)
        .build();

    ScheduleRequest scheduleRequest = ScheduleRequest.newRequest(schedule, pushPayload);
    ScheduleRequest updateScheduleRequest = ScheduleRequest.newUpdateRequest(schedule, pushPayload, "id");

    @Test
    public void testContentType() throws Exception {
        assertEquals(scheduleRequest.getContentType(), ContentType.APPLICATION_JSON);
        assertEquals(updateScheduleRequest.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(scheduleRequest.getHttpMethod(), Request.HttpMethod.POST);
        assertEquals(updateScheduleRequest.getHttpMethod(), Request.HttpMethod.PUT);
    }

    @Test
    public void testBody() throws Exception {
        assertEquals(scheduleRequest.getRequestBody(), schedulePayload.toJSON());
        assertEquals(updateScheduleRequest.getRequestBody(), schedulePayload.toJSON());
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(scheduleRequest.getRequestHeaders(), headers);
        assertEquals(updateScheduleRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expextedURI = URI.create("https://go.urbanairship.com/api/schedules/");
        assertEquals(scheduleRequest.getUri(baseURI), expextedURI);

        expextedURI = URI.create("https://go.urbanairship.com/api/schedules/id");
        assertEquals(updateScheduleRequest.getUri(baseURI), expextedURI);
    }

    @Test
    public void testScheduleParser() throws Exception {
        ResponseParser responseParser = new ResponseParser<ScheduleResponse>() {
            @Override
            public ScheduleResponse parse(String response) throws IOException {
                return ScheduleObjectMapper.getInstance().readValue(response, ScheduleResponse.class);
            }
        };

        String response = "{\"ok\" : true, \"operation_id\" : \"OpID\", " +
            "\"schedule_urls\" : [\"ScheduleURL\"], " +
            "\"schedule_ids\" : [\"ScheduleID\"], " +
            "\"schedules\" : [\n" +
            "      {\n" +
            "         \"url\" : \"http://go.urbanairship/api/schedules/2d69320c-3c91-5241-fac4-248269eed109\",\n" +
            "         \"schedule\" : { \"scheduled_time\": \"2013-04-01T18:45:00\" },\n" +
            "         \"push\" : { \"audience\":{ \"tag\": \"spoaaaarts\" },\n" +
            "            \"notification\": { \"alert\": \"Booyah!\" },\n" +
            "            \"device_types\": \"all\" },\n" +
            "         \"push_ids\" : [ \"8f18fcb5-e2aa-4b61-b190-43852eadb5ef\" ]\n" +
            "      }\n" +
            "   ]}";
        assertEquals(scheduleRequest.getResponseParser().parse(response), responseParser.parse(response));
        assertEquals(updateScheduleRequest.getResponseParser().parse(response), responseParser.parse(response));
    }
}
