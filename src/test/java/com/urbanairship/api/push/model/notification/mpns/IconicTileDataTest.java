package com.urbanairship.api.push.model.notification.mpns;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IconicTileDataTest extends MpnsTest {

    @Test(expected = IllegalArgumentException.class)
    public void testEmpty() {
        MPNSIconicTileData.newBuilder()
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidTitleValidation() {
        MPNSIconicTileData.newBuilder()
                .setTitle(valueTooLong)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidWideContent1() {
        MPNSIconicTileData.newBuilder()
                .setWideContent1(valueTooLong)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidWideContent2() {
        MPNSIconicTileData.newBuilder()
                .setWideContent2(valueTooLong)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidWideContent3() {
        MPNSIconicTileData.newBuilder()
                .setWideContent3(valueTooLong)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidColorRandomWord() {
        MPNSIconicTileData.newBuilder()
                .setBackgroundColor("foo")
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidColorBadPrefix() {
        MPNSIconicTileData.newBuilder()
                .setBackgroundColor("#00123456")
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidColorBadHex() {
        MPNSIconicTileData.newBuilder()
                .setBackgroundColor("#FF000000Z")
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidColorTooShort() {
        MPNSIconicTileData.newBuilder()
                .setBackgroundColor("#FF12")
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidColorTooLong() {
        MPNSIconicTileData.newBuilder()
                .setBackgroundColor("#FF1234567890")
                .build();
    }

    @Test
    public void testBuild() {
        MPNSIconicTileData tile = MPNSIconicTileData.newBuilder()
                .setId("/Main")
                .setTitle("Title")
                .setCount(10)
                .setIconImage("/icon")
                .setSmallIconImage("/small")
                .setBackgroundColor("#FF000000")
                .setWideContent1("wc1")
                .setWideContent2("wc2")
                .setWideContent3("wc3")
                .build();

        assertEquals("IconicTile", tile.getTemplate());

        assertTrue(tile.getId().isPresent());
        assertEquals("/Main", tile.getId().get());

        assertTrue(tile.getTitle().isPresent());
        assertEquals("Title", tile.getTitle().get());

        assertTrue(tile.getCount().isPresent());
        assertEquals(10, tile.getCount().get().intValue());

        assertTrue(tile.getIconImage().isPresent());
        assertEquals("/icon", tile.getIconImage().get());

        assertTrue(tile.getSmallIconImage().isPresent());
        assertEquals("/small", tile.getSmallIconImage().get());

        assertTrue(tile.getBackgroundColor().isPresent());
        assertEquals("#FF000000", tile.getBackgroundColor().get());

        assertTrue(tile.getWideContent1().isPresent());
        assertEquals("wc1", tile.getWideContent1().get());

        assertTrue(tile.getWideContent2().isPresent());
        assertEquals("wc2", tile.getWideContent2().get());

        assertTrue(tile.getWideContent3().isPresent());
        assertEquals("wc3", tile.getWideContent3().get());
    }
}
