package com.urbanairship.api.reports;

import com.google.common.collect.ImmutableList;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.reports.model.Precision;
import com.urbanairship.api.reports.model.WebCounts;
import com.urbanairship.api.reports.model.WebCountsStats;
import com.urbanairship.api.reports.model.WebResponseReportResponse;
import org.apache.http.entity.ContentType;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class WebResponseReportRequestTest {

    WebResponseReportRequest webResponseReportRequest;

    String WebResponseReportReportUri = "https://go.urbanairship.com/api/reports/web/interaction?app_key=appKey&start=2020-02-01T00%3A00%3A00&precision=MONTHLY&end=2020-03-01T00%3A00%3A00";

    @Before
    public void setupCreate() {
        webResponseReportRequest = WebResponseReportRequest.newRequest("appKey", DateTime.parse("2020-02-01T00:00:00.000Z"))
        .setPrecision(Precision.MONTHLY).setEndTime(DateTime.parse("2020-03-01T00:00:00.000Z"));
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(webResponseReportRequest.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(webResponseReportRequest.getHttpMethod(), Request.HttpMethod.GET);
    }

    @Test
    public void testBody() throws Exception {
        assertNull(webResponseReportRequest.getRequestBody());
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(webResponseReportRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedResponseCustomEventsDetailsListingUri = URI.create(WebResponseReportReportUri);
        assertEquals(webResponseReportRequest.getUri(baseURI), expectedResponseCustomEventsDetailsListingUri);
    }

    @Test
    public void testReportParser() throws Exception {

        WebCountsStats webCountsStats1 = new WebCountsStats(2, 13);
        WebCountsStats webCountsStats2 = new WebCountsStats(4, 35);

        WebCounts webCounts1 = new WebCounts(webCountsStats1, "2020-02-01 00:00:00");
        WebCounts webCounts2 = new WebCounts(webCountsStats2, "2020-03-01 00:00:00");

        List<WebCounts> counts = new ArrayList();
        counts.add(webCounts1);
        counts.add(webCounts2);

        ImmutableList<WebCounts> immutableList = ImmutableList.copyOf(counts);

        WebResponseReportResponse webResponseReportResponse = new WebResponseReportResponse(
                false,
                "appKey",
                "2020-03-01T00:00:00",
                Precision.MONTHLY,
                "2020-02-01T00:00:00",
                immutableList,
                null,
                null);


        String response = "{\n" +
                "    \"start\": \"2020-02-01 00:00:00\",\n" +
                "    \"end\": \"2020-03-01 00:00:00\",\n" +
                "    \"precision\": \"MONTHLY\",\n" +
                "    \"app_key\": \"appKey\",\n" +
                "    \"total_counts\": [\n" +
                "        {\n" +
                "            \"date\": \"2020-02-01 00:00:00\",\n" +
                "            \"counts\": {\n" +
                "                \"clicks\": 2,\n" +
                "                \"sessions\": 13\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": \"2020-03-01 00:00:00\",\n" +
                "            \"counts\": {\n" +
                "                \"clicks\": 4,\n" +
                "                \"sessions\": 35\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        assertEquals(webResponseReportRequest.getResponseParser().parse(response), webResponseReportResponse);
    }
}
