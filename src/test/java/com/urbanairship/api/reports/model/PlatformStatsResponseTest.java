package com.urbanairship.api.reports.model;


import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.reports.parse.ReportsObjectMapper;

public class PlatformStatsResponseTest {

    @Test
    public void testPlatformStatsResponse() {
        DateTime date = new DateTime(2014, 10, 1, 12, 0, 0, 0);

        PlatformStats obj = PlatformStats.newBuilder()
                .setAndroid(1234)
                .setIOS(5678)
                .setDate(date)
                .build();

        PlatformStatsResponse response = PlatformStatsResponse.newBuilder()
                .setNextPage("Value for Next Page")
                .addPlatformStatsObject(obj)
                .addPlatformStatsObject(obj)
                .build();

        assertNotNull(response);
        assertEquals(2, response.getPlatformStatsObjects().get().size());
        assertEquals("Value for Next Page", response.getNextPage().get());
        assertEquals(1234, response.getPlatformStatsObjects().get().get(0).getAndroid());
        assertEquals(5678, response.getPlatformStatsObjects().get().get(1).getIos());

    }

    @Test
    public void testErrorAPIPlatformStatsResponse() throws IOException {
        String jsonResponse = "{\n" +
                "    \"ok\": false,\n" +
                "    \"error\": \"error\",\n" +
                "    \"details\": {\n" +
                "        \"error\": \"error\"\n" +
                "    }\n" +
                "}";

        ObjectMapper mapper = ReportsObjectMapper.getInstance();
        PlatformStatsResponse response = mapper.readValue(jsonResponse, PlatformStatsResponse.class);
        assertEquals("error", response.getError().get());
        assertEquals("error", response.getErrorDetails().get().getError().get());
        assertEquals(false, response.getOk());
    }

}
