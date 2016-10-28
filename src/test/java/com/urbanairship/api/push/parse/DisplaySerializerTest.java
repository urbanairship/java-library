package com.urbanairship.api.push.parse;


import com.urbanairship.api.push.model.Display;
import com.urbanairship.api.push.model.Position;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class DisplaySerializerTest {

    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testDisplaySerializer() throws Exception {
        String stringJson = "{" +
                "\"primary_color\":\"#ffffff\"," +
                "\"secondary_color\":\"#000000\"," +
                "\"duration\":123456," +
                "\"position\":\"top\"" +
                "}";

        Display display = Display.newBuilder()
                .setPrimaryColor("#ffffff")
                .setSecondaryColor("#000000")
                .setDuration(123456)
                .setPosition(Position.TOP)
                .build();

        String parsedJson = MAPPER.writeValueAsString(display);

        assertEquals(parsedJson, stringJson);
    }
}
