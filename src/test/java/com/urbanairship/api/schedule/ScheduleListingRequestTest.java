package com.urbanairship.api.schedule;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.schedule.model.ListAllSchedulesResponse;
import com.urbanairship.api.schedule.model.SchedulePayloadResponse;
import com.urbanairship.api.schedule.parse.ScheduleObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class ScheduleListingRequestTest {

    ScheduleListingRequest listAllSchedulesRequest = ScheduleListingRequest.newRequest();
    ScheduleListingRequest listSchedulesWithParamsRequest = ScheduleListingRequest.newRequest(UUID.fromString("643a297a-7313-45f0-853f-e68785e54c77"), 25, ListSchedulesOrderType.ASC);
    ScheduleListingRequest listSingleScheduleRequest = ScheduleListingRequest.newRequest("id");
    ScheduleListingRequest listNextPageSchedulesRequest = ScheduleListingRequest.newRequest(URI.create("https://go.urbanairship.com/api/schedules/?start=643a297a-7313-45f0-853f-e68785e54c77&limit=25&order=asc"));

    @Test
    public void testContentType() throws Exception {
        assertEquals(listAllSchedulesRequest.getContentType(), null);
        assertEquals(listSchedulesWithParamsRequest.getContentType(), null);
        assertEquals(listSingleScheduleRequest.getContentType(), null);
        assertEquals(listNextPageSchedulesRequest.getContentType(), null);

    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(listAllSchedulesRequest.getHttpMethod(), Request.HttpMethod.GET);
        assertEquals(listSchedulesWithParamsRequest.getHttpMethod(), Request.HttpMethod.GET);
        assertEquals(listSingleScheduleRequest.getHttpMethod(), Request.HttpMethod.GET);
        assertEquals(listNextPageSchedulesRequest.getHttpMethod(), Request.HttpMethod.GET);
    }

    @Test
    public void testBody() throws Exception {
        assertEquals(listAllSchedulesRequest.getRequestBody(), null);
        assertEquals(listSchedulesWithParamsRequest.getRequestBody(), null);
        assertEquals(listSingleScheduleRequest.getRequestBody(), null);
        assertEquals(listNextPageSchedulesRequest.getRequestBody(), null);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(listAllSchedulesRequest.getRequestHeaders(), headers);
        assertEquals(listSchedulesWithParamsRequest.getRequestHeaders(), headers);
        assertEquals(listSingleScheduleRequest.getRequestHeaders(), headers);
        assertEquals(listNextPageSchedulesRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expextedURI = URI.create("https://go.urbanairship.com/api/schedules/");
        assertEquals(listAllSchedulesRequest.getUri(baseURI), expextedURI);

        expextedURI = URI.create("https://go.urbanairship.com/api/schedules/id");
        assertEquals(listSingleScheduleRequest.getUri(baseURI), expextedURI);

        expextedURI = URI.create("https://go.urbanairship.com/api/schedules/?start=643a297a-7313-45f0-853f-e68785e54c77&limit=25&order=asc");
        assertEquals(listSchedulesWithParamsRequest.getUri(baseURI), expextedURI);
        assertEquals(listNextPageSchedulesRequest.getUri(baseURI), expextedURI);
    }

    @Test
    public void testListSchedulesParser() throws Exception {
        ResponseParser responseParser = new ResponseParser<ListAllSchedulesResponse>() {
            @Override
            public ListAllSchedulesResponse parse(String response) throws IOException {
                return ScheduleObjectMapper.getInstance().readValue(response, ListAllSchedulesResponse.class);
            }
        };

        String response = "{\"ok\":true,\"count\":5,\"total_count\":6,\"schedules\":" +
            "[{\"url\":\"https://go.urbanairship.com/api/schedules/5a60e0a6-9aa7-449f-a038-6806e572baf3\",\"" +
            "schedule\":{\"scheduled_time\":\"2015-01-01T08:00:00\"},\"push\":{\"audience\":\"ALL\",\"device" +
            "_types\":[\"android\",\"ios\"],\"notification\":{\"alert\":\"Happy New Year 2015!\",\"android\"" +
            ":{},\"ios\":{}}},\"push_ids\":[\"8430f2e0-ec07-4c1e-adc4-0c7c7978e648\"]},{\"url\":\"https://go" +
            ".urbanairship.com/api/schedules/f53aa2bd-018a-4482-8d7d-691d13407973\",\"schedule\":{\"schedule" +
            "d_time\":\"2016-01-01T08:00:00\"},\"push\":{\"audience\":\"ALL\",\"device_types\":[\"android\"," +
            "\"ios\"],\"notification\":{\"alert\":\"Happy New Year 2016!\",\"android\":{},\"ios\":{}}},\"pus" +
            "h_ids\":[\"b217a321-922f-4aee-b239-ca1b58c6b652\"]}]}";
        assertEquals(listAllSchedulesRequest.getResponseParser().parse(response), responseParser.parse(response));
        assertEquals(listNextPageSchedulesRequest.getResponseParser().parse(response), responseParser.parse(response));
        assertEquals(listSchedulesWithParamsRequest.getResponseParser().parse(response), responseParser.parse(response));
    }

    @Test
    public void testListSingleScheduleParser() throws Exception {
        ResponseParser responseParser = new ResponseParser<ListAllSchedulesResponse>() {
            @Override
            public ListAllSchedulesResponse parse(String response) throws IOException {
                return ListAllSchedulesResponse.newBuilder()
                    .setCount(1)
                    .setTotalCount(1)
                    .setOk(true)
                    .addSchedule(ScheduleObjectMapper.getInstance().readValue(response, SchedulePayloadResponse.class))
                    .build();
            }
        };

        String response = "{\"schedule\":{\"scheduled_time\":\"2015-08-07T22:10:44\"},\"name\":\"Special Scheduled" +
            "Push 20\",\"push\":{\"audience\":\"ALL\",\"device_types\":\"all\",\"notification\":{\"alert\":\"Scheduled" +
            "Push 20\"}},\"push_ids\":[\"274f9aa4-2d00-4911-a043-70129f29adf2\"]}";

        assertEquals(listSingleScheduleRequest.getResponseParser().parse(response), responseParser.parse(response));
    }
}
