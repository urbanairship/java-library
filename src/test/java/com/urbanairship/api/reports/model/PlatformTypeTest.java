package com.urbanairship.api.reports.model;

import java.util.Optional;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * @deprecated Marked to be removed in 2.0.0. Urban Airship stopped recommending use of these endpoints in October 2015,
 * so we are now completing their removal from our libraries.
 */
@Deprecated
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
    public void testWebPlatformType() {
        PlatformType obj = PlatformType.find("web").get();
        assertNotNull(obj);
        assertEquals(PlatformType.WEB, obj);
        assertEquals("web", obj.getIdentifier());
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
