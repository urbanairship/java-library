package com.urbanairship.api.reports.model;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

import org.junit.Assert;

public class DeviceStatsTest {

    @Test
    public void testDeviceStats() {
        DeviceStats deviceStats = DeviceStats.newBuilder()
                .setDirect(1123)
                .setInfluenced(5813)
                .build();

        assertNotNull(deviceStats);
        Assert.assertEquals(1123, deviceStats.getDirect().get().intValue());
        Assert.assertEquals(5813, deviceStats.getInfluenced().get().intValue());
    }
}
