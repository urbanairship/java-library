package com.urbanairship.api.push.model.notification.mpns;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class MPNSPushTest extends MpnsTest {

    @Test(expected = IllegalArgumentException.class)
    public void testEmpty() {
        MPNSPush.newBuilder()
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTileMissing() {
        MPNSPush.newBuilder()
                .setType(MPNSPush.Type.TILE)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToastMissing() {
        MPNSPush.newBuilder()
                .setType(MPNSPush.Type.TOAST)
                .build();
    }

    @Test
    public void testTile() {
        MPNSIconicTileData tile = MPNSIconicTileData.newBuilder()
                .setTitle("T")
                .setCount(10)
                .build();
        MPNSPush push = MPNSPush.newBuilder()
                .setType(MPNSPush.Type.TILE)
                .setTile(tile)
                .build();
        assertEquals(MPNSPush.Type.TILE, push.getType());
        assertTrue(push.getTile().isPresent());
        assertFalse(push.getToast().isPresent());
        assertSame(tile, push.getTile().get());
        assertEquals("T", push.getTile().get().getTitle().get());
    }

    @Test
    public void testBatchingInterval() {
        MPNSIconicTileData tile = MPNSIconicTileData.newBuilder()
                .setTitle("T")
                .build();
        MPNSPush push = MPNSPush.newBuilder()
                .setType(MPNSPush.Type.TILE)
                .setBatchingInterval(MPNSPush.BatchingInterval.SHORT)
                .setTile(tile)
                .build();
        assertEquals(MPNSPush.BatchingInterval.SHORT, push.getBatchingInterval());
    }
}
