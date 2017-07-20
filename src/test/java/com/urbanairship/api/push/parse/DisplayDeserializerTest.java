package com.urbanairship.api.push.parse;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.push.model.Display;
import com.urbanairship.api.push.model.Position;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;


public class DisplayDeserializerTest {
    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testDisplayDeserializer() throws Exception {
        String json = "{" +
                "\"primary_color\":\"#ffffff\"," +
                "\"secondary_color\":\"#000000\"," +
                "\"position\":\"top\"," +
                "\"duration\":123456" +
                "}";

        Display display = MAPPER.readValue(json, Display.class);
        assertNotNull(display);
        assertEquals("#ffffff", display.getPrimaryColor().get());
        assertEquals("#000000", display.getSecondaryColor().get());
        assertEquals(Position.TOP, display.getPosition().get());
        assertEquals(Integer.valueOf(123456), display.getDuration().get());
    }
}
