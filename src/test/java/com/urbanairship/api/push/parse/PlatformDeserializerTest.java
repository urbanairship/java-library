package com.urbanairship.api.push.parse;

import com.urbanairship.api.common.parse.*;
import com.google.common.collect.Iterables;
import com.urbanairship.api.push.model.Platform;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;

public class PlatformDeserializerTest {

    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testDeserialize() throws Exception {
        String json = "[\"" + Platform.WNS.getIdentifier() + "\"]";

        Set<Platform> parsed = mapper.readValue(json, new TypeReference<Set<Platform>>() {});

        assertEquals(1, parsed.size());
        assertEquals(Platform.WNS, Iterables.getOnlyElement(parsed));
    }

    @Test(expected = APIParsingException.class)
    public void testInvalidPlatform() throws Exception {
        String json = "[\"foo\"]";

        mapper.readValue(json, new TypeReference<Set<Platform>>() {});
    }

    public void testDeserializeAndroid() throws Exception {
        String json = "[\"android\"]";

        Set<Platform> parsed = mapper.readValue(json, new TypeReference<Set<Platform>>() {});

        assertEquals(1, parsed.size());
        assertEquals(Platform.ANDROID, Iterables.getOnlyElement(parsed));
    }

    public void testDeserializeBlackberry() throws Exception {
        String json = "[\"blackberry\"]";

        Set<Platform> parsed = mapper.readValue(json, new TypeReference<Set<Platform>>() {});

        assertEquals(1, parsed.size());
        assertEquals(Platform.BLACKBERRY, Iterables.getOnlyElement(parsed));
    }

}
