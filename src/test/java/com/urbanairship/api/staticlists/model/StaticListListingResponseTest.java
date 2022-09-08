package com.urbanairship.api.staticlists.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.staticlists.parse.StaticListsObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
        assertTrue(response.getOk());
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

    @Test
    public void testErrorAPIStaticListListingResponse() throws IOException {
        String jsonResponse = "{\n" +
                "    \"ok\": false,\n" +
                "    \"error\": \"error\",\n" +
                "    \"details\": {\n" +
                "        \"error\": \"error\"\n" +
                "    }\n" +
                "}";

        ObjectMapper mapper = StaticListsObjectMapper.getInstance();
        StaticListListingResponse response = mapper.readValue(jsonResponse, StaticListListingResponse.class);
        assertEquals("error", response.getError().get());
        assertEquals("error", response.getErrorDetails().get().getError().get());
        assertFalse(response.getOk());
    }
}
