package com.urbanairship.api.reports;

import com.google.common.collect.ImmutableList;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.reports.model.CustomEventsDetailResponse;
import com.urbanairship.api.reports.model.CustomEventsDetailsListingResponse;
import org.apache.http.entity.ContentType;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CustomEventsDetailsListingRequestTest {

    DateTime start = new DateTime(2019, 3, 1, 12, 0, 0, 0, DateTimeZone.UTC);
    DateTime end = new DateTime(2022, 3, 1, 12, 0, 0, 0, DateTimeZone.UTC);

    CustomEventsDetailsListingRequest customEventsDetailsListingRequest;

    String CustomEventsDetailsListingUri = "https://go.urbanairship.com/api/reports/events/?start=2019-03-01T12%3A00%3A00&end=2022-03-01T12%3A00%3A00";

    @Before
    public void setupCreate() {
        customEventsDetailsListingRequest = CustomEventsDetailsListingRequest.newRequest(start, end);
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(customEventsDetailsListingRequest.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(customEventsDetailsListingRequest.getHttpMethod(), Request.HttpMethod.GET);
    }

    @Test
    public void testBody() throws Exception {
        assertNull(customEventsDetailsListingRequest.getRequestBody());
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(customEventsDetailsListingRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedResponseCustomEventsDetailsListingUri = URI.create(CustomEventsDetailsListingUri);
        assertEquals(customEventsDetailsListingRequest.getUri(baseURI), expectedResponseCustomEventsDetailsListingUri);
    }

    @Test
    public void testReportParser() throws Exception {

        CustomEventsDetailResponse customEventsDetailResponse1 = 
        new CustomEventsDetailResponse("banner_image", "indirect", "ua_mcrap", 1, 1f);

        CustomEventsDetailResponse customEventsDetailResponse2 = 
        new CustomEventsDetailResponse("bounce", "direct", "custom", 23, 0f);

        CustomEventsDetailsListingResponse customEventsDetailsListingResponse = 
            new CustomEventsDetailsListingResponse(
                true,
                "https://go.urbanairship.com/api/reports/events?start=2020-08-01T10:00:00.000Z&end=2020-08-15T20:00:00.000Z&precision=MONTHLY&page_size=20&page=2", 
                2f,
                709, 
                ImmutableList.of(customEventsDetailResponse1, customEventsDetailResponse2)
            );

        String response =
        "{\n" +
                "    \"ok\": true,\n" +
                "                    \"total_value\": 2,\n" +
                "                    \"total_count\": 709,\n" +
                "                    \"next_page\": \"https://go.urbanairship.com/api/reports/events?start=2020-08-01T10:00:00.000Z&end=2020-08-15T20:00:00.000Z&precision=MONTHLY&page_size=20&page=2\",\n" +
                "                    \"events\": [\n" +
                "                        {\n" +
                "                            \"name\": \"banner_image\",\n" +
                "                            \"conversion\": \"indirect\",\n" +
                "                            \"location\": \"ua_mcrap\",\n" +
                "                            \"count\": 1,\n" +
                "                            \"value\": 1\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"name\": \"bounce\",\n" +
                "                            \"conversion\": \"direct\",\n" +
                "                            \"location\": \"custom\",\n" +
                "                            \"count\": 23,\n" +
                "                            \"value\": 0\n" +
                "                        }\n" +
                "                    ]\n" +
                "}";

        assertEquals(customEventsDetailsListingRequest.getResponseParser().parse(response), customEventsDetailsListingResponse);
    }
}
