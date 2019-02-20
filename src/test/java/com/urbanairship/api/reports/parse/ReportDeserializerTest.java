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
    public void testFullResponse() throws IOException {

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
                "     }\n" +
                "   ]\n" +
                "}";

        System.out.println(json);
        Report test = mapper.readValue(json, Report.class);
        assertNotNull(test);
        System.out.println(test);
    }
}
