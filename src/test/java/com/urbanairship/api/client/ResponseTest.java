package com.urbanairship.api.client;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import com.urbanairship.api.channel.model.ChannelResponse;
import com.urbanairship.api.channel.model.ChannelType;
import com.urbanairship.api.channel.model.ChannelView;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.PushResponse;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.reports.model.PushListingResponse;
import com.urbanairship.api.reports.model.SinglePushInfoResponse;
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
import java.util.UUID;

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

    @Test
    public void testAPIChannelViewResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
            new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        httpResponse.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE);

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(httpResponse.getAllHeaders()[0].getName(), httpResponse.getAllHeaders()[0].getValue());

        ChannelResponse channelResponse =
            ChannelResponse.newBuilder()
                .setOk(true)
                .setChannelObject(ChannelView.newBuilder()
                    .setAlias("Alias")
                    .setBackground(true)
                    .setChannelId("channelID")
                    .setCreated(DateTime.now())
                    .setChannelType(ChannelType.ANDROID)
                    .setInstalled(true)
                    .setLastRegistration(DateTime.now().minus(12345L))
                    .setOptIn(true)
                    .setPushAddress("PUSH")
                    .build())
                .build();

        Response<ChannelResponse> response = new Response<ChannelResponse>(channelResponse, headers, httpResponse.getStatusLine().getStatusCode());
        assertTrue("HTTP response body not set properly",
            response.getBody().get().equals(channelResponse));
        assertTrue("HTTP response headers not set properly",
            response.getHeaders().equals(headers));
        assertTrue("HTTP response status not set properly",
            response.getStatus() == httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testAPIListChannelsResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
            new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        httpResponse.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE);

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(httpResponse.getAllHeaders()[0].getName(), httpResponse.getAllHeaders()[0].getValue());

        ChannelResponse channelResponse = ChannelResponse.newBuilder()
            .setOk(true)
            .setNextPage("nextPage")
            .addChannel(ChannelView.newBuilder()
                .setAlias("Alias")
                .setBackground(true)
                .setChannelId("channelID")
                .setCreated(DateTime.now())
                .setChannelType(ChannelType.ANDROID)
                .setInstalled(true)
                .setLastRegistration(DateTime.now().minus(12345L))
                .setOptIn(true)
                .setPushAddress("PUSH")
                .build())
            .build();

        Response<ChannelResponse> response = new Response<ChannelResponse>(channelResponse, headers, httpResponse.getStatusLine().getStatusCode());
        assertTrue("HTTP response body not set properly",
            response.getBody().get().equals(channelResponse));
        assertTrue("HTTP response headers not set properly",
            response.getHeaders().equals(headers));
        assertTrue("HTTP response status not set properly",
            response.getStatus() == httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testAPIReportsListingResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        httpResponse.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE);

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(httpResponse.getAllHeaders()[0].getName(), httpResponse.getAllHeaders()[0].getValue());


        UUID one = UUID.randomUUID();
        UUID two = UUID.randomUUID();

        SinglePushInfoResponse spir = SinglePushInfoResponse.newBuilder()
                .setPushUUID(one)
                .setDirectResponses(4)
                .setSends(5)
                .setPushType(SinglePushInfoResponse.PushType.UNICAST_PUSH)
                .setPushTime("2013-07-31 21:27:38")
                .setGroupID(two)
                .build();

        PushListingResponse pushListingResponse = PushListingResponse.newBuilder()
                .setNextPage("123")
                .addPushInfoObject(spir)
                .addPushInfoObject(spir)
                .addPushInfoObject(spir)
                .build();

        Response<PushListingResponse> response = new Response<PushListingResponse>(
                pushListingResponse,
                headers,
                httpResponse.getStatusLine().getStatusCode());

        assertTrue("HTTP response not set properly",
                response.getBody().get().equals(pushListingResponse));
        assertTrue("HTTP response headers not set properly",
                response.getHeaders().equals(headers));
        assertTrue("HTTP response status not set properly",
                response.getStatus() == httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testListIndividualPushAPIResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        httpResponse.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE);

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(httpResponse.getAllHeaders()[0].getName(), httpResponse.getAllHeaders()[0].getValue());


        UUID one = UUID.randomUUID();
        UUID two = UUID.randomUUID();

        SinglePushInfoResponse pushInfoResponse = SinglePushInfoResponse.newBuilder()
                .setPushUUID(one)
                .setDirectResponses(4)
                .setSends(5)
                .setPushType(SinglePushInfoResponse.PushType.UNICAST_PUSH)
                .setPushTime("2013-07-31 21:27:38")
                .setGroupID(two)
                .build();

        Response<SinglePushInfoResponse> response = new Response<SinglePushInfoResponse>(
                pushInfoResponse,
                headers,
                httpResponse.getStatusLine().getStatusCode());

        assertTrue("HTTP response not set properly",
                response.getBody().get().equals(pushInfoResponse));
        assertTrue("HTTP response headers not set properly",
                response.getHeaders().equals(headers));
        assertTrue("HTTP response status not set properly",
                response.getStatus() == httpResponse.getStatusLine().getStatusCode());

    }
}
