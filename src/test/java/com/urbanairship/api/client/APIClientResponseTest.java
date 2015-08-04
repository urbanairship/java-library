package com.urbanairship.api.client;

import com.google.common.collect.ImmutableList;
import com.urbanairship.api.channel.information.model.ChannelView;
import com.urbanairship.api.client.model.APIClientResponse;
import com.urbanairship.api.client.model.APIListAllChannelsResponse;
import com.urbanairship.api.schedule.model.ListAllSchedulesResponse;
import com.urbanairship.api.client.model.APIListAllSegmentsResponse;
import com.urbanairship.api.client.model.APIListSingleChannelResponse;
import com.urbanairship.api.client.model.APIListTagsResponse;
import com.urbanairship.api.client.model.APILocationResponse;
import com.urbanairship.api.schedule.model.ScheduleResponse;
import com.urbanairship.api.push.model.PushResponse;
import com.urbanairship.api.client.model.APIReportsPushListingResponse;
import com.urbanairship.api.client.model.SegmentInformation;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.reports.model.AppStats;
import com.urbanairship.api.reports.model.PerPushDetailResponse;
import com.urbanairship.api.reports.model.PerPushSeriesResponse;
import com.urbanairship.api.reports.model.ReportsAPIOpensResponse;
import com.urbanairship.api.reports.model.ReportsAPITimeInAppResponse;
import com.urbanairship.api.reports.model.SinglePushInfoResponse;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class APIClientResponseTest {

    @Test
    public void testListPerPushSeriesAPIResponseHandlerTest() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));

        PerPushSeriesResponse obj = PerPushSeriesResponse.newBuilder()
                .setAppKey("blah")
                .build();

        APIClientResponse.Builder<PerPushSeriesResponse> builder = new APIClientResponse.Builder<PerPushSeriesResponse>()
                .setApiResponse(obj)
                .setHttpResponse(httpResponse);

        APIClientResponse<PerPushSeriesResponse> testResponse = builder.build();

        assertTrue("HTTP response not set properly",
                testResponse.getHttpResponse().equals(httpResponse));

        assertTrue("APIResponse not set properly",
                testResponse.getApiResponse().equals(obj));
    }

    @Test
    public void testListPerPushDetailAPIResponseHandlerTest() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));

        PerPushDetailResponse obj = PerPushDetailResponse.newBuilder()
                .setAppKey("blah")
                .build();

        APIClientResponse.Builder<PerPushDetailResponse> builder = new APIClientResponse.Builder<PerPushDetailResponse>()
                .setApiResponse(obj)
                .setHttpResponse(httpResponse);

        APIClientResponse<PerPushDetailResponse> testResponse = builder.build();

        assertTrue("HTTP response not set properly",
                testResponse.getHttpResponse().equals(httpResponse));

        assertTrue("APIResponse not set properly",
                testResponse.getApiResponse().equals(obj));
    }

    @Test
    public void testTimeInAppReportAPIResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));

        ReportsAPITimeInAppResponse obj = ReportsAPITimeInAppResponse.newBuilder().build();

        APIClientResponse.Builder<ReportsAPITimeInAppResponse> builder =
                new APIClientResponse.Builder<ReportsAPITimeInAppResponse>()
                        .setApiResponse(obj)
                        .setHttpResponse(httpResponse);

        APIClientResponse<ReportsAPITimeInAppResponse> testResponse = builder.build();

        assertTrue("HTTP response not set properly",
                testResponse.getHttpResponse().equals(httpResponse));

        assertTrue("APIResponse not set properly",
                testResponse.getApiResponse().equals(obj));
    }

    @Test
    public void testAppsOpenReportAPIResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));

        ReportsAPIOpensResponse obj = ReportsAPIOpensResponse.newBuilder().build();

        APIClientResponse.Builder<ReportsAPIOpensResponse> builder =
                new APIClientResponse.Builder<ReportsAPIOpensResponse>()
                        .setApiResponse(obj)
                        .setHttpResponse(httpResponse);

        APIClientResponse<ReportsAPIOpensResponse> testResponse = builder.build();

        assertTrue("HTTP response not set properly",
                testResponse.getHttpResponse().equals(httpResponse));

        assertTrue("APIResponse not set properly",
                testResponse.getApiResponse().equals(obj));
    }

    @Test
    public void testAPIReportsListingResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));

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

        APIReportsPushListingResponse obj = APIReportsPushListingResponse.newBuilder()
                .setNextPage("123")
                .addPushInfoResponse(spir)
                .addPushInfoResponse(spir)
                .addPushInfoResponse(spir)
                .build();

        APIClientResponse.Builder<APIReportsPushListingResponse> builder = new APIClientResponse.Builder<APIReportsPushListingResponse>()
                .setApiResponse(obj)
                .setHttpResponse(httpResponse);

        APIClientResponse<APIReportsPushListingResponse> testResponse = builder.build();

        assertTrue("HTTP response not set properly",
                testResponse.getHttpResponse().equals(httpResponse));

        assertTrue("APIResponse not set properly",
                testResponse.getApiResponse().equals(obj));

    }

    @Test
    public void testListIndividualPushAPIResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));

        UUID one = UUID.randomUUID();
        UUID two = UUID.randomUUID();

        SinglePushInfoResponse obj = SinglePushInfoResponse.newBuilder()
                .setPushUUID(one)
                .setDirectResponses(4)
                .setSends(5)
                .setPushType(SinglePushInfoResponse.PushType.UNICAST_PUSH)
                .setPushTime("2013-07-31 21:27:38")
                .setGroupID(two)
                .build();

        APIClientResponse.Builder<SinglePushInfoResponse> builder = new APIClientResponse.Builder<SinglePushInfoResponse>()
                .setApiResponse(obj)
                .setHttpResponse(httpResponse);

        APIClientResponse<SinglePushInfoResponse> testResponse = builder.build();

        assertTrue("HTTP response not set properly",
                testResponse.getHttpResponse().equals(httpResponse));

        assertTrue("APIResponse not set properly",
                testResponse.getApiResponse().equals(obj));
    }

    @Test
    public void testAPILocationResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        APILocationResponse locationResponse = APILocationResponse.newBuilder()
                .build();
        APIClientResponse.Builder<APILocationResponse> builder =
                new APIClientResponse.Builder<APILocationResponse>()
                        .setApiResponse(locationResponse)
                        .setHttpResponse(httpResponse);
        APIClientResponse<APILocationResponse> testResponse = builder.build();
        assertTrue("HTTP response not set properly",
                testResponse.getHttpResponse().equals(httpResponse));

        assertTrue("APIResponse not set properly",
                testResponse.getApiResponse().equals(locationResponse));
    }

    @Test
    public void testListOfAppStatsAPIResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));

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
    public void testStringAPIResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));

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
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));

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
                new APIClientResponse.Builder<APIListAllSegmentsResponse>()
                        .setApiResponse(segmentsResponse)
                        .setHttpResponse(httpResponse);

        APIClientResponse<APIListAllSegmentsResponse> testResponse = builder.build();

        assertEquals("HTTP response not set properly", httpResponse, testResponse.getHttpResponse());
        assertEquals("APIResponse not set properly", segmentsResponse, testResponse.getApiResponse());
    }

    @Test
    public void testAudienceSegmentResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));

        AudienceSegment segmentsResponse = AudienceSegment.newBuilder()
                .setDisplayName("hello")
                .setRootPredicate(TagPredicateBuilder.newInstance().setTag("tag").build())
                .build();

        APIClientResponse.Builder<AudienceSegment> builder =
                new APIClientResponse.Builder<AudienceSegment>()
                        .setApiResponse(segmentsResponse)
                        .setHttpResponse(httpResponse);

        APIClientResponse<AudienceSegment> testResponse = builder.build();

        assertEquals("HTTP response not set properly", httpResponse, testResponse.getHttpResponse());
        assertEquals("APIResponse not set properly", segmentsResponse, testResponse.getApiResponse());
    }

    @Test
    public void testAPIScheduleResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        ScheduleResponse scheduleResponse = ScheduleResponse.newBuilder()
                .setOk(true)
                .addAllScheduleUrls(Arrays.asList("ID1", "ID2"))
                .setOperationId("ID")
                .build();
        APIClientResponse.Builder<ScheduleResponse> builder =
                new APIClientResponse.Builder<ScheduleResponse>()
                        .setApiResponse(scheduleResponse)
                        .setHttpResponse(httpResponse);
        APIClientResponse<ScheduleResponse> testResponse = builder.build();
        assertTrue("HTTP response not set properly",
                testResponse.getHttpResponse().equals(httpResponse));

        assertTrue("APIResponse not set properly",
                testResponse.getApiResponse().equals(scheduleResponse));
    }

    @Test
    public void testAPIListAllSchedulesResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));


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

        APIClientResponse.Builder<ListAllSchedulesResponse> builder =
                new APIClientResponse.Builder<ListAllSchedulesResponse>()
                        .setApiResponse(listScheduleResponse)
                        .setHttpResponse(httpResponse);
        APIClientResponse<ListAllSchedulesResponse> testResponse = builder.build();
        assertTrue("HTTP response not set properly",
                testResponse.getHttpResponse().equals(httpResponse));

        assertTrue("APIResponse not set properly",
                testResponse.getApiResponse().equals(listScheduleResponse));

    }

    @Test
    public void testAPIListTagsResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));

        List<String> listOTags = new ArrayList<String>();
        listOTags.add("Puppies");
        listOTags.add("Kitties");

        APIListTagsResponse listTagsResponse = APIListTagsResponse.newBuilder()
                .allAllTags(listOTags)
                .build();
        APIClientResponse.Builder<APIListTagsResponse> builder =
                new APIClientResponse.Builder<APIListTagsResponse>()
                        .setApiResponse(listTagsResponse)
                        .setHttpResponse(httpResponse);
        APIClientResponse<APIListTagsResponse> testResponse = builder.build();
        assertTrue("HTTP response not set properly",
                testResponse.getHttpResponse().equals(httpResponse));

        assertTrue("APIResponse not set properly",
                testResponse.getApiResponse().equals(listTagsResponse));
    }

    @Test
    public void testAPIChannelViewResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));

        APIListSingleChannelResponse response =
                APIListSingleChannelResponse.newBuilder()
                        .setOk(true)
                        .setChannelObject(ChannelView.newBuilder()
                                .setAlias("Alias")
                                .setBackground(true)
                                .setChannelId("channelID")
                                .setCreatedMillis(12345L)
                                .setDeviceType(com.urbanairship.api.channel.information.model.DeviceType.ANDROID)
                                .setInstalled(true)
                                .setLastRegistrationMillis(12345L)
                                .setOptedIn(true)
                                .setPushAddress("PUSH")
                                .build())
                        .build();

        APIClientResponse.Builder<APIListSingleChannelResponse> builder =
                new APIClientResponse.Builder<APIListSingleChannelResponse>()
                        .setApiResponse(response)
                        .setHttpResponse(httpResponse);

        APIClientResponse<APIListSingleChannelResponse> testResponse = builder.build();

        assertTrue("HTTP response not set properly",
                testResponse.getHttpResponse().equals(httpResponse));

        assertTrue("APIResponse not set properly",
                testResponse.getApiResponse().equals(response));
    }

    @Test
    public void testAPIListAllChannelsResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));

        APIListAllChannelsResponse response = APIListAllChannelsResponse.newBuilder()
                .setOk(true)
                .setNextPage("nextPage")
                .addChannel(ChannelView.newBuilder()
                        .setAlias("Alias")
                        .setBackground(true)
                        .setChannelId("channelID")
                        .setCreatedMillis(12345L)
                        .setDeviceType(com.urbanairship.api.channel.information.model.DeviceType.ANDROID)
                        .setInstalled(true)
                        .setLastRegistrationMillis(12345L)
                        .setOptedIn(true)
                        .setPushAddress("PUSH")
                        .build())
                .build();

        APIClientResponse.Builder<APIListAllChannelsResponse> builder =
                new APIClientResponse.Builder<APIListAllChannelsResponse>()
                        .setApiResponse(response)
                        .setHttpResponse(httpResponse);

        APIClientResponse<APIListAllChannelsResponse> testResponse = builder.build();

        assertTrue("HTTP response not set properly",
                testResponse.getHttpResponse().equals(httpResponse));

        assertTrue("APIResponse not set properly",
                testResponse.getApiResponse().equals(response));
    }

    @Test
    public void testAPIPushResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        PushResponse pushResponse = PushResponse.newBuilder()
                .setOk(true)
                .addAllPushIds(Arrays.asList("ID1", "ID2"))
                .setOperationId("OpID")
                .build();
        APIClientResponse.Builder<PushResponse> builder =
                new APIClientResponse.Builder<PushResponse>()
                        .setApiResponse(pushResponse)
                        .setHttpResponse(httpResponse);
        APIClientResponse<PushResponse> apiResponse = builder.build();
        assertTrue("HTTP response not set properly",
                apiResponse.getHttpResponse().equals(httpResponse));
        assertTrue("APIResponse not set properly",
                apiResponse.getApiResponse().equals(pushResponse));
    }

}
