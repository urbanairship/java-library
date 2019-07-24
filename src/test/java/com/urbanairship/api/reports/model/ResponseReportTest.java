package com.urbanairship.api.reports.model;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ResponseReportTest {

    @Test
    public void testReportResponse() {
        DateTime date = new DateTime(2014, 10, 1, 12, 0, 0, 0);

        DeviceStats deviceStats = DeviceStats.newBuilder()
                .setDirect(1111)
                .setInfluenced(2222)
                .build();

        ResponseReportResponse responseReportResponse1, responseReportResponse2;
        responseReportResponse1 = responseReportResponse2 = ResponseReportResponse.newBuilder()
                .setDate(date)
                .addDeviceStatsMapping("ios", deviceStats)
                .addDeviceStatsMapping("android", deviceStats)
                .build();

        ResponseReport responseReport = ResponseReport.newBuilder()
                .setNextPage("Woah! Another Page!")
                .addResponseObject(responseReportResponse1)
                .addResponseObject(responseReportResponse2)
                .build();

        assertNotNull(responseReport);
        assertEquals(2, responseReport.getResponses().get().size());
        assertEquals(1111, responseReport.getResponses().get().get(0).getDeviceStatsMap().get().get("ios").getDirect().get().intValue());
        assertEquals(2222, responseReport.getResponses().get().get(1).getDeviceStatsMap().get().get("android").getInfluenced().get().intValue());
    }
}
