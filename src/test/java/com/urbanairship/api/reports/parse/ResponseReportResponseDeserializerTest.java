package com.urbanairship.api.reports.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.reports.model.ResponseReportResponse;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ResponseReportResponseDeserializerTest {
    private static final ObjectMapper mapper = ReportsObjectMapper.getInstance();

    @Test
    public void testResponseBody() throws IOException {

        String json =
                "{\n" +
                "  \"date\":\"2013-07-01 00:00:00\",\n" +
                "  \"ios\": {\n" +
                "    \"direct\":1337,\n" +
                "    \"influenced\":9999\n" +
                "  },\n" +
                "  \"android\": {\n" +
                "    \"direct\":7331,\n" +
                "    \"influenced\":8888\n" +
                "  }\n" +
                "}";

        ResponseReportResponse responseReportResponse = mapper.readValue(json, ResponseReportResponse.class);
        Assert.assertNotNull(responseReportResponse);

        System.out.println(responseReportResponse);

        Assert.assertEquals(1337, responseReportResponse.getDeviceStatsMap().get().get("ios").getDirect().get().intValue());
        Assert.assertEquals(9999, responseReportResponse.getDeviceStatsMap().get().get("ios").getInfluenced().get().intValue());
        Assert.assertEquals(7331, responseReportResponse.getDeviceStatsMap().get().get("android").getDirect().get().intValue());
        Assert.assertEquals(8888, responseReportResponse.getDeviceStatsMap().get().get("android").getInfluenced().get().intValue());
        Assert.assertEquals(DateFormats.DATE_PARSER.parseDateTime("2013-07-01 00:00:00"), responseReportResponse.getDate().get());
    }
}
