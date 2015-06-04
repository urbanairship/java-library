/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.model;

import com.google.common.base.Optional;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class PlatformTypeTest {

    @Test
    public void testAndroidPlatformType() {
        PlatformType obj = PlatformType.find("android").get();
        assertNotNull(obj);
        assertEquals(PlatformType.ANDROID, obj);
        assertEquals("android", obj.getIdentifier());
    }

    @Test
    public void testIOSPlatformType() {
        PlatformType obj = PlatformType.find("ios").get();
        assertNotNull(obj);
        assertEquals(PlatformType.IOS, obj);
        assertEquals("ios", obj.getIdentifier());
    }

    @Test
    public void testAmazonPlatformType() {
        PlatformType obj = PlatformType.find("amazon").get();
        assertNotNull(obj);
        assertEquals(PlatformType.AMAZON, obj);
        assertEquals("amazon", obj.getIdentifier());
    }

    @Test
    public void testAllPlatformType() {
        PlatformType obj = PlatformType.find("all").get();
        assertNotNull(obj);
        assertEquals(PlatformType.ALL, obj);
        assertEquals("all", obj.getIdentifier());
    }

    @Test
    public void testVariantPlatformType() {
        PlatformType obj = PlatformType.find("variant").get();
        assertNotNull(obj);
        assertEquals(PlatformType.VARIANT, obj);
        assertEquals("variant", obj.getIdentifier());
    }

    @Test
    public void testRandomPlatformType() {
        Optional<PlatformType> obj = PlatformType.find("NetflixPhone");
        assertNotNull(obj);
        assertFalse(obj.isPresent());
    }
}
