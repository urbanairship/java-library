package com.urbanairship.api.segment.model;

import com.urbanairship.api.segments.model.SegmentListingView;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SegmentListingViewTest {

    @Test
    public void testSegmentListingView() {
        SegmentListingView obj = SegmentListingView.newBuilder()
                .setDisplayName("Segment")
                .setId("id_abc_123")
                .setCreationDate(12345)
                .setModificationDate(678910)
                .build();

        assertNotNull(obj);
        assertEquals("Segment", obj.getDisplayName());
        assertEquals("id_abc_123", obj.getSegmentId());
        assertEquals(12345, obj.getCreationDate());
        assertEquals(678910, obj.getModificationDate());
    }
}