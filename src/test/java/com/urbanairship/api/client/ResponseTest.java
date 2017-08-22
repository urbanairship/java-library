package com.urbanairship.api.client;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;
import com.urbanairship.api.channel.model.ChannelResponse;
import com.urbanairship.api.channel.model.ChannelType;
import com.urbanairship.api.channel.model.ChannelView;
import com.urbanairship.api.location.model.LocationResponse;
import com.urbanairship.api.location.model.LocationView;
import com.urbanairship.api.nameduser.model.NamedUserListingResponse;
import com.urbanairship.api.nameduser.model.NamedUserView;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.PushResponse;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.reports.model.PushInfoResponse;
import com.urbanairship.api.reports.model.PushListingResponse;
import com.urbanairship.api.schedule.model.ListAllSchedulesResponse;
import com.urbanairship.api.schedule.model.Schedule;
import com.urbanairship.api.schedule.model.SchedulePayload;
import com.urbanairship.api.schedule.model.ScheduleResponse;
import com.urbanairship.api.segments.model.SegmentListingResponse;
import com.urbanairship.api.segments.model.SegmentListingView;
import com.urbanairship.api.segments.model.SegmentView;
import com.urbanairship.api.staticlists.model.StaticListListingResponse;
import com.urbanairship.api.staticlists.model.StaticListView;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Test;

