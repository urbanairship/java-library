package com.urbanairship.api.push.parse;

import com.urbanairship.api.push.model.DeviceType;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.util.EnumSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DeviceTypeBasicSerializationTest {

    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testAllDeviceTypeEnumSerialization() throws Exception {
        Set<DeviceType> deviceType = EnumSet.allOf(DeviceType.class);

        String json = mapper.writeValueAsString(deviceType);
        String properJson = "[\"ios\",\"wns\",\"mpns\",\"android\",\"blackberry\",\"adm\"]";

        assertTrue("All deviceTypes serialization failure",json.equals(properJson));
    }
}
