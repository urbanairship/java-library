package com.urbanairship.api.push.model;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class PushExpiryTest {


    @Test
    public void testValidateEmpty() throws Exception {
        PushExpiry.newBuilder().build();
    }

    @Test
    public void testValidateBoth() throws Exception {
        PushExpiry.newBuilder()
                .setExpirySeconds(1000)
                .setExpiryTimeStamp(DateTime.now())
                .build();
    }


    @Test
    public void testEqualitySeconds() {
        PushExpiry p1 = PushExpiry.newBuilder().setExpirySeconds(100).build();
        PushExpiry p2 = PushExpiry.newBuilder().setExpirySeconds(100).build();
        assertTrue(p1.equals(p2));
        assertTrue(p2.equals(p1));
        assertTrue(p1.hashCode() == p2.hashCode());
    }

    @Test
    public void testEqualityTimeStamp() {
        DateTime dt = DateTime.now();
        PushExpiry p1 = PushExpiry.newBuilder().setExpiryTimeStamp(dt).build();
        PushExpiry p2 = PushExpiry.newBuilder().setExpiryTimeStamp(dt).build();
        assertTrue(p1.equals(p2));
        assertTrue(p2.equals(p1));
        assertTrue(p1.hashCode() == p2.hashCode());
    }

    @Test
    public void testInequality() {
        PushExpiry p1 = PushExpiry.newBuilder().setExpirySeconds(100).build();
        PushExpiry p2 = PushExpiry.newBuilder().setExpiryTimeStamp(DateTime.now()).build();
        assertFalse(p1.equals(p2));
        assertFalse(p2.equals(p1));
        assertFalse(p1.hashCode() == p2.hashCode());

        p1 = PushExpiry.newBuilder().setExpirySeconds(1000).build();
        p2 = PushExpiry.newBuilder().setExpirySeconds(9999).build();
        assertFalse(p1.equals(p2));
        assertFalse(p2.equals(p1));
        assertFalse(p1.hashCode() == p2.hashCode());

        p1 = PushExpiry.newBuilder().setExpiryTimeStamp(DateTime.now()).build();
        p2 = PushExpiry.newBuilder().setExpiryTimeStamp(DateTime.now().plus(Minutes.THREE)).build();
        assertFalse(p1.equals(p2));
        assertFalse(p2.equals(p1));
        assertFalse(p1.hashCode() == p2.hashCode());
    }

}
