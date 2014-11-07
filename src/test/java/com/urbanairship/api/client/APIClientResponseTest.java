package com.urbanairship.api.client;

import com.urbanairship.api.channel.registration.model.ChannelView;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.client.model.*;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.model.notification.Notifications;
import com.urbanairship.api.reports.model.AppStats;
import com.urbanairship.api.schedule.model.Schedule;
import com.urbanairship.api.schedule.model.SchedulePayload;
import com.urbanairship.api.segments.model.AudienceSegment;
import com.urbanairship.api.segments.model.TagPredicateBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class APIClientResponseTest {

    @Test
    public void testListOfAppStatsAPIResponse(){
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP",1,1), 200, "OK"));

        AppStats one = AppStats.newBuilder()
                .setStartTime(new DateTime(2015, 1, 1, 0, 0, 0, 0))
                .build();

        AppStats two = AppStats.newBuilder()
                .setStartTime(new DateTime(2016, 1, 1, 0, 0, 0, 0))
                .build();

        List<AppStats> list = new ArrayList<AppStats>();

        list.add(one);
        list.add(two);

        APIClientResponse.Builder<List<AppStats>> builder =
                APIClientResponse.newListAppStatsBuilder()
                        .setApiResponse(list)
                        .setHttpResponse(httpResponse);

        APIClientResponse<List<AppStats>> testResponse = builder.build();

        assertTrue("HTTP response not set properly",
                testResponse.getHttpResponse().equals(httpResponse));

        assertTrue("APIResponse not set properly",
                testResponse.getApiResponse().equals(list));
    }

    @Test
    public void testStringAPIResponse(){
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP",1,1), 200, "OK"));

        APIClientResponse.Builder<String> builder =
                APIClientResponse.newStringResponseBuilder()
                        .setApiResponse("StringLaLaLa")
                        .setHttpResponse(httpResponse);

        APIClientResponse<String> testResponse = builder.build();

        assertTrue("HTTP response not set properly",
                testResponse.getHttpResponse().equals(httpResponse));

        assertTrue("APIResponse not set properly",
                testResponse.getApiResponse().equals("StringLaLaLa"));
    }

    @Test
    public void testAPIListAllSegmentsResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP",1,1), 200, "OK"));

        httpResponse.setHeader("Link", "NextPage");

        SegmentInformation si = SegmentInformation.newBuilder()
                .setCreationDate(123L)
                .setDisplayName("DisplayName")
                .setId("Id")
                .setModificationDate(321L)
                .build();

        ImmutableList<SegmentInformation> listsi = ImmutableList.<SegmentInformation>builder()
                .add(si)
                .build();

        APIListAllSegmentsResponse segmentsResponse = APIListAllSegmentsResponse.newBuilder()
                .setNextPage("NextPage")
                .setSegments(listsi)
                .build();

        APIClientResponse.Builder<APIListAllSegmentsResponse> builder =
                APIClientResponse.newListAllSegmentsResponseBuilder()
                        .setApiResponse(segmentsResponse)
                        .setHttpResponse(httpResponse);

        APIClientResponse<APIListAllSegmentsResponse> testResponse = builder.build();

        assertEquals("HTTP response not set properly", httpResponse, testResponse.getHttpResponse());
        assertEquals("APIResponse not set properly", segmentsResponse, testResponse.getApiResponse());
    }

    @Test
    public void testAudienceSegmentResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP",1,1), 200, "OK"));

        AudienceSegment segmentsResponse = AudienceSegment.newBuilder()
                .setDisplayName("hello")
                .setRootPredicate(TagPredicateBuilder.newInstance().setTag("tag").build())
                .build();

        APIClientResponse.Builder<AudienceSegment> builder =
                APIClientResponse.newAudienceSegmentResponseBuilder()
                        .setApiResponse(segmentsResponse)
                        .setHttpResponse(httpResponse);

        APIClientResponse<AudienceSegment> testResponse = builder.build();

        assertEquals("HTTP response not set properly", httpResponse, testResponse.getHttpResponse());
        assertEquals("APIResponse not set properly", segmentsResponse, testResponse.getApiResponse());
    }

    @Test
    public void testAPIScheduleResponse(){
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP",1,1), 200, "OK"));
        APIScheduleResponse scheduleResponse = APIScheduleResponse.newBuilder()
                .addAllScheduleUrls(Arrays.asList("ID1", "ID2"))
                .setOperationId("ID")
                .build();
        APIClientResponse.Builder<APIScheduleResponse> builder =
                APIClientResponse.newScheduleResponseBuilder()
                                 .setApiResponse(scheduleResponse)
                                 .setHttpResponse(httpResponse);
        APIClientResponse<APIScheduleResponse> testResponse = builder.build();
        assertTrue("HTTP response not set properly",
                   testResponse.getHttpResponse().equals(httpResponse));

        assertTrue("APIResponse not set properly",
                   testResponse.getApiResponse().equals(scheduleResponse));
    }

    @Test
    public void testAPIListAllSchedulesResponse(){
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP",1,1), 200, "OK"));


        SchedulePayload sample = SchedulePayload.newBuilder()
                                        .setSchedule(Schedule.newBuilder()
                                                .setScheduledTimestamp(DateTime.now())
                                                .build())
                                        .setPushPayload(PushPayload.newBuilder()
                                                .setAudience(Selectors.all())
                                                .setNotification(Notification.newBuilder()
                                                        .setAlert("Derp")
                                                        .build())
                                                .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS))
                                                .build())
                                        .setUrl("http://sample.com/")
                                        .build();

        APIListAllSchedulesResponse listScheduleResponse = APIListAllSchedulesResponse.newBuilder()
                .setCount(5)
                .setTotalCount(6)
                .addSchedule(SchedulePayload.newBuilder()
                        .setSchedule(Schedule.newBuilder()
                                .setScheduledTimestamp(DateTime.now())
                                .build())
                        .setPushPayload(PushPayload.newBuilder()
                                                   .setAudience(Selectors.deviceToken("token"))
                                                   .setNotification(Notifications.notification("UA Push"))
                                                   .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS))
                                                   .build())
                        .build())
                .build();

        APIClientResponse.Builder<APIListAllSchedulesResponse> builder =
                APIClientResponse.newListAllSchedulesResponseBuilder()
                        .setApiResponse(listScheduleResponse)
                        .setHttpResponse(httpResponse);
        APIClientResponse<APIListAllSchedulesResponse> testResponse = builder.build();
        assertTrue("HTTP response not set properly",
                testResponse.getHttpResponse().equals(httpResponse));

        assertTrue("APIResponse not set properly",
                testResponse.getApiResponse().equals(listScheduleResponse));

    }

    @Test
    public void testAPIListTagsResponse(){
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP",1,1), 200, "OK"));

        List<String> listOTags = new ArrayList<String>();
        listOTags.add("Puppies");
        listOTags.add("Kitties");

        APIListTagsResponse listTagsResponse = APIListTagsResponse.newBuilder()
                .allAllTags(listOTags)
                .build();
        APIClientResponse.Builder<APIListTagsResponse> builder =
                APIClientResponse.newListTagsResponseBuilder()
                        .setApiResponse(listTagsResponse)
                        .setHttpResponse(httpResponse);
        APIClientResponse<APIListTagsResponse> testResponse = builder.build();
        assertTrue("HTTP response not set properly",
                testResponse.getHttpResponse().equals(httpResponse));

        assertTrue("APIResponse not set properly",
                testResponse.getApiResponse().equals(listTagsResponse));
    }

    @Test
    public void testAPIListAllChannelsResponse(){
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP",1,1), 200, "OK"));

        APIListAllChannelsResponse response = APIListAllChannelsResponse.newBuilder()
                .setNextPage("nextPage")
                .addChannel(ChannelView.newBuilder()
                        .setAlias("Alias")
                        .setBackground(true)
                        .setChannelId("channelID")
                        .setCreatedMillis(12345L)
                        .setDeviceType(com.urbanairship.api.channel.registration.model.DeviceType.ANDROID)
                        .setInstalled(true)
                        .setLastRegistrationMillis(12345L)
                        .setOptedIn(true)
                        .setPushAddress("PUSH")
                        .build())
                .build();

        APIClientResponse.Builder<APIListAllChannelsResponse> builder =
                APIClientResponse.newListAllChannelsResponseBuilder()
                        .setApiResponse(response)
                        .setHttpResponse(httpResponse);

        APIClientResponse<APIListAllChannelsResponse> testResponse = builder.build();

        assertTrue("HTTP response not set properly",
                testResponse.getHttpResponse().equals(httpResponse));

        assertTrue("APIResponse not set properly",
                testResponse.getApiResponse().equals(response));
    }

    @Test
    public void testAPIPushResponse(){
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP",1,1), 200, "OK"));
        APIPushResponse pushResponse = APIPushResponse.newBuilder()
                .addAllPushIds(Arrays.asList("ID1", "ID2"))
                .setOperationId("OpID")
                .build();
        APIClientResponse.Builder<APIPushResponse>builder =
                APIClientResponse.newPushResponseBuilder()
                .setApiResponse(pushResponse)
                .setHttpResponse(httpResponse);
        APIClientResponse<APIPushResponse> apiResponse = builder.build();
        assertTrue("HTTP response not set properly",
                   apiResponse.getHttpResponse().equals(httpResponse));
        assertTrue("APIResponse not set properly",
                   apiResponse.getApiResponse().equals(pushResponse));
    }

}
