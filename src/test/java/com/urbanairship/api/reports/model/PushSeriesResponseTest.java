package com.urbanairship.api.reports.model;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PushSeriesResponseTest {

    @Test
    public void testPerPushSeriesResponse() {
        DateTime start = new DateTime(2014, 10, 1, 12, 0, 0, 0);
        DateTime end = start.plus(Period.hours(48));
        UUID pushId = UUID.randomUUID();

        PerPushCounts count = PerPushCounts.newBuilder()
                .setDirectResponses(1234)
                .setInfluencedResponses(2345)
                .setSends(3456)
                .build();

        PlatformCounts counts = PlatformCounts.newBuilder()
                .addPlatform(PlatformType.IOS, count)
                .addPlatform(PlatformType.ANDROID, count)
                .addPlatform(PlatformType.AMAZON, count)
                .setTime(start)
                .build();

        PushSeriesResponse response = PushSeriesResponse.newBuilder()
                .setAppKey("an_app_key")
                .setPushID(pushId)
                .setStart(start)
                .setEnd(end)
                .setPrecision(Precision.DAILY)
                .addPlatformCount(counts)
                .addPlatformCount(counts)
                .build();

        assertNotNull(response);
        assertEquals("an_app_key", response.getAppKey());
        assertEquals(pushId, response.getPushID());
        assertEquals(start, response.getStart());
        assertEquals(end, response.getEnd());
        assertEquals(Precision.DAILY, response.getPrecision());
        assertEquals(2, response.getCounts().size());
    }
}
