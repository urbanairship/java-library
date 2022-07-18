package com.urbanairship.api.reports.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.reports.parse.ReportsObjectMapper;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.IOException;
import java.util.Objects;

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
        assertEquals(1111, Objects.requireNonNull(responseReport.getResponses().get().get(0).getDeviceStatsMap().get().get("ios")).getDirect().get().intValue());
        assertEquals(2222, Objects.requireNonNull(responseReport.getResponses().get().get(1).getDeviceStatsMap().get().get("android")).getInfluenced().get().intValue());
    }

    @Test
    public void testErrorAPIReportResponse() throws IOException {
        String jsonResponse = "{\n" +
                "    \"ok\": false,\n" +
                "    \"error\": \"error\",\n" +
                "    \"details\": {\n" +
                "        \"error\": \"error\"\n" +
                "    }\n" +
                "}";

        ObjectMapper mapper = ReportsObjectMapper.getInstance();
        ResponseReport response = mapper.readValue(jsonResponse, ResponseReport.class);
        assertEquals("error", response.getError().get());
        assertEquals("error", response.getErrorDetails().get().getError().get());
        assertEquals(false, response.getOk().get());
    }
}
