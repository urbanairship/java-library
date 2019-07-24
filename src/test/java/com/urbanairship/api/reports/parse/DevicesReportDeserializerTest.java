package com.urbanairship.api.reports.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.reports.model.DevicesReport;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DevicesReportDeserializerTest {
    private static final ObjectMapper mapper = ReportsObjectMapper.getInstance();

    @Test
    public void testDevicesReportDeserializer() throws IOException {

        String json =
                "{\n" +
                        "   \"total_unique_devices\": 13186,\n" +
                        "   \"date_closed\": \"2018-08-28 00:00:00\",\n" +
                        "   \"date_computed\": \"2018-08-29 13:30:45\",\n" +
                        "   \"counts\": {\n" +
                        "       \"ios\": {\n" +
                        "           \"unique_devices\": 231,\n" +
                        "           \"opted_in\": 142,\n" +
                        "           \"opted_out\": 89,\n" +
                        "           \"uninstalled\": 2096\n" +
                        "       },\n" +
                        "       \"android\": { \n" +
                        "           \"unique_devices\": 11795, \n" +
                        "           \"opted_in\": 226, \n" +
                        "           \"opted_out\": 11569, \n" +
                        "           \"uninstalled\": 1069 \n" +
                        "       }, \n" +
                        "       \"amazon\": { \n" +
                        "           \"unique_devices\": 29, \n" +
                        "           \"opted_in\": 22, \n" +
                        "           \"opted_out\": 7, \n" +
                        "           \"uninstalled\": 9 \n" +
                        "       }, \n" +
                        "       \"sms\": { \n" +
                        "           \"unique_devices\": 26, \n" +
                        "           \"opted_in\": 23, \n" +
                        "           \"opted_out\": 3, \n" +
                        "           \"uninstalled\": 17 \n" +
                        "       } \n" +
                        "   } \n" +
                        "}";

        DevicesReport devicesReport = mapper.readValue(json, DevicesReport.class);
        assertNotNull(devicesReport);

        assertEquals(13186, devicesReport.getTotalUniqueDevices().get().intValue());
        assertEquals("2018-08-28 00:00:00", devicesReport.getDateClosed().get());
        assertEquals("2018-08-29 13:30:45", devicesReport.getDateComputed().get());
        assertEquals(142, devicesReport.getCounts().get().get(0).getDeviceTypeStatsMap().get().get("ios").getOptedIn().get().intValue());
        assertEquals(89, devicesReport.getCounts().get().get(0).getDeviceTypeStatsMap().get().get("ios").getOptedOut().get().intValue());
        assertEquals(231, devicesReport.getCounts().get().get(0).getDeviceTypeStatsMap().get().get("ios").getUniqueDevices().get().intValue());
        assertEquals(2096, devicesReport.getCounts().get().get(0).getDeviceTypeStatsMap().get().get("ios").getUninstalled().get().intValue());
        assertEquals(226, devicesReport.getCounts().get().get(0).getDeviceTypeStatsMap().get().get("android").getOptedIn().get().intValue());
        assertEquals(11569, devicesReport.getCounts().get().get(0).getDeviceTypeStatsMap().get().get("android").getOptedOut().get().intValue());
        assertEquals(11795, devicesReport.getCounts().get().get(0).getDeviceTypeStatsMap().get().get("android").getUniqueDevices().get().intValue());
        assertEquals(1069, devicesReport.getCounts().get().get(0).getDeviceTypeStatsMap().get().get("android").getUninstalled().get().intValue());
        assertEquals(22, devicesReport.getCounts().get().get(0).getDeviceTypeStatsMap().get().get("amazon").getOptedIn().get().intValue());
        assertEquals(7, devicesReport.getCounts().get().get(0).getDeviceTypeStatsMap().get().get("amazon").getOptedOut().get().intValue());
        assertEquals(29, devicesReport.getCounts().get().get(0).getDeviceTypeStatsMap().get().get("amazon").getUniqueDevices().get().intValue());
        assertEquals(9, devicesReport.getCounts().get().get(0).getDeviceTypeStatsMap().get().get("amazon").getUninstalled().get().intValue());
        assertEquals(23, devicesReport.getCounts().get().get(0).getDeviceTypeStatsMap().get().get("sms").getOptedIn().get().intValue());
        assertEquals(3, devicesReport.getCounts().get().get(0).getDeviceTypeStatsMap().get().get("sms").getOptedOut().get().intValue());
        assertEquals(26, devicesReport.getCounts().get().get(0).getDeviceTypeStatsMap().get().get("sms").getUniqueDevices().get().intValue());
        assertEquals(17, devicesReport.getCounts().get().get(0).getDeviceTypeStatsMap().get().get("sms").getUninstalled().get().intValue());
    }
}