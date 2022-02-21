package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;
import com.urbanairship.api.push.model.DeviceType;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ChannelTypeDeserializerTest {

    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testDeserialize() throws Exception {
        String json = "[\"" + DeviceType.WNS.getIdentifier() + "\"]";

        Set<DeviceType> parsed = mapper.readValue(json, new TypeReference<Set<DeviceType>>() {
        });

        assertEquals(1, parsed.size());
        assertEquals(DeviceType.WNS, Iterables.getOnlyElement(parsed));
    }

    @Test(expected = JsonMappingException.class)
    public void testInvalidPlatform() throws Exception {
        String json = "[\"foo\"]";

        mapper.readValue(json, new TypeReference<Set<DeviceType>>() {
        });
    }

    @Test
    public void testDeserializeAndroid() throws Exception {
        String json = "[\"android\"]";

        Set<DeviceType> parsed = mapper.readValue(json, new TypeReference<Set<DeviceType>>() {
        });

        assertEquals(1, parsed.size());
        assertEquals(DeviceType.ANDROID, Iterables.getOnlyElement(parsed));
    }
}
