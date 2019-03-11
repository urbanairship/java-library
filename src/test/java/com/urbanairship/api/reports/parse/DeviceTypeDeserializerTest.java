package com.urbanairship.api.reports.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.reports.model.DeviceStats;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class DeviceTypeDeserializerTest {
    private static final ObjectMapper mapper = ReportsObjectMapper.getInstance();

    @Test
    public void testDeviceStats() throws Exception {

        String json =
                "{ \n" +
                "   \"direct\":1337,\n" +
                "   \"influenced\":1996\n" +
                "}";

        DeviceStats deviceStats = mapper.readValue(json, DeviceStats.class);
        assertNotNull(deviceStats);

        System.out.println(deviceStats);

        assertEquals(1337, deviceStats.getDirect().get().intValue());
        assertEquals(1996, deviceStats.getInfluenced().get().intValue());
    }
}
