package com.urbanairship.api.reports.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.reports.model.Response;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ResponseDeserializerTest {
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

       Response response = mapper.readValue(json, Response.class);
       assertNotNull(response);

       System.out.println(response);

       assertEquals(1337, response.getDeviceStatsMap().get("ios").getDirect());
       assertEquals(9999, response.getDeviceStatsMap().get("ios").getInfluenced());
       assertEquals(7331, response.getDeviceStatsMap().get("android").getDirect());
       assertEquals(8888, response.getDeviceStatsMap().get("android").getInfluenced());
       assertEquals(DateFormats.DATE_PARSER.parseDateTime("2013-07-01 00:00:00"), response.getDate());
   }
}
