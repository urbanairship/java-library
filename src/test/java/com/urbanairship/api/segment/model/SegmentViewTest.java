package com.urbanairship.api.segment.model;

import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.segments.model.SegmentView;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SegmentViewTest {

    @Test
    public void testSegmentView() {
        String displayName = "test_segment";

        Selector andSelector = Selectors.tags("java", "lib");
        Selector criteria = Selectors.or(andSelector, Selectors.tag("#enterprise"));

        SegmentView obj = SegmentView.newBuilder()
                .setCriteria(criteria)
                .setDisplayName(displayName)
                .build();

        assertNotNull(obj);
        assertEquals(criteria, obj.getCriteria());
        assertEquals(displayName, obj.getDisplayName());
    }
}
