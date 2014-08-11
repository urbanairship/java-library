package com.urbanairship.api.push.model;

import org.junit.Test;
import static org.junit.Assert.*;
import com.google.common.collect.ImmutableSet;

public class PlatformDataTest {

    @Test
    public void testPlatformDataOf() {
        PlatformData data = PlatformData.of(Platform.IOS, Platform.ANDROID, Platform.ADM);
        assertTrue(data.getPlatforms().isPresent());
        assertFalse(data.isAll());
        assertEquals(3, data.getPlatforms().get().size());
        assertTrue(data.getPlatforms().get().contains(Platform.IOS));
        assertTrue(data.getPlatforms().get().contains(Platform.ANDROID));
        assertTrue(data.getPlatforms().get().contains(Platform.ADM));
        assertFalse(data.getPlatforms().get().contains(Platform.WNS));
        assertFalse(data.getPlatforms().get().contains(Platform.MPNS));
        assertFalse(data.getPlatforms().get().contains(Platform.BLACKBERRY));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testPlatformDataValidation() {
        PlatformData.newBuilder()
            .setAll(true)
            .addPlatform(Platform.IOS)
            .build();
    }

    @Test
    public void testEquality() {
        PlatformData d = PlatformData.all();
        assertEquals(d, d);
        assertSame(d, d);
        assertFalse(d.equals(null));
        PlatformData d2 = PlatformData.all();
        assertEquals(d, d2);
        assertNotSame(d, d2);
        assertEquals(PlatformData.of(Platform.IOS, Platform.ANDROID),
                     PlatformData.of(Platform.IOS, Platform.ANDROID));
        assertTrue(! PlatformData.all().equals(PlatformData.of(Platform.ADM)));
    }

    @Test
    public void testBuilder() {
        assertEquals(PlatformData.newBuilder()
                     .addPlatform(Platform.IOS)
                     .addPlatform(Platform.ADM)
                     .addPlatform(Platform.WNS)
                     .build(),
                     PlatformData.newBuilder()
                     .addAllPlatforms(ImmutableSet.of(Platform.IOS,
                                                      Platform.ADM,
                                                      Platform.WNS))
                     .build());
    }

    @Test
    public void testHash() {
        assertEquals(PlatformData.all().hashCode(), PlatformData.newBuilder().setAll(true).build().hashCode());
        assertEquals(PlatformData.of(Platform.IOS).hashCode(), PlatformData.of(Platform.IOS).hashCode());
    }

}
