package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.push.model.InApp;
import com.urbanairship.api.push.model.Position;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class InAppDeserializerTest {
    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testInAppDeserializer() throws Exception {
        String displayJson = "{" +
                "\"primary_color\":\"#ffffff\"," +
                "\"secondary_color\":\"#000000\"," +
                "\"position\":\"top\"," +
                "\"duration\":123456" +
                "}";

        String inAppJson = "{" +
                "\"alert\":\"test alert\"," +
                "\"display_type\":\"banner\"," +
                "\"display\":" + displayJson +
                "}";

        InApp inApp = MAPPER.readValue(inAppJson, InApp.class);
        assertNotNull(inApp);
        assertEquals("test alert", inApp.getAlert());
        assertEquals("banner", inApp.getDisplayType());
        assertEquals("#ffffff", inApp.getDisplay().get().getPrimaryColor().get());
        assertEquals("#000000", inApp.getDisplay().get().getSecondaryColor().get());
        assertEquals(Position.TOP, inApp.getDisplay().get().getPosition().get());
        assertEquals(Integer.valueOf(123456), inApp.getDisplay().get().getDuration().get());
    }
}
