package com.urbanairship.api.reports.model;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PlatformCountsTest {
    @Test
    public void testPlatformCounts() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dt = formatter.parseDateTime("2013-07-31 21:27:38");

        PerPushCounts perPush = PerPushCounts.newBuilder()
                .setDirectResponses(1)
                .setInfluencedResponses(2)
                .setSends(3)
                .build();

        RichPerPushCounts richPerPush = RichPerPushCounts.newBuilder()
                .setResponses(1)
                .setSends(2)
                .build();

        PlatformCounts obj = PlatformCounts.newBuilder()
                .addPlatform(PlatformType.IOS, perPush)
                .addPlatform(PlatformType.ANDROID, perPush)
                .addPlatform(PlatformType.AMAZON, perPush)
                .addRichPlatform(PlatformType.IOS, richPerPush)
                .addRichPlatform(PlatformType.ANDROID, richPerPush)
                .setTime(dt)
                .build();

        assertNotNull(obj);
        assertEquals(3, obj.getPushPlatforms().size());
        assertEquals(1, obj.getPushPlatforms().get(PlatformType.IOS).getDirectResponses());
        assertEquals(2, obj.getPushPlatforms().get(PlatformType.ANDROID).getInfluencedResponses());
        assertEquals(3, obj.getPushPlatforms().get(PlatformType.AMAZON).getSends());
        assertEquals(2, obj.getRichPushPlatforms().size());
        assertEquals(1, obj.getRichPushPlatforms().get(PlatformType.IOS).getResponses());
        assertEquals(2, obj.getRichPushPlatforms().get(PlatformType.ANDROID).getSends());
    }
}