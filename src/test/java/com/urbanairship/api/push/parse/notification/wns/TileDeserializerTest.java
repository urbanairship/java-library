package com.urbanairship.api.push.parse.notification.wns;

import com.urbanairship.api.push.model.notification.wns.WNSTileData;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TileDeserializerTest {
    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testDeserialize() throws Exception {
        String json
                = "{"
                + "  \"binding\": ["
                + "      {"
                + "        \"template\": \"TileText01\","
                + "        \"lang\": \"en-US\""
                + "      },"
                + "      {"
                + "        \"template\": \"TileText02\","
                + "        \"lang\": \"en-UK\""
                + "      }"
                + "    ]"
                + "}";

        WNSTileData parsed = mapper.readValue(json, WNSTileData.class);

        assertEquals(2, parsed.getBindingCount());

        assertEquals("TileText01", parsed.getBinding(0).getTemplate());
        assertEquals("TileText02", parsed.getBinding(1).getTemplate());
        assertEquals("en-US", parsed.getBinding(0).getLang().get());
        assertEquals("en-UK", parsed.getBinding(1).getLang().get());
    }
}
