package com.urbanairship.api.customevents.parse;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.customevents.model.CustomEventChannelType;
import com.urbanairship.api.customevents.model.CustomEventOccurred;
import com.urbanairship.api.customevents.model.CustomEventUser;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomEventOccurredSerilaizerTest {

    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testOccurred() throws Exception {
        CustomEventOccurred customEventOccurred = CustomEventOccurred.newBuilder()
                .setOccurred("2016-05-02T02:31:22")
                .build();

        String occurredStringActual = MAPPER.writeValueAsString(customEventOccurred);
        String occurredExpected = "{\"occurred\":\"2016-05-02T09:31:22\"}";
        assertEquals(occurredStringActual, occurredExpected);
    }
}
