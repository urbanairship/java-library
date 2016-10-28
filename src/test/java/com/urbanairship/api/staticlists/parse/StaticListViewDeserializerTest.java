package com.urbanairship.api.staticlists.parse;

import com.google.common.base.Optional;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.staticlists.model.StaticListView;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StaticListViewDeserializerTest {

    private static final ObjectMapper mapper = StaticListsObjectMapper.getInstance();

    @Test
    public void testStaticListView() throws Exception {
        DateTime created = DateFormats.DATE_PARSER.parseDateTime("2014-10-01T12:00:00");
        DateTime updated = DateFormats.DATE_PARSER.parseDateTime("2014-10-03T12:00:00");

        String json = "{" +
                "\"ok\" : true," +
                "\"name\" : \"platinum_members\"," +
                "\"description\" : \"loyalty program platinum members\"," +
                "\"extra\" : { \"key\" : \"value\" }," +
                "\"created\" : \"2014-10-01T12:00:00\"," +
                "\"last_updated\" : \"2014-10-03T12:00:00\"," +
                "\"channel_count\" : 1000," +
                "\"status\" : \"ready\"" +
                "}";

        StaticListView list = mapper.readValue(json, StaticListView.class);
        assertEquals(Optional.of(true), list.getOk());
        assertEquals("platinum_members", list.getName());
        assertEquals(Optional.of("loyalty program platinum members"), list.getDescription());
        assertEquals(created, list.getCreated());
        assertEquals(updated, list.getLastUpdated());
        assertEquals(Integer.valueOf(1000), list.getChannelCount());
        assertEquals("ready", list.getStatus());
    }
}
