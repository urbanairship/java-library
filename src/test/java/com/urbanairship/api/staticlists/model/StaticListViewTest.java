package com.urbanairship.api.staticlists.model;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StaticListViewTest {

    @Test
    public void testMinimalStaticListLookupResponse() {
        DateTime created = new DateTime(2014, 10, 1, 12, 0, 0, 0);
        DateTime updated = created.plus(Period.hours(48));

        StaticListView response = StaticListView.newBuilder()
                .setOk(true)
                .setName("static_list_name")
                .setCreated(created)
                .setLastUpdated(updated)
                .setChannelCount(1234)
                .setStatus("ready")
                .setError("error")
                .build();

        assertNotNull(response);
        assertEquals("static_list_name", response.getName());
        assertEquals(created, response.getCreated());
        assertEquals(Integer.valueOf(1234), response.getChannelCount());
        assertEquals("ready", response.getStatus());
        assertEquals("error", response.getError());
    }

    @Test
    public void testFullStaticListLookupResponse() {
        DateTime created = new DateTime(2014, 10, 1, 12, 0, 0, 0);
        DateTime updated = created.plus(Period.hours(48));

        StaticListView response = StaticListView.newBuilder()
                .setOk(true)
                .setName("static_list_name")
                .setDescription("a great list")
                .setCreated(created)
                .setLastUpdated(updated)
                .setChannelCount(1234)
                .setStatus("processing")
                .build();

        assertNotNull(response);
        assertEquals(Optional.of(true), response.getOk());
        assertEquals("static_list_name", response.getName());
        assertEquals(Optional.of("a great list"), response.getDescription());
        assertEquals(created, response.getCreated());
        assertEquals(updated, response.getLastUpdated());
        assertEquals(Integer.valueOf(1234), response.getChannelCount());
        assertEquals("processing", response.getStatus());
    }
}