import java.util.Arrays;
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

        ListMultimap<String, String> headers = ArrayListMultimap.create();
        headers.put(httpResponse.getAllHeaders()[0].getName(), httpResponse.getAllHeaders()[0].getValue());

        Response<PushResponse> response = new Response<PushResponse>(pushResponse, headers.asMap(), httpResponse.getStatusLine().getStatusCode());
        assertTrue("HTTP response body not set properly",
                response.getBody().get().equals(pushResponse));
        assertTrue("HTTP response headers not set properly",
                response.getHeaders().equals(headers.asMap()));
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

        ListMultimap<String, String> headers = ArrayListMultimap.create();
        headers.put(httpResponse.getAllHeaders()[0].getName(), httpResponse.getAllHeaders()[0].getValue());

        Response<ScheduleResponse> response = new Response<ScheduleResponse>(scheduleResponse, headers.asMap(), httpResponse.getStatusLine().getStatusCode());
        assertTrue("HTTP response body not set properly",
                response.getBody().get().equals(scheduleResponse));
        assertTrue("HTTP response headers not set properly",
                response.getHeaders().equals(headers.asMap()));
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

        ListMultimap<String, String> headers = ArrayListMultimap.create();
        headers.put(httpResponse.getAllHeaders()[0].getName(), httpResponse.getAllHeaders()[0].getValue());

        Response<ListAllSchedulesResponse> response = new Response<ListAllSchedulesResponse>(listScheduleResponse, headers.asMap(), httpResponse.getStatusLine().getStatusCode());
        assertTrue("HTTP response body not set properly",
                response.getBody().get().equals(listScheduleResponse));
        assertTrue("HTTP response headers not set properly",
                response.getHeaders().equals(headers.asMap()));
        assertTrue("HTTP response status not set properly",
                response.getStatus() == httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testAPIChannelViewResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        httpResponse.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE);

        ListMultimap<String, String> headers = ArrayListMultimap.create();
        headers.put(httpResponse.getAllHeaders()[0].getName(), httpResponse.getAllHeaders()[0].getValue());

        ChannelResponse channelResponse =
                ChannelResponse.newBuilder()
                        .setOk(true)
                        .setChannelObject(ChannelView.newBuilder()
                                .setAlias("Alias")
                                .setBackground(true)
                                .setChannelId("channelID")
                                .setCreated(DateTime.now())
                                .setChannelType(ChannelType.ANDROID.getIdentifier())
                                .setInstalled(true)
                                .setLastRegistration(DateTime.now().minus(12345L))
                                .setOptIn(true)
                                .setPushAddress("PUSH")
                                .build())
                        .build();

        Response<ChannelResponse> response = new Response<ChannelResponse>(channelResponse, headers.asMap(), httpResponse.getStatusLine().getStatusCode());
        assertTrue("HTTP response body not set properly",
                response.getBody().get().equals(channelResponse));
        assertTrue("HTTP response headers not set properly",
                response.getHeaders().equals(headers.asMap()));
        assertTrue("HTTP response status not set properly",
                response.getStatus() == httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testAPIListChannelsResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        httpResponse.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE);

        ListMultimap<String, String> headers = ArrayListMultimap.create();
        headers.put(httpResponse.getAllHeaders()[0].getName(), httpResponse.getAllHeaders()[0].getValue());

        ChannelResponse channelResponse = ChannelResponse.newBuilder()
                .setOk(true)
                .setNextPage("nextPage")
                .addChannel(ChannelView.newBuilder()
                        .setAlias("Alias")
                        .setBackground(true)
                        .setChannelId("channelID")
                        .setCreated(DateTime.now())
                        .setChannelType(ChannelType.ANDROID.getIdentifier())
                        .setInstalled(true)
                        .setLastRegistration(DateTime.now().minus(12345L))
                        .setOptIn(true)
                        .setPushAddress("PUSH")
                        .build())
                .build();

        Response<ChannelResponse> response = new Response<ChannelResponse>(channelResponse, headers.asMap(), httpResponse.getStatusLine().getStatusCode());
        assertTrue("HTTP response body not set properly",
                response.getBody().get().equals(channelResponse));
        assertTrue("HTTP response headers not set properly",
                response.getHeaders().equals(headers.asMap()));
        assertTrue("HTTP response status not set properly",
                response.getStatus() == httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testNamedUserListingResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        httpResponse.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE);

        ListMultimap<String, String> headers = ArrayListMultimap.create();
        headers.put(httpResponse.getAllHeaders()[0].getName(), httpResponse.getAllHeaders()[0].getValue());

        NamedUserListingResponse namedUserListingResponse = NamedUserListingResponse.newBuilder()
                .setOk(true)
                .setNamedUserView(NamedUserView.newBuilder()
                        .setChannelViews(ImmutableSet.of(ChannelView.newBuilder()
                                .setAlias("Alias")
                                .setBackground(true)
                                .setChannelId("channelID")
                                .setCreated(DateTime.now())
                                .setChannelType(ChannelType.ANDROID.getIdentifier())
                                .setInstalled(true)
                                .setLastRegistration(DateTime.now().minus(12345L))
                                .setOptIn(true)
                                .setPushAddress("PUSH")
                                .build()))
                        .build())
                .build();

        Response<NamedUserListingResponse> response = new Response<NamedUserListingResponse>(namedUserListingResponse, headers.asMap(), httpResponse.getStatusLine().getStatusCode());
        assertTrue("HTTP response body not set properly",
                response.getBody().get().equals(namedUserListingResponse));
        assertTrue("HTTP response headers not set properly",
                response.getHeaders().equals(headers.asMap()));
        assertTrue("HTTP response status not set properly",
                response.getStatus() == httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testAPIReportsListingResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        httpResponse.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE);

        ListMultimap<String, String> headers = ArrayListMultimap.create();
        headers.put(httpResponse.getAllHeaders()[0].getName(), httpResponse.getAllHeaders()[0].getValue());


        UUID one = UUID.randomUUID();
        UUID two = UUID.randomUUID();

        PushInfoResponse spir = PushInfoResponse.newBuilder()
                .setPushId(one)
                .setDirectResponses(4)
                .setSends(5)
                .setPushType(PushInfoResponse.PushType.UNICAST_PUSH)
                .setPushTime("2013-07-31 21:27:38")
                .setGroupId(two)
                .build();

        PushListingResponse pushListingResponse = PushListingResponse.newBuilder()
                .setNextPage("123")
                .addPushInfoObject(spir)
                .addPushInfoObject(spir)
                .addPushInfoObject(spir)
                .build();

        Response<PushListingResponse> response = new Response<PushListingResponse>(
                pushListingResponse,
                headers.asMap(),
                httpResponse.getStatusLine().getStatusCode());

        assertTrue("HTTP response not set properly",
                response.getBody().get().equals(pushListingResponse));
        assertTrue("HTTP response headers not set properly",
                response.getHeaders().equals(headers.asMap()));
        assertTrue("HTTP response status not set properly",
                response.getStatus() == httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testListIndividualPushAPIResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        httpResponse.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE);

        ListMultimap<String, String> headers = ArrayListMultimap.create();
        headers.put(httpResponse.getAllHeaders()[0].getName(), httpResponse.getAllHeaders()[0].getValue());


        UUID one = UUID.randomUUID();
        UUID two = UUID.randomUUID();

        PushInfoResponse pushInfoResponse = PushInfoResponse.newBuilder()
                .setPushId(one)
                .setDirectResponses(4)
                .setSends(5)
                .setPushType(PushInfoResponse.PushType.UNICAST_PUSH)
                .setPushTime("2013-07-31 21:27:38")
                .setGroupId(two)
                .build();

        Response<PushInfoResponse> response = new Response<PushInfoResponse>(
                pushInfoResponse,
                headers.asMap(),
                httpResponse.getStatusLine().getStatusCode());

        assertTrue("HTTP response not set properly",
                response.getBody().get().equals(pushInfoResponse));
        assertTrue("HTTP response headers not set properly",
                response.getHeaders().equals(headers.asMap()));
        assertTrue("HTTP response status not set properly",
                response.getStatus() == httpResponse.getStatusLine().getStatusCode());

    }

    @Test
    public void testAudienceSegmentResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        httpResponse.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE);

        ListMultimap<String, String> headers = ArrayListMultimap.create();
        headers.put(httpResponse.getAllHeaders()[0].getName(), httpResponse.getAllHeaders()[0].getValue());

        Selector andSelector = Selectors.tags("java", "lib");
        Selector compound = Selectors.or(andSelector, Selectors.not(Selectors.tag("mfd")));

        SegmentView segment = SegmentView.newBuilder()
                .setDisplayName("hello")
                .setCriteria(compound)
                .build();

        Response<SegmentView> response = new Response<SegmentView>(
                segment,
                headers.asMap(),
                httpResponse.getStatusLine().getStatusCode());

        assertTrue("HTTP response not set properly",
                response.getBody().get().equals(segment));
        assertTrue("HTTP response headers not set properly",
                response.getHeaders().equals(headers.asMap()));
        assertTrue("HTTP response status not set properly",
                response.getStatus() == httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testAPIListAllSegmentsResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        httpResponse.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE);

        ListMultimap<String, String> headers = ArrayListMultimap.create();
        headers.put(httpResponse.getAllHeaders()[0].getName(), httpResponse.getAllHeaders()[0].getValue());

        SegmentListingView listItem = SegmentListingView.newBuilder()
                .setCreationDate(123L)
                .setDisplayName("DisplayName")
                .setId("Id")
                .setModificationDate(321L)
                .build();

        ImmutableList<SegmentListingView> list = ImmutableList.<SegmentListingView>builder()
                .add(listItem)
                .add(listItem)
                .add(listItem)
                .build();

        SegmentListingResponse segments = SegmentListingResponse.newBuilder()
                .setNextPage("NextPage")
                .addAllSegmentObjects(list)
                .build();

        Response<SegmentListingResponse> response = new Response<SegmentListingResponse>(
                segments,
                headers.asMap(),
                httpResponse.getStatusLine().getStatusCode());

        assertTrue("HTTP response not set properly",
                response.getBody().get().equals(segments));
        assertTrue("HTTP response headers not set properly",
                response.getHeaders().equals(headers.asMap()));
        assertTrue("HTTP response status not set properly",
                response.getStatus() == httpResponse.getStatusLine().getStatusCode());
    }


    @Test
    public void testLocationResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
            new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        httpResponse.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE);

        ListMultimap<String, String> headers = ArrayListMultimap.create();
        headers.put(httpResponse.getAllHeaders()[0].getName(), httpResponse.getAllHeaders()[0].getValue());
            ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("hello", "kitty");

        LocationView locationView = LocationView.newBuilder()
            .setLocationId("ID")
            .setLocationType("Type")
            .setPropertiesNode(node)
            .build();

        LocationResponse locationResponse = LocationResponse.newBuilder()
            .addAllFeatures(Arrays.asList(locationView))
            .build();

        Response<LocationResponse> response = new Response<LocationResponse>(
            locationResponse,
            headers.asMap(),
            httpResponse.getStatusLine().getStatusCode());

        assertTrue("HTTP response not set properly",
            response.getBody().get().equals(locationResponse));
        assertTrue("HTTP response headers not set properly",
            response.getHeaders().equals(headers.asMap()));
        assertTrue("HTTP response status not set properly",
            response.getStatus() == httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testStaticListLookupResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        httpResponse.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE);

        ListMultimap<String, String> headers = ArrayListMultimap.create();
        headers.put(httpResponse.getAllHeaders()[0].getName(), httpResponse.getAllHeaders()[0].getValue());

        DateTime created = new DateTime(2014, 10, 1, 12, 0, 0, 0);
        DateTime updated = created.plus(Period.hours(48));

        StaticListView staticListView = StaticListView.newBuilder()
                .setOk(true)
                .setName("static_list_name")
                .setDescription("a great list")
                .setCreated(created)
                .setLastUpdated(updated)
                .setChannelCount(1234)
                .setStatus("processing")
                .build();

        Response<StaticListView> response = new Response<StaticListView>(
                staticListView,
                headers.asMap(),
                httpResponse.getStatusLine().getStatusCode());

        assertTrue("HTTP response not set properly",
                response.getBody().get().equals(staticListView));
        assertTrue("HTTP response headers not set properly",
                response.getHeaders().equals(headers.asMap()));
        assertTrue("HTTP response status not set properly",
                response.getStatus() == httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testStaticListListingResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        httpResponse.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE);

        ListMultimap<String, String> headers = ArrayListMultimap.create();
        headers.put(httpResponse.getAllHeaders()[0].getName(), httpResponse.getAllHeaders()[0].getValue());

        DateTime created = new DateTime(2014, 10, 1, 12, 0, 0, 0);
        DateTime updated = created.plus(Period.hours(48));

        StaticListView res1 = StaticListView.newBuilder()
                .setName("static_list_name")
                .setCreated(created)
                .setChannelCount(1234)
                .setLastUpdated(updated)
                .setStatus("ready")
                .build();

        StaticListView res2 = StaticListView.newBuilder()
                .setName("static_list_name")
                .setDescription("a great list")
                .setCreated(created)
                .setLastUpdated(updated)
                .setChannelCount(1234)
                .setStatus("processing")
                .build();

        StaticListListingResponse staticListListingResponse = StaticListListingResponse.newBuilder()
                .setOk(true)
                .addStaticList(res1)
                .addStaticList(res2)
                .build();

        Response<StaticListListingResponse> response = new Response<StaticListListingResponse>(
                staticListListingResponse,
                headers.asMap(),
                httpResponse.getStatusLine().getStatusCode());

        assertTrue("HTTP response not set properly",
                response.getBody().get().equals(staticListListingResponse));
        assertTrue("HTTP response headers not set properly",
                response.getHeaders().equals(headers.asMap()));
        assertTrue("HTTP response status not set properly",
                response.getStatus() == httpResponse.getStatusLine().getStatusCode());

    }

}
