package com.urbanairship.api.push.model.notification.mpns;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FlipTileDataTest extends MpnsTest {

    @Test(expected = IllegalArgumentException.class)
    public void testEmpty() {
        MPNSFlipTileData.newBuilder()
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidTitle() {
        MPNSFlipTileData.newBuilder()
                .setTitle(valueTooLong)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidBackContent() {
        MPNSFlipTileData.newBuilder()
                .setBackContent(valueTooLong)
                .build();
    }


    @Test(expected = IllegalArgumentException.class)
    public void testInvalidBackTitle() {
        MPNSFlipTileData.newBuilder()
                .setBackTitle(valueTooLong)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidWideBackContent() {
        MPNSFlipTileData.newBuilder()
                .setWideBackContent(valueTooLong)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidBackBackground() {
        MPNSFlipTileData.newBuilder()
                .setBackBackgroundImage(uriTooLong)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidBackground() {
        MPNSFlipTileData.newBuilder()
                .setBackgroundImage(uriTooLong)
                .build();
    }

    @Test
    public void testBuild() {
        MPNSFlipTileData tile = MPNSFlipTileData.newBuilder()
                .setId("/tile")
                .setTitle("T")
                .setCount(99)
                .setBackBackgroundImage("/BBI")
                .setBackContent("BC")
                .setBackgroundImage("/BI")
                .setBackTitle("BT")
                .setSmallBackgroundImage("/SBI")
                .setWideBackBackgroundImage("/WBBI")
                .setWideBackContent("WBC")
                .setWideBackgroundImage("/WBI")
                .build();

        assertTrue(tile.getId().isPresent());
        assertEquals("/tile", tile.getId().get());

        assertTrue(tile.getTitle().isPresent());
        assertEquals("T", tile.getTitle().get());

        assertTrue(tile.getCount().isPresent());
        assertEquals(99, tile.getCount().get().intValue());

        assertTrue(tile.getBackBackgroundImage().isPresent());
        assertEquals("/BBI", tile.getBackBackgroundImage().get());

        assertTrue(tile.getBackContent().isPresent());
        assertEquals("BC", tile.getBackContent().get());

        assertTrue(tile.getBackgroundImage().isPresent());
        assertEquals("/BI", tile.getBackgroundImage().get());

        assertTrue(tile.getBackTitle().isPresent());
        assertEquals("BT", tile.getBackTitle().get());

        assertTrue(tile.getSmallBackgroundImage().isPresent());
        assertEquals("/SBI", tile.getSmallBackgroundImage().get());

        assertTrue(tile.getWideBackBackgroundImage().isPresent());
        assertEquals("/WBBI", tile.getWideBackBackgroundImage().get());

        assertTrue(tile.getWideBackContent().isPresent());
        assertEquals("WBC", tile.getWideBackContent().get());

        assertTrue(tile.getWideBackgroundImage().isPresent());
        assertEquals("/WBI", tile.getWideBackgroundImage().get());
    }
}
