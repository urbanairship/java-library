package com.urbanairship.api.reports.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.reports.model.ResponseReport;
import com.urbanairship.api.reports.model.ResponseReportResponse;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

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
        Assert.assertNotNull(responseReport);

        System.out.println(responseReport);

        ResponseReportResponse responseReportResponse1 = responseReport.getResponses().get().get(0);
        Assert.assertEquals(DateFormats.DATE_PARSER.parseDateTime("2013-07-01 00:00:00"), responseReportResponse1.getDate().get());
        Assert.assertEquals(1337, responseReportResponse1.getDeviceStatsMap().get().get("ios").getDirect().get().intValue());
        Assert.assertEquals(9999, responseReportResponse1.getDeviceStatsMap().get().get("ios").getInfluenced().get().intValue());
        Assert.assertEquals(7331, responseReportResponse1.getDeviceStatsMap().get().get("android").getDirect().get().intValue());
        Assert.assertEquals(8888, responseReportResponse1.getDeviceStatsMap().get().get("android").getInfluenced().get().intValue());

        ResponseReportResponse responseReportResponse2 = responseReport.getResponses().get().get(1);
        Assert.assertEquals(DateFormats.DATE_PARSER.parseDateTime("2015-10-15 11:22:33"), responseReportResponse2.getDate().get());
        Assert.assertEquals(1996, responseReportResponse2.getDeviceStatsMap().get().get("android").getDirect().get().intValue());
        Assert.assertEquals(1234, responseReportResponse2.getDeviceStatsMap().get().get("android").getInfluenced().get().intValue());
        Assert.assertEquals(5813, responseReportResponse2.getDeviceStatsMap().get().get("ios").getDirect().get().intValue());
        Assert.assertEquals(1123, responseReportResponse2.getDeviceStatsMap().get().get("ios").getInfluenced().get().intValue());
    }
}
