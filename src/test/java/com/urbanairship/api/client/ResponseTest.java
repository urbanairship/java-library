package com.urbanairship.api.client;

import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.PushResponse;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.schedule.model.ListAllSchedulesResponse;
import com.urbanairship.api.schedule.model.Schedule;
import com.urbanairship.api.schedule.model.SchedulePayload;
import com.urbanairship.api.schedule.model.ScheduleResponse;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class ResponseTest {

    private final String CONTENT_TYPE_KEY = "content-type";
    private final String CONTENT_TYPE = "application/json";

    @Test
    public void testPushResponse() {
        PushResponse pushResponse = PushResponse.newBuilder()
            .setOk(true)
            .addAllPushIds(Arrays.asList("ID1", "ID2"))
            .setOperationId("OpID")
            .build();

        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
            new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        httpResponse.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE);

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(httpResponse.getAllHeaders()[0].getName(), httpResponse.getAllHeaders()[0].getValue());

        Response<PushResponse> response = new Response<PushResponse>(pushResponse, headers, httpResponse.getStatusLine().getStatusCode());
        assertTrue("HTTP response body not set properly",
            response.getBody().get().equals(pushResponse));
        assertTrue("HTTP response headers not set properly",
            response.getHeaders().equals(headers));
        assertTrue("HTTP response status not set properly",
            response.getStatus() == httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testAPIScheduleResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
            new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        httpResponse.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE);

        ScheduleResponse scheduleResponse = ScheduleResponse.newBuilder()
            .setOk(true)
            .addAllScheduleUrls(Arrays.asList("ID1", "ID2"))
            .setOperationId("ID")
            .build();

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(httpResponse.getAllHeaders()[0].getName(), httpResponse.getAllHeaders()[0].getValue());

        Response<ScheduleResponse> response = new Response<ScheduleResponse>(scheduleResponse, headers, httpResponse.getStatusLine().getStatusCode());
        assertTrue("HTTP response body not set properly",
            response.getBody().get().equals(scheduleResponse));
        assertTrue("HTTP response headers not set properly",
            response.getHeaders().equals(headers));
        assertTrue("HTTP response status not set properly",
            response.getStatus() == httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testAPIListAllSchedulesResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
            new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        httpResponse.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE);

        SchedulePayload sample = SchedulePayload.newBuilder()
            .setSchedule(Schedule.newBuilder()
                .setScheduledTimestamp(DateTime.now())
                .build())
            .setPushPayload(PushPayload.newBuilder()
                .setAudience(Selectors.all())
                .setNotification(Notification.newBuilder()
                    .setAlert("UA Push")
                    .build())
                .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS))
                .build())
            .setUrl("http://sample.com/")
            .build();

        ListAllSchedulesResponse listScheduleResponse = ListAllSchedulesResponse.newBuilder()
            .setOk(true)
            .setCount(5)
            .setTotalCount(6)
            .addSchedule(sample)
            .build();

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(httpResponse.getAllHeaders()[0].getName(), httpResponse.getAllHeaders()[0].getValue());

        Response<ListAllSchedulesResponse> response = new Response<ListAllSchedulesResponse>(listScheduleResponse, headers, httpResponse.getStatusLine().getStatusCode());
        assertTrue("HTTP response body not set properly",
            response.getBody().get().equals(listScheduleResponse));
        assertTrue("HTTP response headers not set properly",
            response.getHeaders().equals(headers));
        assertTrue("HTTP response status not set properly",
            response.getStatus() == httpResponse.getStatusLine().getStatusCode());
    }

}
