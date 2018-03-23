package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.push.model.DeviceType;
import org.junit.Test;

import java.util.EnumSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ChannelTypeBasicSerializationTest {

    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testRoundTrip() throws Exception {
        Set<DeviceType> deviceType = DeviceType.TYPES;

        String json = mapper.writeValueAsString(deviceType);
        Set<DeviceType> parsed = mapper.readValue(json, new TypeReference<Set<DeviceType>>() {
        });

        assertEquals(deviceType, parsed);
    }
}
