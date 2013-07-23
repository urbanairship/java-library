package com.urbanairship.api.push.parse;

import com.urbanairship.api.push.model.Platform;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import java.util.EnumSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlatformBasicSerializationTest {

    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testAllPlatformEnumSerialization() throws Exception {
        Set<Platform> platform = EnumSet.allOf(Platform.class);

        String json = mapper.writeValueAsString(platform);
        String properJson = "[\"ios\",\"wns\",\"mpns\",\"android\",\"blackberry\",\"adm\"]";

        assertTrue("All platforms serialization failure",json.equals(properJson));
    }
}
