package com.urbanairship.api.reports.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DevicesReportResponseTest {

    @Test
    public void testDevicesReportResponse() {

        DeviceTypeStats deviceTypeStats = DeviceTypeStats.newBuilder()
                .setUniqueDevices(1)
                .setUninstalled(2)
                .setOptedOut(3)
                .setOptedIn(4)
                .build();

        DevicesReportResponse devicesReportResponse= DevicesReportResponse.newBuilder()
                .addDeviceTypeStatsMapping("ios", deviceTypeStats)
                .addDeviceTypeStatsMapping("sms", deviceTypeStats)
                .build();

        assertNotNull(devicesReportResponse);
        assertEquals(2, devicesReportResponse.getDeviceTypeStatsMap().get().size());
        assertEquals(1, devicesReportResponse.getDeviceTypeStatsMap().get().get("ios").getUniqueDevices().get().intValue());
        assertEquals(2, devicesReportResponse.getDeviceTypeStatsMap().get().get("ios").getUninstalled().get().intValue());
        assertEquals(3, devicesReportResponse.getDeviceTypeStatsMap().get().get("sms").getOptedOut().get().intValue());
        assertEquals(4, devicesReportResponse.getDeviceTypeStatsMap().get().get("sms").getOptedIn().get().intValue());
    }

}