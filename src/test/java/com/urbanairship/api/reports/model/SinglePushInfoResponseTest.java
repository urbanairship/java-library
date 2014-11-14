/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.model;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class SinglePushInfoResponseTest {

    @Test
    public void testSinglePushInfoResponseBuilder() {

        UUID one = UUID.randomUUID();
        UUID two = UUID.randomUUID();

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dt = formatter.parseDateTime("2013-07-31 21:27:38");

        SinglePushInfoResponse obj = SinglePushInfoResponse.newBuilder()
                .setPushUUID(one)
                .setDirectResponses(4)
                .setSends(5)
                .setPushType(SinglePushInfoResponse.PushType.UNICAST_PUSH)
                .setPushTime("2013-07-31 21:27:38")
                .setGroupID(two)
                .build();

        assertNotNull(obj);
        assertEquals(one, obj.getPushUUID());
        assertEquals(4, obj.getDirectResponses());
        assertEquals(5, obj.getSends());
        assertEquals(SinglePushInfoResponse.PushType.UNICAST_PUSH, obj.getPushType());
        assertEquals(dt, obj.getPushTime());
        assertTrue(obj.getGroupID().isPresent());
        assertEquals(two, obj.getGroupID().get());

    }

    @Test
    public void testSinglePushInfoResponseBuilderNoGroupID() {

        UUID one = UUID.randomUUID();

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dt = formatter.parseDateTime("2013-07-31 21:27:38");

        SinglePushInfoResponse obj = SinglePushInfoResponse.newBuilder()
                .setPushUUID(one)
                .setSends(5)
                .setPushType(SinglePushInfoResponse.PushType.UNICAST_PUSH)
                .setPushTime("2013-07-31 21:27:38")
                .build();

        assertNotNull(obj);
        assertEquals(one, obj.getPushUUID());
        assertEquals(5, obj.getSends());
        assertEquals(SinglePushInfoResponse.PushType.UNICAST_PUSH, obj.getPushType());
        assertEquals(dt, obj.getPushTime());
        assertFalse(obj.getGroupID().isPresent());

    }
}
