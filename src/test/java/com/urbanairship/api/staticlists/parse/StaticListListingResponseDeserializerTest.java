package com.urbanairship.api.staticlists.parse;


import com.google.common.base.Optional;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.staticlists.model.StaticListListingResponse;
import com.urbanairship.api.staticlists.model.StaticListView;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StaticListListingResponseDeserializerTest {
    private static final ObjectMapper mapper = StaticListsObjectMapper.getInstance();

    @Test
    public void testStaticListListingResponse() throws Exception {
        DateTime created = DateFormats.DATE_PARSER.parseDateTime("2013-08-08T20:41:06");
        DateTime updated = DateFormats.DATE_PARSER.parseDateTime("2014-05-01T18:00:27");

        String json = "{" +
                "\"ok\" : true," +
                "\"lists\" : [" +
                    "{" +
                        "\"name\" : \"platinum_members\"," +
                        "\"description\" : \"loyalty program platinum members\"," +
                        "\"extra\" : { \"key\" : \"value\" }," +
                        "\"created\" : \"2013-08-08T20:41:06\"," +
                        "\"last_updated\" : \"2014-05-01T18:00:27\"," +
                        "\"channel_count\": 3145," +
                        "\"status\": \"ready\"" +
                    "}," +
                    "{" +
                        "\"name\": \"gold_members\"," +
                        "\"extra\": { \"key\": \"value\" }," +
                        "\"created\": \"2013-08-08T20:41:06\"," +
                        "\"last_updated\": \"2014-05-01T18:00:27\"," +
                        "\"channel_count\": 678," +
                        "\"status\": \"ready\"" +
                    "}" +
                "]" +
            "}";

        StaticListListingResponse lists = mapper.readValue(json, StaticListListingResponse.class);
        assertNotNull(lists);
        assertEquals(true, lists.getOk());

        StaticListView list1 = lists.getStaticListObjects().get(0);
        assertNotNull(list1);
        assertEquals("platinum_members", list1.getName());
        assertEquals(Optional.of("loyalty program platinum members"), list1.getDescription());
        assertEquals(created, list1.getCreated());
        assertEquals(updated, list1.getLastUpdated());
        assertEquals(Integer.valueOf(3145), list1.getChannelCount());
        assertEquals("ready", list1.getStatus());

        StaticListView list2 = lists.getStaticListObjects().get(1);
        assertNotNull(list2);
        assertEquals("gold_members", list2.getName());
        assertEquals(Optional.absent(), list2.getDescription());
        assertEquals(created, list2.getCreated());
        assertEquals(updated, list2.getLastUpdated());
        assertEquals(Integer.valueOf(678), list2.getChannelCount());
        assertEquals("ready", list2.getStatus());

    }
}
