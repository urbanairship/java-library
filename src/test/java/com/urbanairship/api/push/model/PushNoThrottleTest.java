package com.urbanairship.api.push.model;

import com.urbanairship.api.common.parse.APIParsingException;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class PushNoThrottleTest {


    @Test(expected= APIParsingException.class)
    public void testValidateEmpty() throws Exception {
        PushNoThrottle.newBuilder().build();
    }

    @Test
    public void testValidateValid() throws Exception {
        PushNoThrottle.Builder pushNoThrottle = PushNoThrottle.newBuilder();
        pushNoThrottle.setValue(true);
        pushNoThrottle.build();
        pushNoThrottle.setValue(false);
        pushNoThrottle.build();
    }


    @Test
    public void testEquality() {
        PushNoThrottle p1 = PushNoThrottle.newBuilder().setValue(true).build();
        PushNoThrottle p2 = PushNoThrottle.newBuilder().setValue(true).build();
        assertTrue(p1.equals(p2));
        assertTrue(p2.equals(p1));
        assertTrue(p1.hashCode() == p2.hashCode());
    }

    @Test
    public void testInequality() {
        PushNoThrottle p1 = PushNoThrottle.newBuilder().setValue(true).build();
        PushNoThrottle p2 = PushNoThrottle.newBuilder().setValue(false).build();
        assertFalse(p1.equals(p2));
        assertFalse(p2.equals(p1));
        assertFalse(p1.hashCode() == p2.hashCode());

    }

}
