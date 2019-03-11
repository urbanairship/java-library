package com.urbanairship.api.reports.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.reports.model.ResponseReport;
import com.urbanairship.api.reports.model.ResponseReportResponse;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ResponseReportDeserializerTest {
    private static final ObjectMapper mapper = ReportsObjectMapper.getInstance();

    @Test
    public void testMultipleResponses() throws IOException {

        String json =
                "{\n" +
                "   \"next_page\":\"Another Page, What Up!\",\n" +
                "   \"responses\":[\n" +
                "     {\n" +
                "       \"date\":\"2013-07-01 00:00:00\",\n" +
                "       \"ios\": {\n" +
                "           \"direct\":1337,\n" +
                "           \"influenced\":9999\n" +
                "       },\n" +
                "       \"android\": {\n" +
                "           \"direct\":7331,\n" +
                "           \"influenced\":8888\n" +
                "       }\n" +
                "     },\n" +
                "     {\n" +
                "       \"android\": {\n" +
                "           \"direct\":1996,\n" +
                "           \"influenced\":1234\n" +
                "       },\n" +
                "       \"date\":\"2015-10-15 11:22:33\",\n" +
                "       \"ios\": {\n" +
                "           \"direct\":5813,\n" +
                "           \"influenced\":1123\n" +
                "       }\n" +
                "     }\n" +
                "   ]\n" +
                "}";

        ResponseReport responseReport = mapper.readValue(json, ResponseReport.class);
        assertNotNull(responseReport);

        System.out.println(responseReport);

        ResponseReportResponse responseReportResponse1 = responseReport.getResponses().get().get(0);
        assertEquals(DateFormats.DATE_PARSER.parseDateTime("2013-07-01 00:00:00"), responseReportResponse1.getDate().get());
        assertEquals(1337, responseReportResponse1.getDeviceStatsMap().get().get("ios").getDirect().get().intValue());
        assertEquals(9999, responseReportResponse1.getDeviceStatsMap().get().get("ios").getInfluenced().get().intValue());
        assertEquals(7331, responseReportResponse1.getDeviceStatsMap().get().get("android").getDirect().get().intValue());
        assertEquals(8888, responseReportResponse1.getDeviceStatsMap().get().get("android").getInfluenced().get().intValue());

        ResponseReportResponse responseReportResponse2 = responseReport.getResponses().get().get(1);
        assertEquals(DateFormats.DATE_PARSER.parseDateTime("2015-10-15 11:22:33"), responseReportResponse2.getDate().get());
        assertEquals(1996, responseReportResponse2.getDeviceStatsMap().get().get("android").getDirect().get().intValue());
        assertEquals(1234, responseReportResponse2.getDeviceStatsMap().get().get("android").getInfluenced().get().intValue());
        assertEquals(5813, responseReportResponse2.getDeviceStatsMap().get().get("ios").getDirect().get().intValue());
        assertEquals(1123, responseReportResponse2.getDeviceStatsMap().get().get("ios").getInfluenced().get().intValue());
    }
}
