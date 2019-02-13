package com.urbanairship.api.reports.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.reports.model.DeviceStats;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class DeviceTypeDeserializerTest {

    private static final ObjectMapper mapper = ReportsObjectMapper.getInstance();

    @Test
    public void testStats() throws Exception {

        String json =
                "{  \n" +
                        "\"direct\":1337,\n" +
                        "\"influenced\":1996\n" +
                        "}";

        DeviceStats obj = mapper.readValue(json, DeviceStats.class);
        assertNotNull(obj);

        System.out.println(obj);

        assertEquals(1337, obj.getDirect());
        assertEquals(1996, obj.getInfluenced());
    }
}
