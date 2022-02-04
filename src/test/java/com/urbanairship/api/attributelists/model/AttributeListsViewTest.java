package com.urbanairship.api.attributelists.model;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AttributeListsViewTest {

    @Test
    public void testAttributeListsViewResponse() {
        DateTime created = new DateTime(2014, 10, 1, 12, 0, 0, 0);
        DateTime updated = created.plus(Period.hours(48));

        AttributeListsView response = AttributeListsView.newBuilder()
                .setOk(true)
                .setName("ua_attributes_list_name")
                .setCreated(created)
                .setLastUpdated(updated)
                .setChannelCount(1234)
                .setErrorPath("https://go.urbanairship.com/api/attribute-lists/ua_attributes_my_list/errors")
                .setStatus("ready")
                .build();

        assertNotNull(response);
        assertEquals("ua_attributes_list_name", response.getName());
        assertEquals(created, response.getCreated());
        assertEquals(Integer.valueOf(1234), response.getChannelCount());
        assertEquals("https://go.urbanairship.com/api/attribute-lists/ua_attributes_my_list/errors", response.getErrorPath());
        assertEquals("ready", response.getStatus());
    }
}
