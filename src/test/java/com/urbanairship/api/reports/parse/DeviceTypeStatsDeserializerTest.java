package com.urbanairship.api.reports.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.reports.model.DeviceTypeStats;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DeviceTypeStatsDeserializerTest {
    private static final ObjectMapper mapper = ReportsObjectMapper.getInstance();

    @Test
    public void testDeviceTypeStatDeserializer() throws IOException {

        String json =
                "{\n" +
                        "\"unique_devices\": 231,\n" +
                        "\"opted_in\": 142,\n" +
                        "\"opted_out\": 89,\n" +
                        "\"uninstalled\": 2096\n" +
                        "}";

        DeviceTypeStats deviceTypeStats = mapper.readValue(json, DeviceTypeStats.class);
        assertNotNull(deviceTypeStats);

        assertEquals(142, deviceTypeStats.getOptedIn().get().intValue());
        assertEquals(89, deviceTypeStats.getOptedOut().get().intValue());
        assertEquals(231, deviceTypeStats.getUniqueDevices().get().intValue());
        assertEquals(2096, deviceTypeStats.getUninstalled().get().intValue());
    }

}