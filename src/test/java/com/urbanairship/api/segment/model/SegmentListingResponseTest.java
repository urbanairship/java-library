package com.urbanairship.api.segment.model;

import com.google.common.collect.ImmutableList;
import com.urbanairship.api.segments.model.SegmentListingResponse;
import com.urbanairship.api.segments.model.SegmentListingView;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class SegmentListingResponseTest {

    @Test
    public void testSegmentListingResponse() {
        SegmentListingView seg1 = SegmentListingView.newBuilder()
                .setDisplayName("segment1")
                .setId("id1")
                .setCreationDate(1)
                .setModificationDate(6)
                .build();

        SegmentListingView seg2 = SegmentListingView.newBuilder()
                .setDisplayName("segment2")
                .setId("id2")
                .setCreationDate(2)
                .setModificationDate(7)
                .build();

        SegmentListingView seg3 = SegmentListingView.newBuilder()
                .setDisplayName("segment3")
                .setId("id3")
                .setCreationDate(3)
                .setModificationDate(8)
                .build();

        SegmentListingView seg4 = SegmentListingView.newBuilder()
                .setDisplayName("segment4")
                .setId("id4")
                .setCreationDate(4)
                .setModificationDate(9)
                .build();

        SegmentListingView seg5 = SegmentListingView.newBuilder()
                .setDisplayName("segment5")
                .setId("id5")
                .setCreationDate(5)
                .setModificationDate(10)
                .build();

        ImmutableList<SegmentListingView> segments = ImmutableList.of(seg1, seg2, seg3, seg4, seg5);

        SegmentListingResponse response = SegmentListingResponse.newBuilder()
                .setNextPage("next_page")
                .addAllSegmentObjects(segments)
                .build();

        assertNotNull(response);
        assertEquals(response.getSegmentListingViews().size(), 5);

        assertEquals(response.getSegmentListingViews().get(0).getDisplayName(), "segment1");
        assertEquals(response.getSegmentListingViews().get(0).getSegmentId(), "id1");
        assertEquals(response.getSegmentListingViews().get(0).getCreationDate(), 1);
        assertEquals(response.getSegmentListingViews().get(0).getModificationDate(), 6);

        assertEquals(response.getSegmentListingViews().get(1).getDisplayName(), "segment2");
        assertEquals(response.getSegmentListingViews().get(1).getSegmentId(), "id2");
        assertEquals(response.getSegmentListingViews().get(1).getCreationDate(), 2);
        assertEquals(response.getSegmentListingViews().get(1).getModificationDate(), 7);

        assertEquals(response.getSegmentListingViews().get(2).getDisplayName(), "segment3");
        assertEquals(response.getSegmentListingViews().get(2).getSegmentId(), "id3");
        assertEquals(response.getSegmentListingViews().get(2).getCreationDate(), 3);
        assertEquals(response.getSegmentListingViews().get(2).getModificationDate(), 8);

        assertEquals(response.getSegmentListingViews().get(3).getDisplayName(), "segment4");
        assertEquals(response.getSegmentListingViews().get(3).getSegmentId(), "id4");
        assertEquals(response.getSegmentListingViews().get(3).getCreationDate(), 4);
        assertEquals(response.getSegmentListingViews().get(3).getModificationDate(), 9);

        assertEquals(response.getSegmentListingViews().get(4).getDisplayName(), "segment5");
        assertEquals(response.getSegmentListingViews().get(4).getSegmentId(), "id5");
        assertEquals(response.getSegmentListingViews().get(4).getCreationDate(), 5);
        assertEquals(response.getSegmentListingViews().get(4).getModificationDate(), 10);
    }
}
