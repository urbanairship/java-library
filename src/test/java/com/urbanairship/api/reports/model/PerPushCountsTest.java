package com.urbanairship.api.reports.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @deprecated Marked to be removed in 2.0.0. Urban Airship stopped recommending use of these endpoints in October 2015,
 * so we are now completing their removal from our libraries.
 */
@Deprecated
public class PerPushCountsTest {

    @Test
    public void testPerPushCounts() {
        PerPushCounts obj = PerPushCounts.newBuilder()
                .setDirectResponses(1)
                .setInfluencedResponses(2)
                .setSends(3)
                .build();

        assertNotNull(obj);
        assertEquals(1, obj.getDirectResponses());
        assertEquals(2, obj.getInfluencedResponses());
        assertEquals(3, obj.getSends());
    }

    @Test
    public void testRichPerPushCounts() {
        RichPerPushCounts obj = RichPerPushCounts.newBuilder()
                .setResponses(1)
                .setSends(2)
                .build();

        assertNotNull(obj);
        assertEquals(1, obj.getResponses());
        assertEquals(2, obj.getSends());
    }
}
