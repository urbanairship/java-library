package com.urbanairship.api.reports.model;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DevicesReportTest {

    @Test
    public void testDevicesReportResponse() {
        DateTime date = new DateTime(2019, 03, 1, 12, 0, 0, 0);

        DeviceTypeStats deviceTypeStats = DeviceTypeStats.newBuilder()
                .setUniqueDevices(1)
                .setUninstalled(2)
                .setOptedOut(3)
                .setOptedIn(4)
                .build();

        DevicesReportResponse devicesReportResponse = DevicesReportResponse.newBuilder()
                .addDeviceTypeStatsMapping("ios", deviceTypeStats)
                .addDeviceTypeStatsMapping("sms", deviceTypeStats)
                .build();

        DevicesReport devicesReport = DevicesReport.newBuilder()
                .setTotalUniqueDevices(3)
                .setDateComputed(date.toString())
                .setDateClosed(date.toString())
                .addDevicesReportResponseObject(devicesReportResponse)
                .build();

        assertNotNull(devicesReport);
        assertEquals(1, devicesReport.getCounts().get().size());
        assertEquals(2, devicesReport.getCounts().get().get(0).getDeviceTypeStatsMap().get().size());
        assertEquals(date.toString(), devicesReport.getDateClosed().get());
        assertEquals(date.toString(), devicesReport.getDateComputed().get());
        assertEquals(3, devicesReport.getTotalUniqueDevices().get().intValue());
        assertEquals(1, devicesReport.getCounts().get().get(0).getDeviceTypeStatsMap().get().get("ios").getUniqueDevices().get().intValue());
        assertEquals(2, devicesReport.getCounts().get().get(0).getDeviceTypeStatsMap().get().get("ios").getUninstalled().get().intValue());
        assertEquals(3, devicesReport.getCounts().get().get(0).getDeviceTypeStatsMap().get().get("sms").getOptedOut().get().intValue());
        assertEquals(4, devicesReport.getCounts().get().get(0).getDeviceTypeStatsMap().get().get("sms").getOptedIn().get().intValue());
    }
}