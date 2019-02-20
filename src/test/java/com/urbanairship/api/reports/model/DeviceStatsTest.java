package com.urbanairship.api.reports.model;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DeviceStatsTest {

    @Test
    public void testDeviceStats() {
        DeviceStats deviceStats = DeviceStats.newBuilder()
                .setDirect(1123)
                .setInfluenced(5813)
                .build();

        assertNotNull(deviceStats);
        assertEquals(1123, deviceStats.getDirect());
        assertEquals(5813, deviceStats.getInfluenced());
    }
}
