package com.urbanairship.api.push.parse.notification.wns;

import com.urbanairship.api.push.model.notification.wns.WNSToastData;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ToastDeserializerTest {
    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testDeserialize() throws Exception {
        String json
                = "{"
                + "  \"duration\": \"short\","
                + "  \"binding\": {"
                + "      \"template\": \"ToastText01\","
                + "      \"lang\": \"en-US\""
                + "    }"
                + "}";

        WNSToastData parsed = mapper.readValue(json, WNSToastData.class);
        assertEquals(WNSToastData.Duration.SHORT, parsed.getDuration().get());
        assertEquals("ToastText01", parsed.getBinding().getTemplate());
        assertEquals("en-US", parsed.getBinding().getLang().get());
    }
}
