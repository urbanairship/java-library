package com.urbanairship.api.reports;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.reports.model.Precision;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class PushSeriesRequestTest {
    String pushId = UUID.randomUUID().toString();
    String queryPathString = "/api/reports/perpush/series/" + pushId + "?precision=HOURLY&start=2014-10-01T12%3A00%3A00&end=2014-10-03T12%3A00%3A00";
    DateTime start = new DateTime(2014, 10, 1, 12, 0, 0, 0, DateTimeZone.UTC);
    DateTime end = start.plus(Period.hours(48));

    private PushSeriesRequest setup() {
        return PushSeriesRequest.newRequest(pushId)
                .setStart(start)
                .setEnd(end)
                .setPrecision(Precision.HOURLY);
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(this.setup().getContentType(), null);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(this.setup().getHttpMethod(), Request.HttpMethod.GET);
    }

    @Test
    public void testBody() throws Exception {
        assertEquals(this.setup().getRequestBody(), null);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION);

        assertEquals(this.setup().getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedURI = URI.create("https://go.urbanairship.com" + queryPathString);
        assertEquals(this.setup().getUri(baseURI), expectedURI);
    }


}
