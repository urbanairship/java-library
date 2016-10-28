package com.urbanairship.api.segment.parse;

import com.urbanairship.api.segments.model.SegmentListingView;
import com.urbanairship.api.segments.parse.SegmentObjectMapper;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SegmentListingViewDeserializerTest {
    private static final ObjectMapper mapper = SegmentObjectMapper.getInstance();

    @Test
    public void testSegmentListingViewDeserializer() throws Exception {
        String listItem = "{\n" +
                "        \"creation_date\": 1346248822221,\n" +
                "        \"display_name\": \"segment1\",\n" +
                "        \"id\": \"00c0d899-a595-4c66-9071-bc59374bbe6a\",\n" +
                "        \"modification_date\": 1346248822221\n" +
                "      }";

        SegmentListingView obj = mapper.readValue(listItem, SegmentListingView.class);

        assertNotNull(obj);
        assertEquals(1346248822221L, obj.getCreationDate());
        assertEquals("segment1", obj.getDisplayName());
        assertEquals("00c0d899-a595-4c66-9071-bc59374bbe6a", obj.getSegmentId());
        assertEquals(1346248822221L, obj.getModificationDate());
    }
}
