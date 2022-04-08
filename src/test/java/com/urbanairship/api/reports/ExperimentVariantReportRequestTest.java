package com.urbanairship.api.reports;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.reports.ExperimentVariantReportRequest;
import com.urbanairship.api.reports.model.ExperimentVariantReportResponse;
import com.urbanairship.api.reports.model.ExperimentVariantStats;
import com.urbanairship.api.reports.parse.ReportsObjectMapper;
import org.apache.http.entity.ContentType;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ExperimentVariantReportRequestTest {
    ObjectMapper mapper = ReportsObjectMapper.getInstance();

    ExperimentVariantReportRequest experimentVariantReportRequest;

    String experimentVariantReportUri = "https://go.urbanairship.com/api/reports/experiment/detail/0f56423d-d410-4199-8ce6-9ade89447206/1";

    @Before
    public void setupCreate() {
        experimentVariantReportRequest = ExperimentVariantReportRequest.newRequest("0f56423d-d410-4199-8ce6-9ade89447206", "1");
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(experimentVariantReportRequest.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(experimentVariantReportRequest.getHttpMethod(), Request.HttpMethod.GET);
    }

    @Test
    public void testBody() throws Exception {
        assertEquals(experimentVariantReportRequest.getRequestBody(), null);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(experimentVariantReportRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedResponseCustomEventsDetailsListingUri = URI.create(experimentVariantReportUri);
        assertEquals(experimentVariantReportRequest.getUri(baseURI), expectedResponseCustomEventsDetailsListingUri);
    }

    @Test
    public void testReportParser() throws Exception {

        ExperimentVariantStats experimentVariantStats = new ExperimentVariantStats("devicePlatformBreakdown", 0, null, 0, 0);
        ExperimentVariantStats  experimentVariantStats2 = new ExperimentVariantStats("devicePlatformBreakdown", 0, null, 0, 0);
        ExperimentVariantStats experimentVariantStats3 = new ExperimentVariantStats("devicePlatformBreakdown", 0, 0, null, 0);
        ExperimentVariantStats experimentVariantStats4 = new ExperimentVariantStats("devicePlatformBreakdown", 0, null, 0, 0);

        Map<String, ExperimentVariantStats> mutableMap = new HashMap<>();
        mutableMap.put("amazon", experimentVariantStats);
        mutableMap.put("web", experimentVariantStats2);
        mutableMap.put("android", experimentVariantStats3);
        mutableMap.put("ios", experimentVariantStats4);

        ImmutableMap<String, ExperimentVariantStats> immutableMap = ImmutableMap.copyOf(mutableMap);

        Integer test = 0;
        Integer variant = 1;

        ExperimentVariantReportResponse experimentVariantReportResponse = new ExperimentVariantReportResponse(false, "appKey", "0f56423d-d410-4199-8ce6-9ade89447206", "0f56423d-d410-4199-8ce6-9ade89447206", "2021-06-07 14:06:23", variant, "Tesr variant B", test, test, test, immutableMap, null, null);

        String response =
        "{\"app_key\":\"appKey\",\"experiment_id\":\"0f56423d-d410-4199-8ce6-9ade89447206\",\"push_id\":\"0f56423d-d410-4199-8ce6-9ade89447206\",\"created\":\"2021-06-07 14:06:23\",\"variant\":1,\"variant_name\":\"Tesr variant B\",\"sends\":0,\"direct_responses\":0,\"influenced_responses\":0,\"platforms\":{\"amazon\":{\"type\":\"devicePlatformBreakdown\",\"direct_responses\":0,\"influenced_responses\":0,\"sends\":0},\"web\":{\"type\":\"devicePlatformBreakdown\",\"direct_responses\":0,\"influenced_responses\":0,\"sends\":0},\"android\":{\"type\":\"devicePlatformBreakdown\",\"direct_responses\":0,\"indirect_responses\":0,\"sends\":0},\"ios\":{\"type\":\"devicePlatformBreakdown\",\"direct_responses\":0,\"influenced_responses\":0,\"sends\":0}}}";

        assertEquals(experimentVariantReportRequest.getResponseParser().parse(response), experimentVariantReportResponse);
    }
}
