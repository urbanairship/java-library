package com.urbanairship.api.reports.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.reports.model.Report;
import com.urbanairship.api.reports.model.Response;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ReportDeserializerTest {
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

        Report report = mapper.readValue(json, Report.class);
        assertNotNull(report);

        System.out.println(report);

        Response response1 = report.getResponses().get().get(0);
        assertEquals(DateFormats.DATE_PARSER.parseDateTime("2013-07-01 00:00:00"), response1.getDate());
        assertEquals(1337, response1.getDeviceStatsMap().get("ios").getDirect());
        assertEquals(9999, response1.getDeviceStatsMap().get("ios").getInfluenced());
        assertEquals(7331, response1.getDeviceStatsMap().get("android").getDirect());
        assertEquals(8888, response1.getDeviceStatsMap().get("android").getInfluenced());

        Response response2 = report.getResponses().get().get(1);
        assertEquals(DateFormats.DATE_PARSER.parseDateTime("2015-10-15 11:22:33"), response2.getDate());
        assertEquals(1996, response2.getDeviceStatsMap().get("android").getDirect());
        assertEquals(1234, response2.getDeviceStatsMap().get("android").getInfluenced());
        assertEquals(5813, response2.getDeviceStatsMap().get("ios").getDirect());
        assertEquals(1123, response2.getDeviceStatsMap().get("ios").getInfluenced());
    }
}
