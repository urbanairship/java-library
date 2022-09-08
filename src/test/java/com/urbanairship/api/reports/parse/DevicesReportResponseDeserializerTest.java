package com.urbanairship.api.reports.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.reports.model.DevicesReportResponse;
import org.junit.Test;

import java.io.IOException;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DevicesReportResponseDeserializerTest {
    private static final ObjectMapper mapper = ReportsObjectMapper.getInstance();

    @Test
    public void testDevicesReportResponseDeserializer() throws IOException {

        String json =
                "{\n" +
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
                        "}";

        DevicesReportResponse devicesReportResponse = mapper.readValue(json, DevicesReportResponse.class);
        assertNotNull(devicesReportResponse);

        assertEquals(142, Objects.requireNonNull(devicesReportResponse.getDeviceTypeStatsMap().get().get("ios")).getOptedIn().get().intValue());
        assertEquals(89, Objects.requireNonNull(devicesReportResponse.getDeviceTypeStatsMap().get().get("ios")).getOptedOut().get().intValue());
        assertEquals(231, Objects.requireNonNull(devicesReportResponse.getDeviceTypeStatsMap().get().get("ios")).getUniqueDevices().get().intValue());
        assertEquals(2096, Objects.requireNonNull(devicesReportResponse.getDeviceTypeStatsMap().get().get("ios")).getUninstalled().get().intValue());
        assertEquals(226, Objects.requireNonNull(devicesReportResponse.getDeviceTypeStatsMap().get().get("android")).getOptedIn().get().intValue());
        assertEquals(11569, Objects.requireNonNull(devicesReportResponse.getDeviceTypeStatsMap().get().get("android")).getOptedOut().get().intValue());
        assertEquals(11795, Objects.requireNonNull(devicesReportResponse.getDeviceTypeStatsMap().get().get("android")).getUniqueDevices().get().intValue());
        assertEquals(1069, Objects.requireNonNull(devicesReportResponse.getDeviceTypeStatsMap().get().get("android")).getUninstalled().get().intValue());
        assertEquals(22, Objects.requireNonNull(devicesReportResponse.getDeviceTypeStatsMap().get().get("amazon")).getOptedIn().get().intValue());
        assertEquals(7, Objects.requireNonNull(devicesReportResponse.getDeviceTypeStatsMap().get().get("amazon")).getOptedOut().get().intValue());
        assertEquals(29, Objects.requireNonNull(devicesReportResponse.getDeviceTypeStatsMap().get().get("amazon")).getUniqueDevices().get().intValue());
        assertEquals(9, Objects.requireNonNull(devicesReportResponse.getDeviceTypeStatsMap().get().get("amazon")).getUninstalled().get().intValue());
        assertEquals(23, Objects.requireNonNull(devicesReportResponse.getDeviceTypeStatsMap().get().get("sms")).getOptedIn().get().intValue());
        assertEquals(3, Objects.requireNonNull(devicesReportResponse.getDeviceTypeStatsMap().get().get("sms")).getOptedOut().get().intValue());
        assertEquals(26, Objects.requireNonNull(devicesReportResponse.getDeviceTypeStatsMap().get().get("sms")).getUniqueDevices().get().intValue());
        assertEquals(17, Objects.requireNonNull(devicesReportResponse.getDeviceTypeStatsMap().get().get("sms")).getUninstalled().get().intValue());
    }
}