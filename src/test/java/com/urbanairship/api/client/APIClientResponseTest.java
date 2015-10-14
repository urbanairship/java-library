package com.urbanairship.api.client;

import com.google.common.collect.ImmutableList;
import com.urbanairship.api.client.model.APIClientResponse;
import com.urbanairship.api.client.model.APIListAllSegmentsResponse;
import com.urbanairship.api.client.model.APIListTagsResponse;
import com.urbanairship.api.client.model.APILocationResponse;
import com.urbanairship.api.client.model.SegmentInformation;
import com.urbanairship.api.reports.model.AppStats;
import com.urbanairship.api.reports.model.PerPushDetailResponse;
import com.urbanairship.api.reports.model.PerPushSeriesResponse;
import com.urbanairship.api.reports.model.ReportsAPIOpensResponse;
import com.urbanairship.api.reports.model.ReportsAPITimeInAppResponse;
import com.urbanairship.api.segments.model.AudienceSegment;
import com.urbanairship.api.segments.model.TagPredicateBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

}
