package com.urbanairship.api.push.model.notification.mpns;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class CycleTileDataTest extends MpnsTest {
    @Test(expected=IllegalArgumentException.class)
    public void testInvalidTitleValidation() {
        MPNSCycleTileData.newBuilder()
            .setTitle(valueTooLong)
            .build();
    }

    @Test(expected=IllegalArgumentException.class)
    public void testInvalidImagePath() {
        MPNSCycleTileData.newBuilder()
            .addImage("not/a/path")
            .addImage("/is/a/path")
            .build();
    }

    @Test
    public void testBuild() {
        MPNSCycleTileData tile = MPNSCycleTileData.newBuilder()
            .setId("/tile")
            .setTitle("T")
            .setCount(23)
            .setSmallBackgroundImage("/SBI")
            .addImage("/I1")
            .addImage("/I2")
            .build();

        assertTrue(tile.getId().isPresent());
        assertEquals("/tile", tile.getId().get());

        assertTrue(tile.getTitle().isPresent());
        assertEquals("T", tile.getTitle().get());

        assertTrue(tile.getCount().isPresent());
        assertEquals(23, tile.getCount().get().intValue());

        assertTrue(tile.getSmallBackgroundImage().isPresent());
        assertEquals("/SBI", tile.getSmallBackgroundImage().get());

        assertTrue(tile.getImages().isPresent());
        assertEquals(2, tile.getImages().get().size());
    }
}
