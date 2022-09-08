package com.urbanairship.api.push.model;

import com.urbanairship.api.common.parse.APIParsingException;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PushExpiryTest {


    @Test(expected= APIParsingException.class)
    public void testValidateEmpty() {
        PushExpiry.newBuilder().build();
    }

    @Test(expected= APIParsingException.class)
    public void testValidateBoth() {
        PushExpiry.newBuilder()
                .setExpirySeconds(1000)
                .setExpiryTimeStamp(DateTime.now())
                .setExpiryPersonalization("{{ expiry }}")
                .build();
    }


    @Test
    public void testEqualitySeconds() {
        PushExpiry p1 = PushExpiry.newBuilder().setExpirySeconds(100).build();
        PushExpiry p2 = PushExpiry.newBuilder().setExpirySeconds(100).build();
        assertEquals(p1, p2);
        assertEquals(p2, p1);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    public void testEqualityTimeStamp() {
        DateTime dt = DateTime.now();
        PushExpiry p1 = PushExpiry.newBuilder().setExpiryTimeStamp(dt).build();
        PushExpiry p2 = PushExpiry.newBuilder().setExpiryTimeStamp(dt).build();
        assertEquals(p1, p2);
        assertEquals(p2, p1);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    public void testInequality() {
        PushExpiry p1 = PushExpiry.newBuilder().setExpirySeconds(100).build();
        PushExpiry p2 = PushExpiry.newBuilder().setExpiryTimeStamp(DateTime.now()).build();
        assertNotEquals(p1, p2);
        assertNotEquals(p2, p1);
        assertNotEquals(p1.hashCode(), p2.hashCode());

        p1 = PushExpiry.newBuilder().setExpirySeconds(1000).build();
        p2 = PushExpiry.newBuilder().setExpirySeconds(9999).build();
        assertNotEquals(p1, p2);
        assertNotEquals(p2, p1);
        assertNotEquals(p1.hashCode(), p2.hashCode());

        p1 = PushExpiry.newBuilder().setExpiryTimeStamp(DateTime.now()).build();
        p2 = PushExpiry.newBuilder().setExpiryTimeStamp(DateTime.now().plus(Minutes.THREE)).build();
        assertNotEquals(p1, p2);
        assertNotEquals(p2, p1);
        assertNotEquals(p1.hashCode(), p2.hashCode());
    }

}
