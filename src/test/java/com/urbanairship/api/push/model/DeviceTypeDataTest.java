package com.urbanairship.api.push.model;

import org.junit.Test;
import static org.junit.Assert.*;
import com.google.common.collect.ImmutableSet;

public class DeviceTypeDataTest {

    @Test
    public void testPlatformDataOf() {
        DeviceTypeData data = DeviceTypeData.of(DeviceType.IOS, DeviceType.ANDROID, DeviceType.ADM);
        assertTrue(data.getPlatforms().isPresent());
        assertFalse(data.isAll());
        assertEquals(3, data.getPlatforms().get().size());
        assertTrue(data.getPlatforms().get().contains(DeviceType.IOS));
        assertTrue(data.getPlatforms().get().contains(DeviceType.ANDROID));
        assertTrue(data.getPlatforms().get().contains(DeviceType.ADM));
        assertFalse(data.getPlatforms().get().contains(DeviceType.WNS));
        assertFalse(data.getPlatforms().get().contains(DeviceType.MPNS));
        assertFalse(data.getPlatforms().get().contains(DeviceType.BLACKBERRY));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testPlatformDataValidation() {
        DeviceTypeData.newBuilder()
            .setAll(true)
            .addPlatform(DeviceType.IOS)
            .build();
    }

    @Test
    public void testEquality() {
        DeviceTypeData d = DeviceTypeData.all();
        assertEquals(d, d);
        assertSame(d, d);
        assertFalse(d.equals(null));
        DeviceTypeData d2 = DeviceTypeData.all();
        assertEquals(d, d2);
        assertNotSame(d, d2);
        assertEquals(DeviceTypeData.of(DeviceType.IOS, DeviceType.ANDROID),
                     DeviceTypeData.of(DeviceType.IOS, DeviceType.ANDROID));
        assertTrue(! DeviceTypeData.all().equals(DeviceTypeData.of(DeviceType.ADM)));
    }

    @Test
    public void testBuilder() {
        assertEquals(DeviceTypeData.newBuilder()
                     .addPlatform(DeviceType.IOS)
                     .addPlatform(DeviceType.ADM)
                     .addPlatform(DeviceType.WNS)
                     .build(),
                     DeviceTypeData.newBuilder()
                     .addAllPlatforms(ImmutableSet.of(DeviceType.IOS,
                                                      DeviceType.ADM,
                                                      DeviceType.WNS))
                     .build());
    }

    @Test
    public void testHash() {
        assertEquals(DeviceTypeData.all().hashCode(), DeviceTypeData.newBuilder().setAll(true).build().hashCode());
        assertEquals(DeviceTypeData.of(DeviceType.IOS).hashCode(), DeviceTypeData.of(DeviceType.IOS).hashCode());
    }

}
