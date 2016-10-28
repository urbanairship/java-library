package com.urbanairship.api.staticlists.model;

import com.google.common.base.Optional;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StaticListListingResponseTest {

    @Test
    public void testStaticListListingResponse() {
        DateTime created = new DateTime(2014, 10, 1, 12, 0, 0, 0);
        DateTime updated = created.plus(Period.hours(48));

        StaticListView res1 = StaticListView.newBuilder()
                .setName("static_list_name")
                .setCreated(created)
                .setLastUpdated(updated)
                .setChannelCount(1234)
                .setStatus("ready")
                .build();

        StaticListView res2 = StaticListView.newBuilder()
                .setName("static_list_name")
                .setDescription("a great list")
                .setCreated(created)
                .setLastUpdated(updated)
                .setChannelCount(1234)
                .setStatus("processing")
                .build();

        StaticListListingResponse response = StaticListListingResponse.newBuilder()
                .setOk(true)
                .addStaticList(res1)
                .addStaticList(res2)
                .build();

        assertNotNull(response);
        assertEquals(true, response.getOk());
        assertEquals("static_list_name", response.getStaticListViews().get(0).getName());
        assertEquals(created, response.getStaticListViews().get(0).getCreated());
        assertEquals(Integer.valueOf(1234), response.getStaticListViews().get(0).getChannelCount());
        assertEquals("ready", response.getStaticListViews().get(0).getStatus());
        assertEquals("static_list_name", response.getStaticListViews().get(1).getName());
        assertEquals(Optional.of("a great list"), response.getStaticListViews().get(1).getDescription());
        assertEquals(created, response.getStaticListViews().get(1).getCreated());
        assertEquals(updated, response.getStaticListViews().get(1).getLastUpdated());
        assertEquals(Integer.valueOf(1234), response.getStaticListViews().get(1).getChannelCount());
        assertEquals("processing", response.getStaticListViews().get(1).getStatus());
    }
}
