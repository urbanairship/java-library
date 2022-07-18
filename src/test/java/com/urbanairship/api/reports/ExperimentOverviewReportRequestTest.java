package com.urbanairship.api.reports;

import com.google.common.collect.ImmutableList;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.reports.model.ExperimentControl;
import com.urbanairship.api.reports.model.ExperimentOverviewReportResponse;
import com.urbanairship.api.reports.model.ExperimentVariant;
import org.apache.http.entity.ContentType;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ExperimentOverviewReportRequestTest {

    ExperimentOverviewReportRequest experimentOverviewReportRequest;

    String experimentOverviewReportUri = "https://go.urbanairship.com/api/reports/experiment/overview/0f56423d-d410-4199-8ce6-9ade89447206";

    @Before
    public void setupCreate() {
        experimentOverviewReportRequest = ExperimentOverviewReportRequest.newRequest("0f56423d-d410-4199-8ce6-9ade89447206");
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(experimentOverviewReportRequest.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(experimentOverviewReportRequest.getHttpMethod(), Request.HttpMethod.GET);
    }

    @Test
    public void testBody() throws Exception {
        assertNull(experimentOverviewReportRequest.getRequestBody());
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(experimentOverviewReportRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedResponseCustomEventsDetailsListingUri = URI.create(experimentOverviewReportUri);
        assertEquals(experimentOverviewReportRequest.getUri(baseURI), expectedResponseCustomEventsDetailsListingUri);
    }

    @Test
    public void testReportParser() throws Exception {

        ExperimentVariant experimentVariant1 = new ExperimentVariant(0, "test variant A", 40.0f, 0,0,0.0f,0,0.0f);
        ExperimentVariant experimentVariant2 = new ExperimentVariant(1, "Test variant B", 40.0f, 0,0,0.0f,0,0.0f);

        List<ExperimentVariant> variants = new ArrayList();
        variants.add(experimentVariant1);
        variants.add(experimentVariant2);
        ImmutableList<ExperimentVariant> immutableList = ImmutableList.copyOf(variants);

        ExperimentControl experimentControl = new ExperimentControl(20.0f, 0,0, 0.0f);

        ExperimentOverviewReportResponse experimentOverviewReportResponse = new ExperimentOverviewReportResponse(false, "appKey", "0f56423d-d410-4199-8ce6-9ade89447206","0f56423d-d410-4199-8ce6-9ade89447206","2021-06-07 14:06:23",0,0,0,0,0,immutableList, experimentControl, null, null);

        String response = "{\"app_key\":\"appKey\",\"experiment_id\":\"0f56423d-d410-4199-8ce6-9ade89447206\",\"push_id\":\"0f56423d-d410-4199-8ce6-9ade89447206\",\"created\":\"2021-06-07 14:06:23\",\"sends\":0,\"direct_responses\":0,\"influenced_responses\":0,\"web_clicks\":0,\"web_sessions\":0,\"variants\":[{\"id\":0,\"name\":\"test variant A\",\"audience_pct\":40.0,\"sends\":0,\"direct_responses\":0,\"direct_response_pct\":0.0,\"indirect_responses\":0,\"indirect_response_pct\":0.0},{\"id\":1,\"name\":\"Test variant B\",\"audience_pct\":40.0,\"sends\":0,\"direct_responses\":0,\"direct_response_pct\":0.0,\"indirect_responses\":0,\"indirect_response_pct\":0.0}],\"control\":{\"audience_pct\":20.0,\"sends\":0,\"responses\":0,\"response_rate_pct\":0.0}}";
        assertEquals(experimentOverviewReportRequest.getResponseParser().parse(response), experimentOverviewReportResponse);
    }
}
