package com.urbanairship.api.push.parse;


import com.urbanairship.api.push.model.Display;
import com.urbanairship.api.push.model.InApp;
import com.urbanairship.api.push.model.Position;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InAppSerializerTest {
    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testInAppSerializer() throws Exception {
        String displayJson = "{" +
                "\"primary_color\":\"#ffffff\"," +
                "\"secondary_color\":\"#000000\"," +
                "\"duration\":123456," +
                "\"position\":\"top\"" +
                "}";

        String stringJson = "{" +
                "\"alert\":\"test alert\"," +
                "\"display_type\":\"banner\"," +
                "\"display\":" + displayJson +
                "}";

        Display display = Display.newBuilder()
                .setPrimaryColor("#ffffff")
                .setSecondaryColor("#000000")
                .setDuration(123456)
                .setPosition(Position.TOP)
                .build();

        InApp inApp = InApp.newBuilder()
                .setAlert("test alert")
                .setDisplay(display)
                .build();

        String parsedJson = MAPPER.writeValueAsString(inApp);
        assertEquals(stringJson,parsedJson);
    }
}
