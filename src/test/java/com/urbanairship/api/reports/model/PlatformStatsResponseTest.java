package com.urbanairship.api.reports.model;


import com.urbanairship.api.reports.model.PlatformStats;
import com.urbanairship.api.reports.model.PlatformStatsResponse;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

}
