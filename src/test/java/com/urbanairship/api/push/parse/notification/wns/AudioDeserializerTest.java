package com.urbanairship.api.push.parse.notification.wns;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.push.model.notification.wns.WNSAudioData;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AudioDeserializerTest {
    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testDeserialize() throws Exception {
        String json =
                "{" +
                        "\"sound\": \"mail\"," +
                        "\"loop\": false" +
                        "}";

        WNSAudioData parsed = mapper.readValue(json, WNSAudioData.class);
        assertEquals(WNSAudioData.Sound.MAIL, parsed.getSound());
        assertEquals(false, parsed.getLoop().get());
    }
}
