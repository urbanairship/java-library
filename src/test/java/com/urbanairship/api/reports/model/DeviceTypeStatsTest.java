package com.urbanairship.api.reports.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DeviceTypeStatsTest {

    @Test
    public void testDeviceTypeStats() {
        DeviceTypeStats deviceTypeStats = DeviceTypeStats.newBuilder()
                .setOptedIn(1)
                .setOptedOut(2)
                .setUninstalled(3)
                .setUniqueDevices(4)
                .build();

        assertNotNull(deviceTypeStats);
        assertEquals(1, deviceTypeStats.getOptedIn().get().intValue());
        assertEquals(2, deviceTypeStats.getOptedOut().get().intValue());
        assertEquals(3, deviceTypeStats.getUninstalled().get().intValue());
        assertEquals(4, deviceTypeStats.getUniqueDevices().get().intValue());
    }
}