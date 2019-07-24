package com.urbanairship.api.reports.model;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

public class StatisticsResponseTest {

    @Test
    public void testStatisticsResponseBuilder() {
        StatisticsResponse obj = StatisticsResponse.newBuilder()
                .setStartTime(new DateTime(2015, 5, 31, 12, 0, 0, 0))
                .setIosCount(1)
                .setBlackBerryCount(2)
                .setC2dmCount(3)
                .setGcmCount(4)
                .setWindows8Count(5)
                .setWindowsPhone8Count(6)
                .build();

        assertNotNull(obj);
        assertEquals(new DateTime(2015, 5, 31, 12, 0, 0, 0), obj.getStart());
        assertEquals(1, obj.getIosCount());
        assertEquals(2, obj.getBlackBerryCount());
        assertEquals(3, obj.getC2dmCount());
        assertEquals(4, obj.getGcmCount());
        assertEquals(5, obj.getWindows8Count());
        assertEquals(6, obj.getWindowsPhone8Count());
    }

    @Test(expected = NullPointerException.class)
    public void testsInvalidStatisticsResponseBuilder() {
        StatisticsResponse.newBuilder()
                .setIosCount(1)
                .setBlackBerryCount(2)
                .setC2dmCount(3)
                .setGcmCount(4)
                .setWindows8Count(5)
                .setWindowsPhone8Count(6)
                .build();
    }

    @Test
    public void testStatisticsResponseEqualityAndHash() {
        StatisticsResponse obj1 = StatisticsResponse.newBuilder()
                .setStartTime(new DateTime(2015, 5, 31, 12, 0, 0, 0))
                .setIosCount(1)
                .setBlackBerryCount(2)
                .setC2dmCount(3)
                .setGcmCount(4)
                .setWindows8Count(5)
                .setWindowsPhone8Count(6)
                .build();

        StatisticsResponse obj2 = StatisticsResponse.newBuilder()
                .setStartTime(new DateTime(2015, 5, 31, 12, 0, 0, 0))
                .setIosCount(1)
                .setBlackBerryCount(2)
                .setC2dmCount(3)
                .setGcmCount(4)
                .setWindows8Count(5)
                .setWindowsPhone8Count(6)
                .build();

        StatisticsResponse obj3 = StatisticsResponse.newBuilder()
                .setStartTime(new DateTime(2015, 5, 31, 12, 0, 0, 0))
                .setIosCount(1)
                .setBlackBerryCount(2)
                .setC2dmCount(2)
                .setGcmCount(4)
                .setWindows8Count(5)
                .setWindowsPhone8Count(6)
                .build();

        assertTrue(obj1.equals(obj2));
        assertFalse(obj1.equals(obj3));
        assertEquals(obj1.hashCode(), obj2.hashCode());
        assertNotSame(obj2.hashCode(), obj3.hashCode());
    }
}
