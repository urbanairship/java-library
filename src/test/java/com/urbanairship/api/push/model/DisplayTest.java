package com.urbanairship.api.push.model;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class DisplayTest {

    @Test
    public void testDisplay() {
        Display display = Display.newBuilder()
                .setPrimaryColor("#ffffff")
                .setSecondaryColor("#000000")
                .setPosition(Position.TOP)
                .setDuration(123456)
                .build();

        assertNotNull(display);
        assertEquals("#ffffff", display.getPrimaryColor().get());
        assertEquals("#000000", display.getSecondaryColor().get());
        assertEquals(Position.TOP, display.getPosition().get());
        assertEquals(Integer.valueOf(123456), display.getDuration().get());
    }
}
