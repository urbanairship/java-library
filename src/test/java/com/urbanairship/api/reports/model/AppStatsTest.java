package com.urbanairship.api.reports.model;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.*;

public class AppStatsTest {

    @Test
    public void testAppStatsBuilder() {
        AppStats obj = AppStats.newBuilder()
                .setStartTime(new DateTime(2015, 5, 31, 12, 0, 0, 0))
                .setIOSCount(1)
                .setBlackBerryCount(2)
                .setC2DMCount(3)
                .setGCMCount(4)
                .setWindows8Count(5)
                .setWindowsPhone8Count(6)
                .build();

        assertNotNull(obj);
        assertEquals(new DateTime(2015, 5, 31, 12, 0, 0, 0), obj.getStart());
        assertEquals(1, obj.getiOSCount());
        assertEquals(2, obj.getBlackBerryCount());
        assertEquals(3, obj.getC2DMCount());
        assertEquals(4, obj.getGCMCount());
        assertEquals(5, obj.getWindows8Count());
        assertEquals(6, obj.getWindowsPhone8Count());
    }

    @Test(expected = NullPointerException.class)
    public void testInvalidAppStatsBuilder() {
        AppStats.newBuilder()
                .setIOSCount(1)
                .setBlackBerryCount(2)
                .setC2DMCount(3)
                .setGCMCount(4)
                .setWindows8Count(5)
                .setWindowsPhone8Count(6)
                .build();
    }

    @Test
    public void testAppStatsEqualityandHash() {
        AppStats obj1 = AppStats.newBuilder()
                .setStartTime(new DateTime(2015, 5, 31, 12, 0, 0, 0))
                .setIOSCount(1)
                .setBlackBerryCount(2)
                .setC2DMCount(3)
                .setGCMCount(4)
                .setWindows8Count(5)
                .setWindowsPhone8Count(6)
                .build();

        AppStats obj2 = AppStats.newBuilder()
                .setStartTime(new DateTime(2015, 5, 31, 12, 0, 0, 0))
                .setIOSCount(1)
                .setBlackBerryCount(2)
                .setC2DMCount(3)
                .setGCMCount(4)
                .setWindows8Count(5)
                .setWindowsPhone8Count(6)
                .build();

        AppStats obj3 = AppStats.newBuilder()
                .setStartTime(new DateTime(2015, 5, 31, 12, 0, 0, 0))
                .setIOSCount(1)
                .setBlackBerryCount(2)
                .setC2DMCount(2)
                .setGCMCount(4)
                .setWindows8Count(5)
                .setWindowsPhone8Count(6)
                .build();

        assertTrue(obj1.equals(obj2));
        assertFalse(obj1.equals(obj3));
        assertEquals(obj1.hashCode(), obj2.hashCode());
        assertNotSame(obj2.hashCode(), obj3.hashCode());
    }
}
