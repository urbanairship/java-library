package com.urbanairship.api.attributelists.model;

import com.google.common.base.Optional;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AttributeListsListingResponseTest {

    @Test
    public void testAttributeListsListingResponse() {
        DateTime created = new DateTime(2014, 10, 1, 12, 0, 0, 0);
        DateTime updated = created.plus(Period.hours(48));

        AttributeListsView res1 = AttributeListsView.newBuilder()
                .setName("ua_attributes_list_name")
                .setCreated(created)
                .setLastUpdated(updated)
                .setChannelCount(1234)
                .setErrorPath("https://go.urbanairship.com/api/attribute-lists/ua_attributes_my_list/errors")
                .setStatus("ready")
                .build();

        AttributeListsView res2 = AttributeListsView.newBuilder()
                .setName("ua_attributes_list_name")
                .setDescription("a great list")
                .setCreated(created)
                .setLastUpdated(updated)
                .setChannelCount(1234)
                .setErrorPath("https://go.urbanairship.com/api/attribute-lists/ua_attributes_my_list2/errors")
                .setStatus("processing")
                .build();

        AttributeListsListingResponse response = AttributeListsListingResponse.newBuilder()
                .setOk(true)
                .addAttributeLists(res1)
                .addAttributeLists(res2)
                .build();

        assertNotNull(response);
        assertEquals(true, response.getOk());
        assertEquals("ua_attributes_list_name", response.getAttributeListsViews().get(0).getName());
        assertEquals(created, response.getAttributeListsViews().get(0).getCreated());
        assertEquals(Integer.valueOf(1234), response.getAttributeListsViews().get(0).getChannelCount());
        assertEquals("ready", response.getAttributeListsViews().get(0).getStatus());
        assertEquals("ua_attributes_list_name", response.getAttributeListsViews().get(1).getName());
        assertEquals(Optional.of("a great list"), response.getAttributeListsViews().get(1).getDescription());
        assertEquals(created, response.getAttributeListsViews().get(1).getCreated());
        assertEquals(updated, response.getAttributeListsViews().get(1).getLastUpdated());
        assertEquals(Integer.valueOf(1234), response.getAttributeListsViews().get(1).getChannelCount());
        assertEquals("https://go.urbanairship.com/api/attribute-lists/ua_attributes_my_list/errors", response.getAttributeListsViews().get(0).getErrorPath());
        assertEquals("https://go.urbanairship.com/api/attribute-lists/ua_attributes_my_list2/errors", response.getAttributeListsViews().get(1).getErrorPath());
        assertEquals("processing", response.getAttributeListsViews().get(1).getStatus());
    }
}
